package connectroomies.service;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.entities.Vivienda;
import connectroomies.model.repository.ViviendaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViviendaService {
	
	private final ViviendaRepository viviendaRepository;
	
	public List<Vivienda> findAll() {
		return viviendaRepository.findAll();
	}
	
	public Vivienda findById(Long id) {
        return viviendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vivienda no encontrada"));
    }

    public Vivienda save(Vivienda vivienda) {
        return viviendaRepository.save(vivienda);
    }

    public Vivienda update(Long id, Vivienda vivienda) {
        Vivienda v = findById(id);
        v.setTitulo(vivienda.getTitulo());
        v.setTipo(vivienda.getTipo());
        v.setDireccion(vivienda.getDireccion());
        v.setLocalidad(vivienda.getLocalidad());
        v.setProvincia(vivienda.getProvincia());
        v.setCodigo_postal(vivienda.getCodigo_postal());
        v.setPrecio(vivienda.getPrecio());
        v.setDisponible(vivienda.getDisponible());
        return viviendaRepository.save(v);
    }

    public void delete(Long id) {
        viviendaRepository.deleteById(id);
    }
	
}
