package connectroomies.model.mappers;

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
        
        PropietarioDto propietarioDto = new PropietarioDto();
        propietarioDto.setId(vivienda.getPropietario().getId());
        propietarioDto.setNombre(vivienda.getPropietario().getNombre());
        dto.setPropietario(propietarioDto);
        
		return dto;
		
	}
}
