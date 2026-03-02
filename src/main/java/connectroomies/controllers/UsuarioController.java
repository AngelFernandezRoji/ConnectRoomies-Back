package connectroomies.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> createUser(@RequestBody Usuario usuario) {
        try {
            UsuarioDto dto = UsuarioMapper.toDto(usuarioService.newUsuario(usuario));
            return ResponseEntity.status(201).body("Añadido correctamente: " + dto.getNombre());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al añadir usuario");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id,
                                            @RequestBody Usuario usuario) {
        try {
        	usuario.setId(id);
            UsuarioDto dto = UsuarioMapper.toDto(usuarioService.updateUsuario(usuario));
            return ResponseEntity.ok("Usuario actualizado: " + dto.getNombre());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error al actualizar usuario: no se encontró");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar usuario");
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
}