package connectroomies.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
        .csrf(csrf -> csrf.disable()) // DESACTIVA CSRF para POST/PUT/DELETE
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated() // todos los endpoints requieren login
        )
        .httpBasic(httpBasic -> {}); // habilita Basic Auth

    return http.build();
     
    }
}