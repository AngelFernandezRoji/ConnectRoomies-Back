package connectroomies.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import connectroomies.model.dtos.AlquilerDto;
import connectroomies.model.entities.Alquiler;
import connectroomies.model.entities.Usuario;
import connectroomies.model.mappers.AlquilerMapper;
import connectroomies.services.AlquilerService;
import connectroomies.services.UsuarioService;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService alquilerService;
    private final UsuarioService usuarioService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<AlquilerDto> getAllAlquileres() {
        return alquilerService.findAll()
                .stream()
                .map(AlquilerMapper::toDto)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROPIETARIO','USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAlquilerById(@PathVariable Long id) {
        try {
            AlquilerDto dto = AlquilerMapper.toDto(alquilerService.findById(id));
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Alquiler no encontrado");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    @PostMapping
    public ResponseEntity<?> createAlquiler(@RequestBody Alquiler alquiler, Authentication authentication) {
    	try {
    		Usuario usuario = (Usuario) authentication.getPrincipal();

    		alquilerService.newAlquiler(alquiler, usuario);
            return ResponseEntity.status(201).body("Alquiler creado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear alquiler");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlquiler(@PathVariable Long id,
                                            @RequestBody Alquiler alquiler,
                                            Authentication authentication) {
        try {
        	Usuario usuario = (Usuario) authentication.getPrincipal();
        	
            alquiler.setId(id);
            
            alquilerService.updateAlquiler(alquiler, usuario);
            return ResponseEntity.ok("Alquiler actualizado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error, no se encontró el alquiler");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al actualizar alquiler");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlquiler(@PathVariable Long id, Authentication authentication) {
        try {
        	Usuario usuario = (Usuario) authentication.getPrincipal();

        	alquilerService.deleteAlquiler(id, usuario);
            return ResponseEntity.ok("Alquiler eliminado correctamentw");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("No se ha encontrado el alquiler indicado");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al eliminar el alquiler");
        }
    }

    
    @GetMapping("/alquileres-usuario")
    public ResponseEntity<List<AlquilerDto>> getAlquileresUsuario(@RequestParam Long usuarioId){
    	return ResponseEntity.ok(alquilerService.getAlquileresByUsuario(usuarioId));
    }
    
    
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarAlquiler(@PathVariable Long id,
                                              @RequestParam Long usuarioId) {
        try {
            Usuario usuario = usuarioService.findById(usuarioId);

            alquilerService.cancelarAlquiler(id, usuario);

            return ResponseEntity.ok("Alquiler cancelado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
