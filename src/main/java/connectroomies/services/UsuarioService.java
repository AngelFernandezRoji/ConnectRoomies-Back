package connectroomies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.entities.Usuario;

@Service
public interface UsuarioService {
	
	List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario findByEmail(String email);
    Usuario newUsuario(Usuario usuario);
    Usuario updateUsuario(Usuario usuario);
    boolean deleteUsuario(Long id);
    
}
