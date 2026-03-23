package connectroomies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.dtos.AlquilerDto;
import connectroomies.model.entities.Alquiler;
import connectroomies.model.entities.Habitacion;
import connectroomies.model.entities.Usuario;
import connectroomies.model.entities.Vivienda;
import connectroomies.model.enums.EstadoAlquiler;
import connectroomies.model.mappers.AlquilerMapper;
import connectroomies.model.repositories.AlquilerRepository;
import connectroomies.model.repositories.HabitacionRepository;
import connectroomies.model.repositories.UsuarioRepository;
import connectroomies.model.repositories.ViviendaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlquilerServiceImpl implements AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final UsuarioRepository usuarioRepository;
    private final ViviendaRepository viviendaRepository;
    private final HabitacionRepository habitacionRepository;

    //CRUD
    @Override
    public List<Alquiler> findAll() {
        // TODO Auto-generated method stub
       return alquilerRepository.findAll();
    }
    @Override
    public Alquiler findById(Long id) {
        // TODO Auto-generated method stub
        return alquilerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));
    }
    @Override
    public Alquiler newAlquiler(Alquiler alquiler, Usuario usuario) {
    	//Validar Rol
        boolean isUsuario = usuario.getRoles().stream().anyMatch(r -> r.getNombre().equals("USUARIO"));
        boolean isAdmin = usuario.getRoles().stream().anyMatch(r -> r.getNombre().equals("ADMIN"));
        if (!isUsuario && !isAdmin) {
            throw new RuntimeException("Solo los usuarios pueden alquilar habitaciones");
        }
        //Validar fechas
        if (alquiler.getFechaInicio() == null) {
        	throw new RuntimeException("La fecha de inicio es necesaria");
        }
        if (alquiler.getFechaFin() == null) {
        	throw new RuntimeException("La fecha de fin es necesaria");
        }
        if (alquiler.getFechaFin().isBefore(alquiler.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la de inicio");
        }
        //Validar que el usuario no tiene alquiler activo
        boolean tieneActivo = alquilerRepository.existsByInquilinoIdAndEstado(usuario.getId(), EstadoAlquiler.ACTIVO);
        if (tieneActivo) {
            throw new RuntimeException("El usuario ya tiene un alquiler activo");
        }
        //Validar habitación o vivienda completa
        if (alquiler.getHabitacion() != null) {
            Habitacion habitacion = habitacionRepository.findById(alquiler.getHabitacion().getId())
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
            
            if (habitacion.getDisponible() == 0) {
                throw new RuntimeException("La habitación no está disponible");
            }
            // Marcar como no disponible
            habitacion.setDisponible(0);

            alquiler.setHabitacion(habitacion);
            alquiler.setVivienda(habitacion.getVivienda());
        } else if (alquiler.getVivienda() != null) {
            Vivienda vivienda = viviendaRepository.findById(alquiler.getVivienda().getId())
                    .orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));

            alquiler.setVivienda(vivienda);
            alquiler.setHabitacion(null);
        } else {
            throw new RuntimeException("Debe indicar habitación o vivienda a alquilar");
        }

        //Asignar inquilino y estado
        alquiler.setInquilino(usuario);
        alquiler.setEstado(EstadoAlquiler.ACTIVO);

        return alquilerRepository.save(alquiler);

    }
    @Override
    public Alquiler updateAlquiler(Alquiler alquiler, Usuario usuario) {
        
    	Alquiler currentAlquiler = alquilerRepository.findById(alquiler.getId())
    	            .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));

	    boolean isInquilino = currentAlquiler.getInquilino().getId().equals(usuario.getId());
	    boolean isAdmin = usuario.getRoles().stream().anyMatch(r -> r.getNombre().equals("ADMIN"));

	    if (!isInquilino && !isAdmin) {
	        throw new RuntimeException("No tienes permisos para modificar este alquiler");
	    }

	    if (alquiler.getFechaInicio() != null) {
	        currentAlquiler.setFechaInicio(alquiler.getFechaInicio());
	    }

	    if (alquiler.getFechaFin() != null) {
	        currentAlquiler.setFechaFin(alquiler.getFechaFin());
	    }

	    if (alquiler.getEstado() != null) {
	        currentAlquiler.setEstado(alquiler.getEstado());
	    }

	    return alquilerRepository.save(currentAlquiler);
    }
    @Override
    public void deleteAlquiler(Long id, Usuario usuario) {
    	 Alquiler alquiler = alquilerRepository.findById(id)
    	            .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));

    	    boolean isInquilino = alquiler.getInquilino().getId().equals(usuario.getId());
    	    boolean isAdmin = usuario.getRoles().stream().anyMatch(r -> r.getNombre().equals("ADMIN"));
    	    if (!isInquilino && !isAdmin) {
    	        throw new RuntimeException("No tienes permisos para eliminar este alquiler");
    	    }

    	    Habitacion habitacion = alquiler.getHabitacion();

    	    if (habitacion != null) {
    	        habitacion.setDisponible(1);
    	    }

    	    alquilerRepository.delete(alquiler);
    }
	@Override
	public List<AlquilerDto> getAlquileresByUsuario(Long usuarioId) {
		return alquilerRepository.findByInquilinoId(usuarioId)
				.stream()
				.map(AlquilerMapper::toDto)
				.toList();
	}
	@Override
	public void cancelarAlquiler(Long alquilerId, Usuario usuario) {
		
		Alquiler alquiler = alquilerRepository.findById(alquilerId)
	            .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));

	    boolean isAdmin = usuario.getRoles().stream()
	            .anyMatch(r -> r.getNombre().equals("ADMIN"));

	    if (!isAdmin && !alquiler.getInquilino().getId().equals(usuario.getId())) {
	        throw new RuntimeException("No puedes cancelar este alquiler");
	    }

	    if (alquiler.getEstado() == EstadoAlquiler.CANCELADO) {
	        throw new RuntimeException("El alquiler ya está cancelado");
	    }

	    if (alquiler.getEstado() == EstadoAlquiler.FINALIZADO) {
	        throw new RuntimeException("El alquiler ya finalizó");
	    }

	    if (alquiler.getHabitacion() != null) {
	        Habitacion habitacion = alquiler.getHabitacion();
	        habitacion.setDisponible(1);
	    }

	    alquiler.setEstado(EstadoAlquiler.CANCELADO);

	    alquilerRepository.save(alquiler);
	}

    
    
}
