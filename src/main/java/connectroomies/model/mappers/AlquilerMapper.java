package connectroomies.model.mappers;

import connectroomies.model.dtos.AlquilerDto;
import connectroomies.model.dtos.HabitacionAlquilerDto;
import connectroomies.model.dtos.InquilinoDto;
import connectroomies.model.dtos.ViviendaAlquilerDto;
import connectroomies.model.entities.Alquiler;
import connectroomies.model.enums.EstadoAlquiler;

public class AlquilerMapper {

    public static AlquilerDto toDto(Alquiler alquiler) {
        AlquilerDto dto = new AlquilerDto();
        dto.setId(alquiler.getId());
        dto.setFechaInicio(alquiler.getFechaInicio());
        dto.setFechaFin(alquiler.getFechaFin());
        dto.setEstado(EstadoAlquiler.ACTIVO);
        
        if (alquiler.getInquilino() != null) {
            InquilinoDto mini = new InquilinoDto();
            mini.setId(alquiler.getInquilino().getId());
            mini.setNombre(alquiler.getInquilino().getNombre());
            dto.setInquilino(mini);
        }

        if (alquiler.getVivienda() != null) {
            ViviendaAlquilerDto mini = new ViviendaAlquilerDto();
            mini.setId(alquiler.getVivienda().getId());
            mini.setTitulo(alquiler.getVivienda().getTitulo());
            dto.setVivienda(mini);
        }

        if (alquiler.getHabitacion() != null) {
            HabitacionAlquilerDto mini = new HabitacionAlquilerDto();
            mini.setId(alquiler.getHabitacion().getId());
            mini.setNombre(alquiler.getHabitacion().getNombre());
            dto.setHabitacion(mini);
        }
        
        return dto;
    }
}
