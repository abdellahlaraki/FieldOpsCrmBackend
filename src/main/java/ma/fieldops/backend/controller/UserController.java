package ma.fieldops.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/users")
public class UserController {

    @GetMapping("/me")
    public Map<String, Object> me (Authentication authentication){
        List<String> roles=authentication.getAuthorities().stream().map(a->a.getAuthority()).toList();
        return Map.of("username",authentication.getName(),"roles",roles);
    }
}
