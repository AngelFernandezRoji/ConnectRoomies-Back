package connectroomies.model.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RegistrarAlquilerRequestDto {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private Long viviendaId;
    private Long habitacionId;

    private String mensaje;
    private Integer duracionMeses;
    
}
