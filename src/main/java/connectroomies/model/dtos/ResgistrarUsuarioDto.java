package connectroomies.model.dtos;

import lombok.Data;

@Data
public class ResgistrarUsuarioDto {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String password;
    private String rol;
}