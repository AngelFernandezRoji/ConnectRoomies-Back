package connectroomies.services;

import java.util.List;

import connectroomies.model.dtos.AlquilerDto;
import connectroomies.model.dtos.AlquilerResponseDto;
import connectroomies.model.dtos.RegistrarAlquilerRequestDto;
import connectroomies.model.entities.Alquiler;
import connectroomies.model.entities.Usuario;
import connectroomies.model.enums.EstadoAlquiler;

public interface AlquilerService {

	//CRUD
    List<Alquiler> findAll();
    Alquiler findById(Long id);
    Alquiler updateAlquiler(Alquiler alquiler, Usuario usuario);
    void deleteAlquiler(Long id, Usuario usuario);

    //CONSULTAS PROPIAS
    List<AlquilerDto> getAlquileresByUsuario(Long usuarioId);
    void cancelarAlquiler(Long alquilerId, Usuario usuario);
    
    AlquilerResponseDto crearSolicitud(RegistrarAlquilerRequestDto req, Usuario usuario);
    List<AlquilerDto> getTodasSolicitudes();
    List<AlquilerDto> getSolicitudesPropietario(Long propietarioId);
    List<AlquilerDto> getSolicitudesActivasPropietario(Long propietarioId);
    void actualizarEstado(Long alquilerId, EstadoAlquiler estado);
}
