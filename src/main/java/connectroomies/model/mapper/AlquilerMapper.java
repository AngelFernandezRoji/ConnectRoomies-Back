package connectroomies.model.mapper;

import connectroomies.model.dtos.AlquilerDto;
import connectroomies.model.entities.Alquiler;

public class AlquilerMapper {

    public static AlquilerDto toDto(Alquiler alquiler) {
        AlquilerDto dto = new AlquilerDto();
        dto.setId(alquiler.getId());
        dto.setFechaInicio(alquiler.getFechaInicio());
        dto.setFechaFin(alquiler.getFechaFin());
        dto.setEstado(alquiler.getEstado());
        
        dto.setInquilinoId(
            alquiler.getInquilino() != null ? alquiler.getInquilino().getId() : null
        );

        dto.setViviendaId(
            alquiler.getVivienda() != null ? alquiler.getVivienda().getId() : null
        );

        dto.setHabitacionId(
            alquiler.getHabitacion() != null ? alquiler.getHabitacion().getId() : null
        );
        
        return dto;
    }
}
