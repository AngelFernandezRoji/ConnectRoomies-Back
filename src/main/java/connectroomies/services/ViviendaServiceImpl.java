package connectroomies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.dtos.HabitacionDto;
import connectroomies.model.dtos.ViviendaDto;
import connectroomies.model.entities.Habitacion;
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
	public Vivienda newVivienda(Vivienda vivienda, Usuario creador) {
		if (vivienda.getTitulo() == null || vivienda.getTitulo().isBlank()) {
			throw new IllegalArgumentException("El título es obligatorio");
		}
		if (vivienda.getPrecio() == null || vivienda.getPrecio() <= 0) {
			throw new IllegalArgumentException("El precio debe ser mayor que 0");
		}
		if (creador.getRoles().stream().noneMatch(r -> r.getNombre().equals("PROPIETARIO") || r.getNombre().equals("ADMIN"))) {
	        throw new RuntimeException("Solo los propietarios pueden publicar viviendas");
	    }
		vivienda.setPropietario(creador);
		vivienda.setDisponible(1);

		return viviendaRepository.save(vivienda);
	}
	@Override
	public Vivienda updateVivienda(Vivienda vivienda, Usuario usuario) {
		Vivienda currentVivienda = viviendaRepository.findById(vivienda.getId())
									.orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));
		
		boolean isPropietario = currentVivienda.getPropietario().getId().equals(usuario.getId());
		boolean isAdmin = usuario.getRoles().stream()
		        .anyMatch(r -> r.getNombre().equals("ADMIN"));

		if (!isPropietario && !isAdmin) {
		    throw new RuntimeException("No tienes permisos para modificar esta vivienda");
		}
		
		currentVivienda.setTitulo(vivienda.getTitulo());
		currentVivienda.setTipo(vivienda.getTipo());
		currentVivienda.setDireccion(vivienda.getDireccion());
		currentVivienda.setLocalidad(vivienda.getLocalidad());
		currentVivienda.setProvincia(vivienda.getProvincia());
		currentVivienda.setCodigoPostal(vivienda.getCodigoPostal());
		currentVivienda.setPrecio(vivienda.getPrecio());
		currentVivienda.setDisponible(vivienda.getDisponible());
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
