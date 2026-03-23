package connectroomies.services;

import java.util.List;

import connectroomies.model.entities.Usuario;

public interface UsuarioService {
	
	List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario findByEmail(String email);
    Usuario newUsuario(Usuario usuario);
    Usuario updateUsuario(Usuario usuario);
    void deleteUsuario(Long id);
    
}
