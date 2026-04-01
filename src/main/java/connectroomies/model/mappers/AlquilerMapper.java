package connectroomies.model.mappers;

import connectroomies.model.dtos.AlquilerDto;
import connectroomies.model.dtos.AlquilerResponseDto;
import connectroomies.model.dtos.HabitacionAlquilerDto;
import connectroomies.model.dtos.InquilinoDto;
import connectroomies.model.dtos.ViviendaAlquilerDto;
import connectroomies.model.entities.Alquiler;

public class AlquilerMapper {

    public static AlquilerDto toDto(Alquiler alquiler) {
        AlquilerDto dto = new AlquilerDto();

        dto.setId(alquiler.getId());
        dto.setFechaInicio(alquiler.getFechaInicio());
        dto.setFechaFin(alquiler.getFechaFin());
        dto.setDuracionMeses(alquiler.getDuracionMeses());
        dto.setMensaje(alquiler.getMensaje());
        dto.setEstado(alquiler.getEstado());
        
        if (alquiler.getInquilino() != null) {
            InquilinoDto inquilinoDto = new InquilinoDto();
            inquilinoDto.setId(alquiler.getInquilino().getId());
            inquilinoDto.setNombre(alquiler.getInquilino().getNombre());
            inquilinoDto.setApellidos(alquiler.getInquilino().getApellidos());
            inquilinoDto.setEmail(alquiler.getInquilino().getEmail());
            inquilinoDto.setTelefono(alquiler.getInquilino().getTelefono());
            dto.setInquilino(inquilinoDto);
        }

        if (alquiler.getVivienda() != null) {
            ViviendaAlquilerDto viviendaDto = new ViviendaAlquilerDto();
            viviendaDto.setId(alquiler.getVivienda().getId());
            viviendaDto.setTitulo(alquiler.getVivienda().getTitulo());
            dto.setVivienda(viviendaDto);
        }

        if (alquiler.getHabitacion() != null) {
            HabitacionAlquilerDto habitacionDto = new HabitacionAlquilerDto();
            habitacionDto.setId(alquiler.getHabitacion().getId());
            habitacionDto.setNombre(alquiler.getHabitacion().getNombre());
            dto.setHabitacion(habitacionDto);
        }
        
        return dto;
    }

    public static AlquilerResponseDto toResponseDto(Alquiler alquiler) {

        AlquilerResponseDto dto = new AlquilerResponseDto();
        dto.setId(alquiler.getId());
        dto.setFechaInicio(alquiler.getFechaInicio());
        dto.setFechaFin(alquiler.getFechaFin());
        dto.setDuracionMeses(alquiler.getDuracionMeses());
        dto.setMensaje(alquiler.getMensaje());

        if (alquiler.getEstado() != null) {
            dto.setEstado(alquiler.getEstado().name());
        }

        return dto;
    }
}
