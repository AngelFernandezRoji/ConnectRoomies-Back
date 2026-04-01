package connectroomies.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import connectroomies.model.entities.Alquiler;
import connectroomies.model.enums.EstadoAlquiler;

public interface AlquilerRepository extends JpaRepository<Alquiler, Long>{

	boolean existsByInquilinoIdAndEstado(Long inquilinoId, EstadoAlquiler estado);
	List<Alquiler> findByInquilinoId(Long inquilinoId);
	List<Alquiler> findByPropietarioId(Long propietarioId);
	List<Alquiler> findByPropietarioIdAndEstado(Long propietarioId , EstadoAlquiler estado);
    
}
