package connectroomies.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import connectroomies.model.dtos.AlquilerDto;
import connectroomies.model.dtos.AlquilerResponseDto;
import connectroomies.model.dtos.RegistrarAlquilerRequestDto;
import connectroomies.model.entities.Alquiler;
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

	    if (alquiler.getEstado() == EstadoAlquiler.CANCELADO || alquiler.getEstado() == EstadoAlquiler.FINALIZADO) {
	        throw new RuntimeException("El alquiler ya no puede ser cancelado");
	    }

	    alquiler.setEstado(EstadoAlquiler.CANCELADO);

        Vivienda vivienda = alquiler.getVivienda();
        vivienda.setDisponible(1);
        viviendaRepository.save(vivienda);

	    alquilerRepository.save(alquiler);
	}

    //SOLICITUDES
    @Override
    public AlquilerResponseDto crearSolicitud(RegistrarAlquilerRequestDto req, Usuario usuario) {
        
        boolean tieneActivo = alquilerRepository
                .existsByInquilinoIdAndEstado(usuario.getId(), EstadoAlquiler.ACTIVO);
        if (tieneActivo) {
            throw new RuntimeException("Ya tienes un alquiler activo");
        }

        boolean tienePendiente = alquilerRepository
                .existsByInquilinoIdAndEstado(usuario.getId(), EstadoAlquiler.PENDIENTE);
        if (tienePendiente) {
            throw new RuntimeException("Tienes una solicitud en estado pendiente");
        }

        if (req.getViviendaId() == null) {
            throw new RuntimeException("No se ha indicado la vivienda a alquilar");
        }

        Vivienda vivienda = viviendaRepository.findById(req.getViviendaId())
                .orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));

        boolean ocupada = alquilerRepository
                .existsByViviendaIdAndEstado(vivienda.getId(), EstadoAlquiler.ACTIVO);
        if (ocupada) {
            throw new RuntimeException("La vivienda ya está ocupada");
        }

        boolean yaSolicitada = alquilerRepository
                .findByInquilinoId(usuario.getId())
                .stream()
                .anyMatch(a ->
                        a.getVivienda().getId().equals(vivienda.getId()) &&
                        a.getEstado() == EstadoAlquiler.PENDIENTE
                );
        if (yaSolicitada) {
            throw new RuntimeException("Ya has solicitado esta vivienda");
        }
        

        Alquiler alquiler = new Alquiler();

        alquiler.setFechaInicio(req.getFechaInicio());
        alquiler.setFechaFin(req.getFechaFin());
        alquiler.setMensaje(req.getMensaje());
        alquiler.setDuracionMeses(req.getDuracionMeses());

        alquiler.setEstado(EstadoAlquiler.PENDIENTE);
        alquiler.setInquilino(usuario);
        alquiler.setVivienda(vivienda);
        alquiler.setPropietario(vivienda.getPropietario());

        alquilerRepository.save(alquiler);

        return AlquilerMapper.toResponseDto(alquiler);

    }
        @Override
    public List<AlquilerDto> getTodasSolicitudes() {
        return alquilerRepository.findAll()
        .stream()
        .map(AlquilerMapper::toDto)
        .toList();
    }
    @Override
    public List<AlquilerDto> getSolicitudesPropietario(Long propietarioId) {
        return alquilerRepository.findByPropietarioId(propietarioId)
        .stream()
        .map(AlquilerMapper::toDto)
        .toList();
    }
    public List<AlquilerDto> getSolicitudesActivasPropietario(Long propietarioId) {
        return alquilerRepository
        .findByPropietarioIdAndEstado(propietarioId, EstadoAlquiler.ACTIVO)
        .stream()
        .map(AlquilerMapper::toDto)
        .toList();
    }
    @Override
    public void actualizarEstado(Long alquilerId, EstadoAlquiler estado) {
        Alquiler alquiler = alquilerRepository.findById(alquilerId)
            .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));

        if (alquiler.getEstado() != EstadoAlquiler.PENDIENTE) {
            throw new RuntimeException("Solo se pueden modificar solicitudes pendientes");
        }

        if (estado != EstadoAlquiler.ACTIVO && estado != EstadoAlquiler.RECHAZADO) {
            throw new RuntimeException("Estado no válido");
        }

        if (estado == EstadoAlquiler.ACTIVO) {

            Long viviendaId = alquiler.getVivienda().getId();

            boolean ocupada = alquilerRepository
                    .existsByViviendaIdAndEstado(viviendaId, EstadoAlquiler.ACTIVO);
            if (ocupada) {
                throw new RuntimeException("La vivienda ya está ocupada");
            }

            alquiler.setEstado(EstadoAlquiler.ACTIVO);

            Vivienda vivienda = alquiler.getVivienda();
            vivienda.setDisponible(0);
            viviendaRepository.save(vivienda);

            List<Alquiler> pendientes = alquilerRepository
                    .findByViviendaIdAndEstado(viviendaId, EstadoAlquiler.PENDIENTE);
            for (Alquiler a : pendientes) {
                if (!a.getId().equals(alquiler.getId())) {
                    a.setEstado(EstadoAlquiler.RECHAZADO);
                }
            }

            alquilerRepository.saveAll(pendientes);

        } else {
            alquiler.setEstado(EstadoAlquiler.RECHAZADO);
        }

        alquilerRepository.save(alquiler);
    }
    
    //AUTO-FINALIZAR ALQUILERES ACTIVOS SI FECHA_FIN HA PASADO
   @Scheduled(cron = "0 0 * * * *")
    public void finalizarAlquileres() {

        List<Alquiler> activos = alquilerRepository.findByEstado(EstadoAlquiler.ACTIVO);

        LocalDateTime ahora = LocalDateTime.now();

        for (Alquiler alquiler : activos) {

            if (alquiler.getFechaFin().isBefore(ahora)) {

                alquiler.setEstado(EstadoAlquiler.FINALIZADO);

                Vivienda vivienda = alquiler.getVivienda();
                vivienda.setDisponible(1);
                viviendaRepository.save(vivienda);
            }
        }

        alquilerRepository.saveAll(activos);
    }

    
}
