package connectroomies.model.dtos;

import java.time.LocalDateTime;

import connectroomies.model.enums.EstadoAlquiler;
import lombok.Data;

@Data
public class AlquilerDto {

    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer duracionMeses;
    private String mensaje;
    private EstadoAlquiler estado;

    private InquilinoDto inquilino;
    private PropietarioDto propietario;
    private ViviendaAlquilerDto vivienda;
    private HabitacionAlquilerDto habitacion;
}
