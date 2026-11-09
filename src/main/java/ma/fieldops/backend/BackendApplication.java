package ma.fieldops.backend;

import ma.fieldops.backend.entity.Role;
import ma.fieldops.backend.entity.User;
import ma.fieldops.backend.repository.RoleRepository;
import ma.fieldops.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public  CommandLineRunner initRole(RoleRepository roleRepository,UserRepository userRepository,PasswordEncoder passwordEncoder){
        return args -> {
         Role admin=   createRoleIfMissing(roleRepository,"ADMIN","Gère les comptes et les rôles");
         Role manager=   createRoleIfMissing(roleRepository,"MANAGER","Gère les clients et planifie les visites");
         Role sales=   createRoleIfMissing(roleRepository,"SALESPERSON","Commercial terrain");

            // 2. puis les utilisateurs
            createUserIfMissing(userRepository, passwordEncoder, "Administrateur", "admin", "admin@fieldops.local", "123", admin);
            createUserIfMissing(userRepository, passwordEncoder, "Karim Manager", "manager1", "manager1@fieldops.local", "123", manager);
            createUserIfMissing(userRepository, passwordEncoder, "Sara Commerciale", "sales1", "sales1@fieldops.local", "123", sales);

            // exemple avec plusieurs rôles : un dirigeant manager ET admin
            createUserIfMissing(userRepository, passwordEncoder, "Dirigeant", "boss", "boss@fieldops.local", "123", admin, manager);
        };
    }

    private Role createRoleIfMissing(RoleRepository rep,String name,String description){
        return rep.findByName(name).orElseGet(()-> rep.save(Role.builder().name(name).description(description).build())
        );
    }


    private void createUserIfMissing(UserRepository repo,PasswordEncoder passwordEncoder,String fullName,String username,String email,String password,Role... roles){
        if(repo.existsByUsername(username)){
            return;
        }
        User user=User.builder().fullName(fullName).email(email).active(true).username(username).passwordHash(passwordEncoder.encode(password)).build();
        user.getRoles().addAll(Arrays.asList(roles));
        repo.save(user);
    }
}
