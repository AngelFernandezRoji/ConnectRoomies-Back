package connectroomies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.entities.Habitacion;
import connectroomies.model.entities.Vivienda;
import connectroomies.model.repository.HabitacionRepository;
import connectroomies.model.repository.ViviendaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final ViviendaRepository viviendaRepository;

    public List<Habitacion> findAll() {
        return habitacionRepository.findAll();
    }

    public Habitacion findById(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
    }

    public Habitacion save(Habitacion habitacion, Long viviendaId) {
        Vivienda vivienda = viviendaRepository.findById(viviendaId)
                .orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));

        habitacion.setVivienda(vivienda);
        return habitacionRepository.save(habitacion);
    }

    public Habitacion update(Long id, Habitacion habitacion) {
        Habitacion h = findById(id);
        h.setNombre(habitacion.getNombre());
        h.setDescripcion(habitacion.getDescripcion());
        h.setPrecio(habitacion.getPrecio());
        h.setDisponible(habitacion.getDisponible());
        return habitacionRepository.save(h);
    }

    public void delete(Long id) {
        habitacionRepository.deleteById(id);
    }

}
