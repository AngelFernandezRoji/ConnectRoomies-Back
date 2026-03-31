package connectroomies.model.mappers;

import connectroomies.model.dtos.ImagenViviendaDto;
import connectroomies.model.entities.ImagenVivienda;

public class ImagenViviendaMapper {
	
	public static ImagenViviendaDto toDto(ImagenVivienda imagen) {
        ImagenViviendaDto dto = new ImagenViviendaDto();
        dto.setId(imagen.getId());
        dto.setUrlImg(imagen.getUrlImg());
        dto.setViviendaId(
            imagen.getVivienda() != null ? imagen.getVivienda().getId() : null
        );
        return dto;
    }
}
