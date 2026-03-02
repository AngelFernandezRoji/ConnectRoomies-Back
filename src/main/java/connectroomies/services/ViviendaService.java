package connectroomies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.entities.Vivienda;

@Service
public interface ViviendaService {
	
	List<Vivienda> findAll();
	Vivienda findById(Long id);
	Vivienda newVivienda(Vivienda vivienda);
	Vivienda updateVivienda(Vivienda vienda);
	boolean deleteVivienda(Long id);
	
	
}
