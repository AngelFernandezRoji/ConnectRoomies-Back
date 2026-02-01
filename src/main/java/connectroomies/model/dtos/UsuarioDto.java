package connectroomies.model.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsuarioDto {

    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private LocalDateTime fechaRegistro;
    private String estado;
    
}