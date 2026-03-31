package connectroomies.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> updateUserById(@PathVariable Long id,
                                            @RequestBody Usuario usuario) {
        try {
        	usuario.setId(id);
            usuarioService.updateUsuario(usuario);
        } catch (Exception e) {
        	return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<Void>(HttpStatus.OK);
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