package connectroomies.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import connectroomies.model.dtos.ResgistrarUsuarioDto;
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
	public Usuario newUsuario(ResgistrarUsuarioDto dto) {
		//Validaciones básicas
		if (dto.getNombre() == null || dto.getNombre().isBlank()) {
			throw new IllegalArgumentException("El nombre es necesario");
		}

		if (dto.getEmail() == null || dto.getEmail().isBlank()) {
			throw new RuntimeException("El email es necesario");
		}

		if (!dto.getEmail().contains("@")) {
			throw new RuntimeException("Email no válido");
		}

		if (usuarioRepository.existsByEmail(dto.getEmail())) {
			throw new RuntimeException("El email ya está registrado");
		}

		if (dto.getPassword() == null || dto.getPassword().isBlank()) {
			throw new RuntimeException("La contraseña es necesaria");
		}

		Usuario usuario = new Usuario();
		usuario.setNombre(dto.getNombre());
		usuario.setApellidos(dto.getApellidos());
		usuario.setTelefono(dto.getTelefono());
		usuario.setEmail(dto.getEmail());

		usuario.setPassword(passwordEncoder.encode(dto.getPassword())); // Encriptar contraseña

		usuario.setEstado("ACTIVO"); // Estado por defecto

		String rolNombre = "USUARIO"; // Rol por defecto

		if ("PROPIETARIO".equalsIgnoreCase(dto.getRol())) {
			rolNombre = "PROPIETARIO";
		}

		Rol rol = rolRepository.findByNombre(rolNombre)
				.orElseThrow(() -> new RuntimeException("Error al asignar rol"));

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
	
	@Override
	public void cambiarPassword(String email, String oldPassword, String newPassword) {
	    if (email == null || email.isBlank()) {
	        throw new RuntimeException("El email es obligatorio");
	    }

	    if (oldPassword == null || oldPassword.isBlank()) {
	        throw new RuntimeException("La contraseña antigua es obligatoria");
	    }

	    if (newPassword == null || newPassword.isBlank()) {
	        throw new RuntimeException("La nueva contraseña es obligatoria");
	    }

	    Usuario usuario = usuarioRepository.findByEmail(email)
	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    if (!passwordEncoder.matches(oldPassword, usuario.getPassword())) {
	        throw new RuntimeException("La contraseña antigua no es correcta");
	    }

	    usuario.setPassword(passwordEncoder.encode(newPassword));
	    usuarioRepository.save(usuario);
	}


}