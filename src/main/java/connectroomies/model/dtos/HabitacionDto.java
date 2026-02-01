package connectroomies.model.dtos;

import lombok.Data;

@Data
public class HabitacionDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer disponible;
    private Long viviendaId;

}
