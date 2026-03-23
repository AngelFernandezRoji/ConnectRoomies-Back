package connectroomies.model.mappers;


import java.util.Collections;

import connectroomies.model.dtos.UsuarioDto;
import connectroomies.model.entities.Rol;
import connectroomies.model.entities.Usuario;
import connectroomies.model.entities.Vivienda;

public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        dto.setEstado(usuario.getEstado());
        //Prevenir null en Viviendas 
        if (usuario.getViviendas() != null) {
            dto.setViviendasIds(
                usuario.getViviendas().stream()
                       .map(Vivienda::getId)
                       .toList()
            );
        } else {
            dto.setViviendasIds(Collections.emptyList());
        }
        //Roles
        dto.setRoles(
        	    usuario.getRoles() != null
        	        ? usuario.getRoles().stream()
        	                  .map(Rol::getNombre)
        	                  .toList()
        	        : Collections.emptyList()
        	);
        return dto;
    }
}