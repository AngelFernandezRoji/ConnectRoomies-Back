package connectroomies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.entities.Usuario;
import connectroomies.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;	

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
        		.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario u = findById(id);
        u.setNombre(usuario.getNombre());
        u.setApellidos(usuario.getApellidos());
        u.setTelefono(usuario.getTelefono());
        u.setEmail(usuario.getEmail());
        u.setEstado(usuario.getEstado());
        return usuarioRepository.save(u);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
