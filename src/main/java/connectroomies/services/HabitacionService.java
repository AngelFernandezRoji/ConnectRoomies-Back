package connectroomies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.entities.Habitacion;

@Service
public interface HabitacionService {

	List<Habitacion> findAll();
	Habitacion findById(Long id);
	Habitacion newHabitacion(Habitacion habitacion);
	Habitacion updateHabitacion(Habitacion habitacion);
	boolean deleteHabitacion(Long id);
	

}
