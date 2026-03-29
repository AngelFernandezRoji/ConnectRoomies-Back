package connectroomies.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import connectroomies.model.entities.ImagenVivienda;
import connectroomies.model.entities.Usuario;

public interface ImagenViviendaService {
    
	ImagenVivienda subirImagen(Long viviendaId, MultipartFile file, Usuario usuario);
    List<ImagenVivienda> listarImagenes(Long viviendaId);
    void eliminarImagen(Long imgId);
}
