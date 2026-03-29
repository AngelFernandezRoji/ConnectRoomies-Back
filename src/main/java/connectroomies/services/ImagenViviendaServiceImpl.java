package connectroomies.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import connectroomies.model.entities.ImagenVivienda;
import connectroomies.model.entities.Usuario;
import connectroomies.model.entities.Vivienda;
import connectroomies.model.repositories.ImagenViviendaRepository;
import connectroomies.model.repositories.ViviendaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagenViviendaServiceImpl implements ImagenViviendaService {

	private final ImagenViviendaRepository imagenRepo;
    private final ViviendaRepository viviendaRepo;

    private final String UPLOAD_DIR = "uploads/"; // /var/www/connectroomies/uploads/ -> PARA EL DESPLIEGUE
	
    
    
	@Override
	public ImagenVivienda subirImagen(Long viviendaId, MultipartFile file, Usuario usuario) {
		
		if (file == null || file.isEmpty()) {
	        throw new RuntimeException("El archivo está vacío");
	    }
	    if (file.getSize() > 5_000_000) {
	        throw new RuntimeException("Archivo muy pesado (máx 5MB)");
	    }
		
		Vivienda vivienda = viviendaRepo.findById(viviendaId)
                .orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));
		
		if (!vivienda.getPropietario().getId().equals(usuario.getId())) {
        	throw new RuntimeException("No tienes permisos para subir imágenes a esta vivienda");
    	}
		
        try {
        	//FGuardar archivo
            File carpeta = new File(UPLOAD_DIR);
            if(!carpeta.exists()) carpeta.mkdirs();

            String filePath = UPLOAD_DIR + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            
            ImagenVivienda img = ImagenVivienda.builder()
                    .vivienda(vivienda)
                    .urlImg(filePath)
                    .build();

            return imagenRepo.save(img);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
	}

	@Override
	public List<ImagenVivienda> listarImagenes(Long viviendaId) {
		Vivienda vivienda = viviendaRepo.findById(viviendaId)
                .orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));
        return imagenRepo.findByVivienda(vivienda);
	}

	@Override
	public void eliminarImagen(Long imgId) {
		 ImagenVivienda img = imagenRepo.findById(imgId)
	                .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));

	        // Eliminar archivo físico
	        File f = new File(img.getUrlImg());
	        if(f.exists()) f.delete();

	        // Eliminar registro
	        imagenRepo.delete(img);
	}

}