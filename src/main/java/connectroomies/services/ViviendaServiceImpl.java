package connectroomies.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import connectroomies.model.entities.Vivienda;
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
		return viviendaRepository.findById(id).orElse(null);
	}
	@Override
	public Vivienda newVivienda(Vivienda vivienda) {
		// TODO Auto-generated method stub
		return viviendaRepository.save(vivienda);
	}
	@Override
	public Vivienda updateVivienda(Vivienda vivienda) {
		Vivienda currentVivienda = findById(vivienda.getId());
		if (currentVivienda != null ) {
			currentVivienda.setTitulo(vivienda.getTitulo());
		    currentVivienda.setTipo(vivienda.getTipo());
		    currentVivienda.setDireccion(vivienda.getDireccion());
		    currentVivienda.setLocalidad(vivienda.getLocalidad());
		    currentVivienda.setProvincia(vivienda.getProvincia());
		    currentVivienda.setCodigoPostal(vivienda.getCodigoPostal());
		    currentVivienda.setPrecio(vivienda.getPrecio());
		    currentVivienda.setDisponible(vivienda.getDisponible());
		    return viviendaRepository.save(currentVivienda);
		} else {
			return null;
		}

	}
	@Override
	public boolean deleteVivienda(Long id) {
		// TODO Auto-generated method stub
		if(viviendaRepository.existsById(id)) {
			viviendaRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	
}
