package connectroomies.services;

import java.util.List;

import connectroomies.model.entities.Habitacion;
import connectroomies.model.entities.Usuario;

public interface HabitacionService {

	List<Habitacion> findAll();
	Habitacion findById(Long id);
	Habitacion newHabitacion(Habitacion habitacion, Long viviendaId, Usuario usuario);
	Habitacion updateHabitacion(Habitacion habitacion, Usuario usuario);
	void deleteHabitacion(Long id, Usuario usuario);
	

}
