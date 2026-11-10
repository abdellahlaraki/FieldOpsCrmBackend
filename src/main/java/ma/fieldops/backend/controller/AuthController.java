package ma.fieldops.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.fieldops.backend.dto.LoginRequest;
import ma.fieldops.backend.dto.LoginResponse;
import ma.fieldops.backend.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),request.password()));
        UserDetails user=(UserDetails) authentication.getPrincipal();
        String token=jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

}
