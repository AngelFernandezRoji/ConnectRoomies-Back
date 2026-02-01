package connectroomies.service;

import java.util.List;

import connectroomies.model.entities.Alquiler;

public interface AlquilerService {

    List<Alquiler> findAll();
    Alquiler findById(Long id);
    Alquiler save(Alquiler alquiler);
    void delete(Long id);

}
