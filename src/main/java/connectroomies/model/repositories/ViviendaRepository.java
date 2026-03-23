package connectroomies.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import connectroomies.model.entities.Vivienda;

public interface ViviendaRepository extends JpaRepository<Vivienda, Long>{
    
	List<Vivienda> findByPropietarioId(Long propietarioId);
	
	List<Vivienda> findByLocalidad(String localidad);

    List<Vivienda> findByProvincia(String provincia);

    List<Vivienda> findByPrecioLessThanEqual(Double precioMax);

    List<Vivienda> findByLocalidadAndPrecioLessThanEqual(String localidad, Double precioMax);

    List<Vivienda> findByProvinciaAndPrecioLessThanEqual(String provincia, Double precioMax);

    List<Vivienda> findByLocalidadAndProvinciaAndPrecioLessThanEqual(
            String localidad, String provincia, Double precioMax);
}
