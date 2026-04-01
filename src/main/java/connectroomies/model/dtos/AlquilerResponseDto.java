package connectroomies.model.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AlquilerResponseDto {
    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    private String mensaje;
    private Integer duracionMeses;
}
