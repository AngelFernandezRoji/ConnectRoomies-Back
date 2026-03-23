package connectroomies.model.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ViviendaDto {
    
	private Long id;
    private String titulo;
    private String tipo;
    private String localidad;
    private String provincia;
    private String direccion;
    private Double precio;
    private Integer disponible;
    private LocalDateTime fechaCreacion;
    private PropietarioDto propietario;
    
}
