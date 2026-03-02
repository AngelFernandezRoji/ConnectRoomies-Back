package connectroomies.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import connectroomies.model.dtos.HabitacionDto;
import connectroomies.model.entities.Habitacion;
import connectroomies.model.mappers.HabitacionMapper;
import connectroomies.services.HabitacionService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    @GetMapping
    public List<HabitacionDto> getAllHabitaciones() {
        return habitacionService.findAll().stream()
                .map(HabitacionMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getHabitacionById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    HabitacionMapper.toDto(habitacionService.findById(id))
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Habitación no encontrada");
        }
    }

    @PostMapping("/vivienda/{viviendaId}")
    public ResponseEntity<?> createHabitacion(@PathVariable Long viviendaId, @RequestBody Habitacion habitacion) {
        try {
            habitacionService.newHabitacion(habitacion);
            return ResponseEntity.status(201).body("Habitación añadida correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        try {
            habitacionService.updateHabitacion(habitacion);
            return ResponseEntity.ok("Habitación actualizada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Habitación no encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHabitacion(@PathVariable Long id) {
        try {
            habitacionService.deleteHabitacion(id);
            return ResponseEntity.ok("Habitación eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("No se ha encontrado la habitación");
        }
    }


}
