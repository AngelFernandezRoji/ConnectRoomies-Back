package connectroomies.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final MyUserDetailsService userDetailsService;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasRole("ADMIN")
            		.requestMatchers("/api/auth/**").permitAll()
            		.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                    .requestMatchers("/api/usuarios/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/viviendas/**").hasAnyRole("USUARIO","PROPIETARIO","ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/viviendas/**").hasAnyRole("PROPIETARIO","ADMIN")
                    .requestMatchers("/api/habitaciones/**").hasAnyRole("PROPIETARIO","ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/alquileres/**").hasAnyRole("USUARIO","ADMIN")
                    .requestMatchers("/api/alquileres/**").hasAnyRole("USUARIO","PROPIETARIO","ADMIN")
                    .anyRequest().authenticated()
            )
            .userDetailsService(userDetailsService)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
		 CorsConfiguration config = new CorsConfiguration();

		    config.setAllowedOrigins(List.of(
		            "http://localhost:8099",
		            "http://localhost:8080",
		            "http://localhost:3000",
		            "http://localhost:4200"
		    ));
		    config.setAllowedMethods(List.of(
		            "GET",
		            "POST",
		            "PUT",
		            "DELETE",
		            "PATCH",
		            "OPTIONS"
		    ));
		    config.setAllowedHeaders(List.of(
		            "Authorization",
		            "Content-Type",
		            "Accept",
		            "Origin",
		            "X-Requested-With"
		    ));
		    config.setExposedHeaders(List.of(
		            "Authorization"
		    ));

		    config.setAllowCredentials(true);

		    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		    source.registerCorsConfiguration("/**", config);

		    return source;
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}