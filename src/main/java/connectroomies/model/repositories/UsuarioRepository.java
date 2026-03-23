package connectroomies.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import connectroomies.model.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByEmail(String email);
	boolean existsByEmail(String email);
}
