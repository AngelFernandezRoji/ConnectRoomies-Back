package connectroomies.model.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AlquilerDto {

    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;    
    private Long inquilinoId;
    private Long viviendaId;
    private Long habitacionId;

}
