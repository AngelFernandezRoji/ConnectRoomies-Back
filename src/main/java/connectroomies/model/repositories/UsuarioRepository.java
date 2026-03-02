package connectroomies.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import connectroomies.model.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByEmail(String email);
	
}
