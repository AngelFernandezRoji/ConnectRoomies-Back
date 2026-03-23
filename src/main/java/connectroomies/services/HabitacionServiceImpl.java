package connectroomies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.entities.Habitacion;
import connectroomies.model.entities.Usuario;
import connectroomies.model.entities.Vivienda;
import connectroomies.model.repositories.HabitacionRepository;
import connectroomies.model.repositories.ViviendaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {
	
	private final HabitacionRepository habitacionRepository;
	private final ViviendaRepository viviendaRepository;
	
	//CRUD
	@Override
	public List<Habitacion> findAll() {
		// TODO Auto-generated method stub
		return habitacionRepository.findAll();
	}
	@Override
	public Habitacion findById(Long id) {
		// TODO Auto-generated method stub
		return habitacionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
	}
	@Override
	public Habitacion newHabitacion(Habitacion habitacion, Long viviendaId, Usuario usuario) {
		
		if (viviendaId == null) {
	        throw new IllegalArgumentException("Debe indicar la vivienda");
	    }

	    Vivienda vivienda = viviendaRepository.findById(viviendaId)
	            .orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));

	    if (habitacion.getNombre() == null || habitacion.getNombre().isBlank()) {
	        throw new IllegalArgumentException("El nombre de la habitación es obligatorio");
	    }

	    if (habitacion.getPrecio() == null || habitacion.getPrecio() <= 0) {
	        throw new IllegalArgumentException("El precio debe ser mayor que 0");
	    }

	    boolean isPropietario = vivienda.getPropietario().getId().equals(usuario.getId());
	    boolean isAdmin = usuario.getRoles().stream().anyMatch(r -> r.getNombre().equals("ADMIN"));

	    if (!isPropietario && !isAdmin) {
	        throw new RuntimeException("No tienes permisos para realizar esta acción.");
	    }

	    habitacion.setVivienda(vivienda);
	    habitacion.setDisponible(1);

	    return habitacionRepository.save(habitacion);
	}
	@Override
	public Habitacion updateHabitacion(Habitacion habitacion, Usuario usuario) {

	    Habitacion currentHab = habitacionRepository.findById(habitacion.getId())
	            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

	    Vivienda vivienda = currentHab.getVivienda();

	    boolean isPropietario = vivienda.getPropietario().getId().equals(usuario.getId());

	    boolean isAdmin = usuario.getRoles().stream().anyMatch(r -> r.getNombre().equals("ADMIN"));

	    if (!isPropietario && !isAdmin) {
	        throw new RuntimeException("No tienes permisos para realizar esta acción.");
	    }

	    currentHab.setNombre(habitacion.getNombre());
	    currentHab.setDescripcion(habitacion.getDescripcion());
	    currentHab.setPrecio(habitacion.getPrecio());
	    currentHab.setDisponible(habitacion.getDisponible());

	    return habitacionRepository.save(currentHab);
	}
	@Override
	public void deleteHabitacion(Long id, Usuario usuario) {
		 Habitacion habitacion = habitacionRepository.findById(id)
		            .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

		    Vivienda vivienda = habitacion.getVivienda();

		    boolean esPropietario = vivienda.getPropietario().getId().equals(usuario.getId());
		    boolean esAdmin = usuario.getRoles().stream().anyMatch(r -> r.getNombre().equals("ADMIN"));

		    if (!esPropietario && !esAdmin) {
		        throw new RuntimeException("No tienes permisos para realizar esta acción.");
		    }

		    habitacionRepository.delete(habitacion);
	}
	
	
}
