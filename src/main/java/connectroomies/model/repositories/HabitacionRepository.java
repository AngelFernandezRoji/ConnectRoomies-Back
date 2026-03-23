package connectroomies.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import connectroomies.model.entities.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    List<Habitacion> findByViviendaId(Long viviendaId);
}
