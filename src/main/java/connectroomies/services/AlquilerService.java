package connectroomies.services;

import java.util.List;

import connectroomies.model.dtos.AlquilerDto;
import connectroomies.model.dtos.RegistrarAlquilerDto;
import connectroomies.model.entities.Alquiler;
import connectroomies.model.entities.Usuario;

public interface AlquilerService {

	//CRUD
    List<Alquiler> findAll();
    Alquiler findById(Long id);
    Alquiler newAlquiler(RegistrarAlquilerDto dto, Usuario usuario);
    Alquiler updateAlquiler(Alquiler alquiler, Usuario usuario);
    void deleteAlquiler(Long id, Usuario usuario);

    //CONSULTAS PROPIAS
    List<AlquilerDto> getAlquileresByUsuario(Long usuarioId);
    void cancelarAlquiler(Long alquilerId, Usuario usuario);
}
