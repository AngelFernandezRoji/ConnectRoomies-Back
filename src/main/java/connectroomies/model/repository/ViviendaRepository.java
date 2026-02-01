package connectroomies.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import connectroomies.model.entities.Vivienda;

public interface ViviendaRepository extends JpaRepository<Vivienda, Long>{
	
}
