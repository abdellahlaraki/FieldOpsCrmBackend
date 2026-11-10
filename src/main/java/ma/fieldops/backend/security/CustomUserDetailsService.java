package ma.fieldops.backend.security;

import lombok.RequiredArgsConstructor;
import ma.fieldops.backend.entity.User;
import ma.fieldops.backend.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService { // charger l’utilisateur depuis la base

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found with username: " + username));
        List<SimpleGrantedAuthority> authorities=user.getRoles().stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.getName())).toList();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(authorities)
                .disabled(!Boolean.TRUE.equals(user.getActive()))
                .build();
    }
}
