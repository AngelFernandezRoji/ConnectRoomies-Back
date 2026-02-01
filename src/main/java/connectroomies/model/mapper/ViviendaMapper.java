package connectroomies.model.mapper;

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
        dto.setPrecio(vivienda.getPrecio());
        dto.setDisponible(vivienda.getDisponible());
        dto.setFecha_creacion(vivienda.getFecha_creacion());
		return dto;
		
	}
}
