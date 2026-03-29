package connectroomies.model.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ViviendaDto {
    
	private Long id;
    private String titulo;
    private String tipo;
    private String localidad;
    private String provincia;
    private String direccion;
    private String codigoPostal;
    private Double precio;
    private Integer disponible;
    private LocalDateTime fechaCreacion;
    private String descripcion;
    private Double metros;
    private Integer banos;
    private Integer habitacionesTotales;
    private String normas;
    
    private List<String> comodidades;
    
    private PropietarioDto propietario;

    private List<ImagenViviendaDto> imagenesVivienda;

   
    
}
