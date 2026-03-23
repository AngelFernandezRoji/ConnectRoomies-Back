package connectroomies.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import connectroomies.model.entities.Rol;
import connectroomies.model.entities.Usuario;
import connectroomies.model.repositories.RolRepository;
import connectroomies.model.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final RolRepository rolRepository;
	private final PasswordEncoder passwordEncoder;

	//CRUD
	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}
	@Override
	public Usuario findById(Long id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}
	@Override
	public Usuario newUsuario(Usuario usuario) {
		//Validaciones básicas
		if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
        	throw new IllegalArgumentException("El nombre es necesario");
    	}
		if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
			throw new RuntimeException("El email es necesario");
		}
		if(!usuario.getEmail().contains("@")){
		    throw new RuntimeException("Email no válido");
		}
		if(usuarioRepository.existsByEmail(usuario.getEmail())){
		    throw new RuntimeException("El email ya está registrado");
		}
		if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
			throw new RuntimeException("La contraseña es necesaria");
		}
		
		//Encriptar la password y asignar estado ACTIVO por defecto al "dar de alta"
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuario.setEstado("ACTIVO");
		
		//Asignación del Rol -> USUARIO por defecto, PROPIETARIO si nos lo manda el front
		Rol rol;

		if (usuario.getRoles() != null && !usuario.getRoles().isEmpty() &&
		    "PROPIETARIO".equalsIgnoreCase(usuario.getRoles().get(0).getNombre())) {
		    rol = rolRepository.findByNombre("PROPIETARIO")
		            .orElseThrow(() -> new RuntimeException("Error al asignar Rol: PROPIETARIO"));
		} else {
		    rol = rolRepository.findByNombre("USUARIO")
		            .orElseThrow(() -> new RuntimeException("Error al asignar Rol: USUARIO"));
		}

		usuario.setRoles(List.of(rol));
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		Usuario currentUsuario = usuarioRepository.findById(usuario.getId())
								.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		currentUsuario.setNombre(usuario.getNombre());
		currentUsuario.setApellidos(usuario.getApellidos());
		currentUsuario.setTelefono(usuario.getTelefono());
		currentUsuario.setEmail(usuario.getEmail());
		currentUsuario.setEstado(usuario.getEstado());
		return usuarioRepository.save(currentUsuario);
	}
	@Override
	public void deleteUsuario(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
							.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		usuarioRepository.delete(usuario);
	}
	

	//CONSULTAS PROPIAS
	@Override
	public Usuario findByEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByEmail(email)
								.orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
	}


}