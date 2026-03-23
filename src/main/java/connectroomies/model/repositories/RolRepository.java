package connectroomies.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import connectroomies.model.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{
	
	Optional<Rol> findByNombre(String name);
}
