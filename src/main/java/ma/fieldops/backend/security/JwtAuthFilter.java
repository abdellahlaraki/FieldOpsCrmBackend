package ma.fieldops.backend.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // créer un seul objet au démarrage
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header=request.getHeader("Authorization");

        if(header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token=header.substring(7);

        try {
            String username=jwtService.extractUsername(token);
            UserDetails user=userDetailsService.loadUserByUsername(username);
            if(jwtService.isValid(token,user)){
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (JwtException | IllegalArgumentException | UsernameNotFoundException e){
            // token invalide ou utilisateur inconnu : on continue sans identite
        }
        filterChain.doFilter(request,response);
    }
}
