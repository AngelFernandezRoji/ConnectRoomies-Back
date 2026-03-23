package connectroomies.services;

import java.util.List;

import connectroomies.model.dtos.HabitacionDto;
import connectroomies.model.dtos.ViviendaDto;
import connectroomies.model.entities.Usuario;
import connectroomies.model.entities.Vivienda;

public interface ViviendaService {
	
	//CRUD
	List<Vivienda> findAll();
	Vivienda findById(Long id);
	Vivienda newVivienda(Vivienda vivienda, Usuario creador);
	Vivienda updateVivienda(Vivienda vienda, Usuario usuario);
	void deleteVivienda(Long id, Usuario usuario);
	
	//CONSULTAS PROPIAS
	List<Vivienda> buscarViviendas(String localidad, String provincia, Double precioMax);
	List<ViviendaDto> getViviendasByPropietario(Long propietarioId);
	List<HabitacionDto> getHabitacionesByVivienda(Long viviendaId);
	
}
