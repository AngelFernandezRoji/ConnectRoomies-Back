package connectroomies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.entities.Alquiler;

@Service
public interface AlquilerService {

    List<Alquiler> findAll();
    Alquiler findById(Long id);
    Alquiler newAlquiler(Alquiler alquiler);
    void deleteAlquiler(Long id);

}
