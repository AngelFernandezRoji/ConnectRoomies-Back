package connectroomies.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import connectroomies.model.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	boolean existsByEmail(String email);
	
}
