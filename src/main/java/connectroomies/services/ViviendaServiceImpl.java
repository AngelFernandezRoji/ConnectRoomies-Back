package connectroomies.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.dtos.HabitacionDto;
import connectroomies.model.dtos.ImagenViviendaDto;
import connectroomies.model.dtos.ViviendaDto;
import connectroomies.model.entities.Habitacion;
import connectroomies.model.entities.ImagenVivienda;
import connectroomies.model.entities.Usuario;
import connectroomies.model.entities.Vivienda;
import connectroomies.model.mappers.HabitacionMapper;
import connectroomies.model.mappers.ViviendaMapper;
import connectroomies.model.repositories.ViviendaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViviendaServiceImpl implements ViviendaService {

	private final ViviendaRepository viviendaRepository;
	
	//CRUD
	@Override
	public List<Vivienda> findAll() {
		// TODO Auto-generated method stub
		return viviendaRepository.findAll();
	}
	@Override
	public Vivienda findById(Long id) {
		// TODO Auto-generated method stub
		return viviendaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));
	}
	@Override
	public Vivienda newVivienda(ViviendaDto dto, Usuario creador) {
		if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
			throw new IllegalArgumentException("El título es obligatorio");
		}
		if (dto.getPrecio() == null || dto.getPrecio() <= 0) {
			throw new IllegalArgumentException("El precio debe ser mayor que 0");
		}
		boolean isPropietario = creador.getRoles().stream()
            .anyMatch(r -> r.getNombre().equals("PROPIETARIO") || r.getNombre().equals("ADMIN"));
		
		if (!isPropietario) {
			throw new RuntimeException("Solo los propietarios pueden publicar viviendas");
		}
		
		Vivienda vivienda = ViviendaMapper.toEntity(dto);
		
		List<ImagenVivienda> imagenes = new ArrayList<>();
		if(dto.getImagenesVivienda() != null && !dto.getImagenesVivienda().isEmpty()) {
			for (ImagenViviendaDto imagenDto : dto.getImagenesVivienda()) {
				ImagenVivienda imagen = new ImagenVivienda();
				imagen.setUrlImg(imagenDto.getUrlImg());
				imagen.setId(imagenDto.getId());
				imagen.setVivienda(vivienda);
				imagenes.add(imagen);
			}
			
			vivienda.setImagenesVivienda(imagenes);
		}
		
		vivienda.setPropietario(creador);
		vivienda.setDisponible(1);

		return viviendaRepository.save(vivienda);
	}
	@Override
	public Vivienda updateVivienda(Long id, ViviendaDto dto, Usuario usuario) {
		Vivienda currentVivienda = viviendaRepository.findById(id)
									.orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));
		
		boolean isPropietario = currentVivienda.getPropietario().getId().equals(usuario.getId());
		boolean isAdmin = usuario.getRoles().stream()
		        .anyMatch(r -> r.getNombre().equals("ADMIN"));

		if (!isPropietario && !isAdmin) {
		    throw new RuntimeException("No tienes permisos para modificar esta vivienda");
		}
		
		if (dto.getTitulo() != null) currentVivienda.setTitulo(dto.getTitulo());
		if (dto.getTipo() != null) currentVivienda.setTipo(dto.getTipo());
		if (dto.getDireccion() != null) currentVivienda.setDireccion(dto.getDireccion());
		if (dto.getLocalidad() != null) currentVivienda.setLocalidad(dto.getLocalidad());
		if (dto.getProvincia() != null) currentVivienda.setProvincia(dto.getProvincia());
		if (dto.getCodigoPostal() != null) currentVivienda.setCodigoPostal(dto.getCodigoPostal());
		if (dto.getPrecio() != null) currentVivienda.setPrecio(dto.getPrecio());
		if (dto.getDescripcion() != null) currentVivienda.setDescripcion(dto.getDescripcion());
		if (dto.getMetros() != null) currentVivienda.setMetros(dto.getMetros());
		if (dto.getBanos() != null) currentVivienda.setBanos(dto.getBanos());
		if (dto.getHabitacionesTotales() != null) currentVivienda.setHabitacionesTotales(dto.getHabitacionesTotales());
		if (dto.getNormas() != null) currentVivienda.setNormas(dto.getNormas());
		if (dto.getComodidades() != null) {
			currentVivienda.setComodidades(String.join(";", dto.getComodidades()));
		}
		if (dto.getDisponible() != null) {
			currentVivienda.setDisponible(dto.getDisponible());
		}
		
		return viviendaRepository.save(currentVivienda);

	}
	@Override
	public void deleteVivienda(Long id, Usuario usuario) {
		Vivienda vivienda = viviendaRepository.findById(id)
            				.orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));
    	
		boolean isPropietario = vivienda.getPropietario().getId().equals(usuario.getId());
	    boolean isAdmin = usuario.getRoles().stream().anyMatch(r -> r.getNombre().equals("ADMIN"));
	    
	    if(!isPropietario && !isAdmin) {
	    	 throw new RuntimeException("No tienes permisos para ELIMINAR VIVIENDA");
	    }
		
		viviendaRepository.delete(vivienda);
	}
	
	
	
	@Override
	public List<Vivienda> buscarViviendas(String localidad, String provincia, Double precioMax) {
		 if(localidad != null && provincia != null && precioMax != null) {
	            return viviendaRepository.findByLocalidadAndProvinciaAndPrecioLessThanEqual(
	                    localidad, provincia, precioMax);
	        } else if(localidad != null && provincia != null) {
	            return viviendaRepository.findByLocalidadAndProvinciaAndPrecioLessThanEqual(
	                    localidad, provincia, Double.MAX_VALUE);
	        } else if(localidad != null && precioMax != null) {
	            return viviendaRepository.findByLocalidadAndPrecioLessThanEqual(localidad, precioMax);
	        } else if(provincia != null && precioMax != null) {
	            return viviendaRepository.findByProvinciaAndPrecioLessThanEqual(provincia, precioMax);
	        } else if(localidad != null) {
	            return viviendaRepository.findByLocalidad(localidad);
	        } else if(provincia != null) {
	            return viviendaRepository.findByProvincia(provincia);
	        } else if(precioMax != null) {
	            return viviendaRepository.findByPrecioLessThanEqual(precioMax);
	        } else {
	            return viviendaRepository.findAll();
	        }
	}
	@Override
	public List<HabitacionDto> getHabitacionesByVivienda(Long viviendaId) {
		// TODO Auto-generated method stub
		return viviendaRepository.findById(viviendaId)
                .orElseThrow(() -> new RuntimeException("Vivienda no encontrada"))
                .getHabitaciones()
                .stream()
                .map(HabitacionMapper::toDto)
                .toList();
	}
	@Override
	public List<ViviendaDto> getViviendasByPropietario(Long propietarioId) {
		return viviendaRepository.findByPropietarioId(propietarioId)
				.stream()
				.map(ViviendaMapper::toDto)
				.toList();
	}
	
	
}
