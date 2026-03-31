package connectroomies.services;

import java.util.List;

import connectroomies.model.dtos.ResgistrarUsuarioDto;
import connectroomies.model.entities.Usuario;

public interface UsuarioService {
	
	List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario findByEmail(String email);
    Usuario newUsuario(ResgistrarUsuarioDto dto);
    Usuario updateUsuario(Usuario usuario);
    void deleteUsuario(Long id);
    void cambiarPassword(String email, String oldPassword, String newPassword);
    
}
