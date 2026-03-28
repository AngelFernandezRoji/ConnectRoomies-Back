package connectroomies.model.mappers;

import java.util.Collections;
import java.util.stream.Collectors;

import connectroomies.model.dtos.PropietarioDto;
import connectroomies.model.dtos.ViviendaDto;
import connectroomies.model.entities.Vivienda;

public class ViviendaMapper {

	public static ViviendaDto toDto(Vivienda vivienda) {
		ViviendaDto dto = new ViviendaDto();
        dto.setId(vivienda.getId());
        dto.setTitulo(vivienda.getTitulo());
        dto.setTipo(vivienda.getTipo());
        dto.setLocalidad(vivienda.getLocalidad());
        dto.setProvincia(vivienda.getProvincia());
        dto.setDireccion(vivienda.getDireccion());
        dto.setPrecio(vivienda.getPrecio());
        dto.setDisponible(vivienda.getDisponible());
        dto.setFechaCreacion(vivienda.getFechaCreacion());
        dto.setDescripcion(vivienda.getDescripcion());
        dto.setMetros(vivienda.getMetros());
        dto.setBanos(vivienda.getBanos());
        dto.setHabitacionesTotales(vivienda.getHabitacionesTotales());
        dto.setNormas(vivienda.getNormas());
        
        PropietarioDto propietarioDto = new PropietarioDto();
        propietarioDto.setId(vivienda.getPropietario().getId());
        propietarioDto.setNombre(vivienda.getPropietario().getNombre());
        dto.setPropietario(propietarioDto);
        
        dto.setImagenesVivienda(
                vivienda.getImagenesVivienda() != null
                    ? vivienda.getImagenesVivienda()
                        .stream()
                        .map(ImagenViviendaMapper::toDto)
                        .collect(Collectors.toList())
                    : Collections.emptyList()
            );        
		return dto;
		
	}
}
