package connectroomies.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import connectroomies.model.entities.Usuario;
import connectroomies.model.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;

	//CRUD
	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}
	@Override
	public Usuario findById(Long id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id).orElse(null);
	}
	@Override
	public Usuario newUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		Usuario currentUsuario = findById(usuario.getId());
        if(currentUsuario != null) {
        	currentUsuario.setNombre(usuario.getNombre());
        	currentUsuario.setApellidos(usuario.getApellidos());
        	currentUsuario.setTelefono(usuario.getTelefono());
        	currentUsuario.setEmail(usuario.getEmail());
        	currentUsuario.setEstado(usuario.getEstado());
            return usuarioRepository.save(currentUsuario);
        } else {
            return null;
        }
	}
	@Override
	public boolean deleteUsuario(Long id) {
		// TODO Auto-generated method stub
		if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
	}
	

	//CONSULTAS PROPIAS
	@Override
	public Usuario findByEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByEmail(email);
	}


}