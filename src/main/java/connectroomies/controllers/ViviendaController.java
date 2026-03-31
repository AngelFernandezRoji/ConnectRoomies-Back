package connectroomies.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import connectroomies.model.dtos.HabitacionDto;
import connectroomies.model.dtos.ViviendaDto;
import connectroomies.model.entities.Usuario;
import connectroomies.model.entities.Vivienda;
import connectroomies.model.mappers.ViviendaMapper;
import connectroomies.security.MyUserDetails;
import connectroomies.services.UsuarioService;
import connectroomies.services.ViviendaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/viviendas")
@RequiredArgsConstructor
public class ViviendaController {

	private final ViviendaService viviendaService;
	private final UsuarioService usuarioService;
	
	@GetMapping
	public List<ViviendaDto> getAllViviendas() {
        return viviendaService.findAll()
                .stream()
                .map(ViviendaMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getViviendaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    ViviendaMapper.toDto(viviendaService.findById(id))
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Vivienda no encontrada");
        }
    }
    
    @GetMapping("/{id}/habitaciones")
    public ResponseEntity<List<HabitacionDto>> getHabitaciones(@PathVariable Long id) {
        List<HabitacionDto> habitaciones = viviendaService.getHabitacionesByVivienda(id);
        return ResponseEntity.ok(habitaciones);
    }

    @PostMapping
    public ResponseEntity<?> createVivienda(@RequestBody ViviendaDto dto, Authentication authentication) {
        try {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            Usuario usuario = userDetails.getUsuario();


            boolean isPropietario = usuario.getRoles().stream()
            .anyMatch(r -> r.getNombre().equals("PROPIETARIO"));
            if (!isPropietario) {
                return ResponseEntity.status(403).body("Solo los propietarios pueden añadir viviendas");
            }

            Vivienda vivienda = viviendaService.newVivienda(dto, usuario);
                return ResponseEntity.status(201).body(ViviendaMapper.toDto(vivienda));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVivienda(@PathVariable Long id,
                                            @RequestBody ViviendaDto dto,
                                            Authentication authentication) {
        try {
        	MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            Usuario usuario = userDetails.getUsuario();
            
            Vivienda vivienda = viviendaService.findById(id);

            if (!vivienda.getPropietario().getId().equals(usuario.getId())) {
                return ResponseEntity.status(403).body("No tienes permisos para modificar esta vivienda");
            }

            Vivienda updatedVivienda = viviendaService.updateVivienda(id, dto, usuario);
            return ResponseEntity.ok(ViviendaMapper.toDto(updatedVivienda));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: vivienda no encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVivienda(@PathVariable Long id, Authentication authentication) {
        try {
        	MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            Usuario usuario = userDetails.getUsuario();
        	
            viviendaService.deleteVivienda(id, usuario);
            return ResponseEntity.ok("Vivienda eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("No se ha encontrado la vivienda indicada");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al eliminar la vivienda");
        }
    }
    
 // ENDPOINT DE BÚSQUEDA
    @GetMapping("/buscar")
    public List<ViviendaDto> buscarViviendas(
            @RequestParam(required = false) String localidad,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) Double precioMax) {
        return viviendaService.buscarViviendas(localidad, provincia, precioMax)
                .stream()
                .map(ViviendaMapper::toDto)
                .toList();
    }
    
    @GetMapping("/viviendas-propietario/{id}")
    public ResponseEntity<List<ViviendaDto>> getMisViviendas(@PathVariable Long id) {
        return ResponseEntity.ok(viviendaService.getViviendasByPropietario(id));
    }
    
}
