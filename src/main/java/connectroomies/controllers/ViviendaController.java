package connectroomies.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

import connectroomies.model.dtos.HabitacionDto;
import connectroomies.model.dtos.ViviendaDto;
import connectroomies.model.entities.Usuario;
import connectroomies.model.entities.Vivienda;
import connectroomies.model.mappers.ViviendaMapper;
import connectroomies.services.ViviendaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/viviendas")
@RequiredArgsConstructor
public class ViviendaController {

	private final ViviendaService viviendaService;
	
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
    public ResponseEntity<?> createVivienda(@RequestBody Vivienda vivienda, Authentication authentication) {
        try {
        	Usuario usuario = (Usuario) authentication.getPrincipal();
            viviendaService.newVivienda(vivienda, usuario);
            return ResponseEntity.status(201).body("Vivienda añadida correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al añadir vivienda");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVivienda(@PathVariable Long id,
                                            @RequestBody Vivienda vivienda,
                                            Authentication authentication) {
        try {
        	Usuario usuario = (Usuario) authentication.getPrincipal();
            vivienda.setId(id);
        	viviendaService.updateVivienda(vivienda, usuario);
            return ResponseEntity.ok("Vivienda actualizada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: vivienda no encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVivienda(@PathVariable Long id, Authentication authentication) {
        try {
        	Usuario usuario = (Usuario) authentication.getPrincipal();
        	
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
    
    @GetMapping("/viviendas-propietario")
    public ResponseEntity<List<ViviendaDto>> getMisViviendas(@RequestParam Long propietarioId) {
        return ResponseEntity.ok(viviendaService.getViviendasByPropietario(propietarioId));
    }
    
}
