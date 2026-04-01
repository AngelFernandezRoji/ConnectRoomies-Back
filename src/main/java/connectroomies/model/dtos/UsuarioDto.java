package connectroomies.model.dtos;

import java.time.LocalDateTime;
import java.util.List;

import connectroomies.model.enums.EstadoUsuario;
import lombok.Data;

@Data
public class UsuarioDto {

    private Long id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private EstadoUsuario estado;
    private LocalDateTime fechaRegistro;
    private List<Long> viviendasIds;
    private List<String> roles;
    
}