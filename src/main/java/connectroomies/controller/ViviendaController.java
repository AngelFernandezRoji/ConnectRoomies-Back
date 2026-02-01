package connectroomies.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import connectroomies.model.dtos.ViviendaDto;
import connectroomies.model.entities.Vivienda;
import connectroomies.model.mapper.ViviendaMapper;
import connectroomies.service.ViviendaService;
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
                .collect(Collectors.toList());
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

    @PostMapping
    public ResponseEntity<?> createVivienda(@RequestBody Vivienda vivienda) {
        try {
            viviendaService.save(vivienda);
            return ResponseEntity.status(201).body("Vivienda añadida correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error al añadir vivienda");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVivienda(@PathVariable Long id,
                                            @RequestBody Vivienda vivienda) {
        try {
            viviendaService.update(id, vivienda);
            return ResponseEntity.ok("Vivienda actualizada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: vivienda no encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVivienda(@PathVariable Long id) {
        try {
            viviendaService.delete(id);
            return ResponseEntity.ok("Vivienda eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("No se ha encontrado la vivienda indicada");
        }
    }
    
    
}
