package connectroomies.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import connectroomies.model.dtos.ImagenViviendaDto;
import connectroomies.model.entities.ImagenVivienda;
import connectroomies.model.entities.Usuario;
import connectroomies.model.mappers.ImagenViviendaMapper;
import connectroomies.security.MyUserDetails;
import connectroomies.services.ImagenViviendaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/viviendas")
@RequiredArgsConstructor
public class ImagenViviendaController {

	private final ImagenViviendaService imagenService;

    @PostMapping("/{viviendaId}/imagenes")
    public ResponseEntity<?> subirImagen(@PathVariable Long viviendaId,
                                        @RequestParam("file") MultipartFile file,
                                        Authentication authentication) {
        try {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            Usuario usuario = userDetails.getUsuario();

            ImagenVivienda img = imagenService.subirImagen(viviendaId, file, usuario);
            return ResponseEntity.status(201).body(ImagenViviendaMapper.toDto(img));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/{viviendaId}/imagenes")
    public List<ImagenViviendaDto> listarImagenes(@PathVariable Long viviendaId) {
        return imagenService.listarImagenes(viviendaId)
                .stream()
                .map(img -> {
                    ImagenViviendaDto dto = new ImagenViviendaDto();
                    dto.setId(img.getId());
                    dto.setUrlImg(img.getUrlImg());
                    return dto;
                })
                .toList();
    }

    @DeleteMapping("/{viviendaId}/imagenes/{imgId}")
    public String eliminarImagen(@PathVariable Long viviendaId, @PathVariable Long imgId) {
        imagenService.eliminarImagen(imgId);
        return "Imagen eliminada correctamente";
    }
}
