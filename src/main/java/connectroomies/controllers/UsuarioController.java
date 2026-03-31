package connectroomies.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import connectroomies.model.dtos.CambiarPasswordDto;
import connectroomies.model.dtos.ResgistrarUsuarioDto;
import connectroomies.model.dtos.UsuarioDto;
import connectroomies.model.entities.Usuario;
import connectroomies.model.mappers.UsuarioMapper;
import connectroomies.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDto> getAllUsers() {
        return usuarioService.findAll()
        		.stream()
        		.map(UsuarioMapper::toDto)
        		.toList(); 		
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UsuarioDto dto = UsuarioMapper.toDto(usuarioService.findById(id));
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody ResgistrarUsuarioDto dto) {
        try {
            usuarioService.newUsuario(dto);
            return ResponseEntity.status(201).body("Añadido correctamente: " + dto.getNombre());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id,
                                            @RequestBody Usuario usuario) {
        try {
        	usuario.setId(id);
        	UsuarioDto dto = UsuarioMapper.toDto(usuarioService.updateUsuario(usuario));
        	return ResponseEntity.ok(dto);
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al actualizar el usuario");
        }		
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("No se ha encontrado el usuario indicado");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al eliminar usuario");
        }
    }
    
    @PutMapping("/restablecer-password")
    public ResponseEntity<?> restablecerPassword(@RequestBody CambiarPasswordDto dto) {
        try {
            usuarioService.cambiarPassword(
                dto.getEmail(),
                dto.getOldPassword(),
                dto.getNewPassword()
            );
            return ResponseEntity.ok("Contraseña actualizada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar la contraseña");
        }
    }
}