package connectroomies.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import connectroomies.model.entities.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    
}
