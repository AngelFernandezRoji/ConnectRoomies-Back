package connectroomies.model.mappers;

import java.util.Collections;
import java.util.List;
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
        dto.setDireccion(vivienda.getDireccion());
        dto.setLocalidad(vivienda.getLocalidad());
        dto.setProvincia(vivienda.getProvincia());
        dto.setCodigoPostal(vivienda.getCodigoPostal());
        dto.setPrecio(vivienda.getPrecio());
        dto.setDisponible(vivienda.getDisponible());
        dto.setFechaCreacion(vivienda.getFechaCreacion());
        dto.setDescripcion(vivienda.getDescripcion());
        dto.setMetros(vivienda.getMetros());
        dto.setBanos(vivienda.getBanos());
        dto.setHabitacionesTotales(vivienda.getHabitacionesTotales());
        dto.setNormas(vivienda.getNormas());
        
        dto.setComodidades(
        vivienda.getComodidades() != null && !vivienda.getComodidades().isBlank()
            ? List.of(vivienda.getComodidades().split(";"))
            : Collections.emptyList()
);
        
        PropietarioDto propietarioDto = new PropietarioDto();
        propietarioDto.setId(vivienda.getPropietario().getId());
        propietarioDto.setNombre(vivienda.getPropietario().getNombre());
        propietarioDto.setEmail(vivienda.getPropietario().getEmail());
        propietarioDto.setTelefono(vivienda.getPropietario().getTelefono());
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

    public static Vivienda toEntity(ViviendaDto dto) {
        Vivienda vivienda = new Vivienda();

        vivienda.setTitulo(dto.getTitulo());
        vivienda.setTipo(dto.getTipo());
        vivienda.setDireccion(dto.getDireccion());
        vivienda.setLocalidad(dto.getLocalidad());
        vivienda.setProvincia(dto.getProvincia());
        vivienda.setCodigoPostal(dto.getCodigoPostal());
        vivienda.setPrecio(dto.getPrecio());
        vivienda.setDescripcion(dto.getDescripcion());
        vivienda.setMetros(dto.getMetros());
        vivienda.setBanos(dto.getBanos());
        vivienda.setHabitacionesTotales(dto.getHabitacionesTotales());
        vivienda.setNormas(dto.getNormas());

        vivienda.setComodidades(
            dto.getComodidades() != null
                ? String.join(";", dto.getComodidades())
                : null
        );

        return vivienda;
    }
}
