package connectroomies.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import connectroomies.model.dtos.AlquilerDto;
import connectroomies.model.entities.Alquiler;
import connectroomies.model.mapper.AlquilerMapper;
import connectroomies.service.AlquilerService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService alquilerService;

    @GetMapping
    public List<AlquilerDto> getAll() {
        return alquilerService.findAll().stream()
                .map(AlquilerMapper::toDto).toList();
    }

    @PostMapping
    public Alquiler create(@RequestBody Alquiler alquiler) {
        return alquilerService.save(alquiler);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        alquilerService.delete(id);
    }

    
}
