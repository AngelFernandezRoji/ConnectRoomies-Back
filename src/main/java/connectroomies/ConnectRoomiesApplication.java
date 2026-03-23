package connectroomies;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import connectroomies.model.entities.Rol;
import connectroomies.model.entities.Usuario;
import connectroomies.model.repositories.RolRepository;
import connectroomies.model.repositories.UsuarioRepository;

@SpringBootApplication
public class ConnectRoomiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectRoomiesApplication.class, args);
	}

	//CommandLineRunner -> se ejecutan al arrancar
    @Bean
    CommandLineRunner initRoles(RolRepository rolRepo) {
        return args -> {
            if (rolRepo.count() == 0) {
                rolRepo.save(new Rol(null, "INVITADO"));
                rolRepo.save(new Rol(null, "USUARIO"));
                rolRepo.save(new Rol(null, "PROPIETARIO"));
                rolRepo.save(new Rol(null, "ADMIN"));
            }
        };
    }
    
    @Bean
    public CommandLineRunner createAdmin(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder encoder) {
        return args -> {
            if (usuarioRepository.findByEmail("admin@connectroomies.com").isEmpty()) {

                Rol adminRole = rolRepository.findByNombre("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

                Usuario admin = Usuario.builder()
                    .nombre("Administrador")
                    .apellidos("Principal")
                    .email("admin@connectroomies.com")
                    .password(encoder.encode("admincr1234"))
                    .estado("ACTIVO")
                    .roles(List.of(adminRole))
                    .build();

                usuarioRepository.save(admin);
            }
        };
    }
    
}
