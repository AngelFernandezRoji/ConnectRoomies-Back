package connectroomies.model.mapper;

import connectroomies.model.dtos.HabitacionDto;
import connectroomies.model.entities.Habitacion;

public class HabitacionMapper {

    public static HabitacionDto toDto(Habitacion habitacion) {
        HabitacionDto dto = new HabitacionDto();
        dto.setId(habitacion.getId());
        dto.setNombre(habitacion.getNombre());
        dto.setDescripcion(habitacion.getDescripcion());
        dto.setPrecio(habitacion.getPrecio());
        dto.setDisponible(habitacion.getDisponible());
        dto.setViviendaId(
            habitacion.getVivienda() != null ? habitacion.getVivienda().getId() : null
        );
        return dto;
    }
}
