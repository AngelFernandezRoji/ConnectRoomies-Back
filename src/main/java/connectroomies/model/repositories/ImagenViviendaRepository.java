package connectroomies.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import connectroomies.model.entities.ImagenVivienda;
import connectroomies.model.entities.Vivienda;

public interface ImagenViviendaRepository extends JpaRepository<ImagenVivienda, Long>{
	List<ImagenVivienda> findByVivienda(Vivienda vivienda);
}
