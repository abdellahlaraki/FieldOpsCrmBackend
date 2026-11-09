package ma.fieldops.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service  // dit à Spring boot de créer un objet de cette classe au démarrage
public class JwtService {
    // les deux données de classe
    private final SecretKey key; // la clé secrète
    private final long expirationMs; // la durée de vie d'un token

    public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expirationMs){
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT_SECRET is not set");
        }
        this.key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirationMs=expirationMs;
    }

    public String generateToken(UserDetails user){ // -- UserDetails est l’objet que Spring Security utilise pour représenter un utilisateur
        Date now=new Date();
        return Jwts.builder()
                .subject(user.getUsername()) // subject le propiétaire du token le nom d'utilisateur ex:sales1
                .claim("roles",user.getAuthorities().stream() // on met la liste des roles dans claim
                        // getAuthorities renvoie les droits de l'utilisateur
                        .map(GrantedAuthority::getAuthority)  // map les transform en text
                        .toList()).issuedAt(now)
                .expiration(new Date(now.getTime()+expirationMs))
                .signWith(key) // calcule la signature avec le clé
                .compact();
    }

    // lire le badge
    public String extractUsername(String token){
        return parse(token).getSubject();
    }

    // vérifier le badge
    public boolean isValid(String token,UserDetails user){
        Claims claims=parse(token);
        return claims.getSubject().equals(user.getUsername()) // le nom dans le token correspond bien à l’utilisateur chargé depuis la base,
                &&
                claims.getExpiration().after(new Date());  // la date d’expiration est dans le futur.
    }

    // le cœur de la sécurité

    private Claims parse(String token){
        return Jwts.parser()
                .verifyWith(key)  //  verifyWith(key) : recalcule la signature avec ta clé et la compare à celle du token
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
}
