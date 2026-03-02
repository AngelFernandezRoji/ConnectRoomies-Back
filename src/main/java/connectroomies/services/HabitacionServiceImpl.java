package connectroomies.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import connectroomies.model.entities.Habitacion;
import connectroomies.model.repositories.HabitacionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {
	
	private final HabitacionRepository habitacionRepository;
	
	//CRUD
	@Override
	public List<Habitacion> findAll() {
		// TODO Auto-generated method stub
		return habitacionRepository.findAll();
	}
	@Override
	public Habitacion findById(Long id) {
		// TODO Auto-generated method stub
		return habitacionRepository.findById(id).orElse(null);
	}
	@Override
	public Habitacion newHabitacion(Habitacion habitacion) {
		// TODO Auto-generated method stub
		return habitacionRepository.save(habitacion);
	}
	@Override
	public Habitacion updateHabitacion(Habitacion habitacion) {
		Habitacion currentHab = findById(habitacion.getId());
		if (currentHab != null ) {
			currentHab.setNombre(habitacion.getNombre());
			currentHab.setDescripcion(habitacion.getDescripcion());
			currentHab.setPrecio(habitacion.getPrecio());
			currentHab.setDisponible(habitacion.getDisponible());
		    return habitacionRepository.save(currentHab);
		} else {
			return null;
		}
	}
	@Override
	public boolean deleteHabitacion(Long id) {
		// TODO Auto-generated method stub
		if (habitacionRepository.existsById(id)) {
			habitacionRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	
}
