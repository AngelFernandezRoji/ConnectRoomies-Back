package connectroomies.model.dtos;

import lombok.Data;

@Data
public class ImagenViviendaDto {
    private Long id;
    private String urlImg;
    private Long viviendaId;
}