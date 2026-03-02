package connectroomies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import connectroomies.model.entities.Alquiler;
import connectroomies.model.repositories.AlquilerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlquilerServiceImpl implements AlquilerService {

    private final AlquilerRepository alquilerRepository;

    //CRUD
    @Override
    public List<Alquiler> findAll() {
        // TODO Auto-generated method stub
       return alquilerRepository.findAll();
    }
    @Override
    public Alquiler findById(Long id) {
        // TODO Auto-generated method stub
        return alquilerRepository.findById(id).orElse(null);
    }
    @Override  //INCOMPLETO
    public Alquiler newAlquiler(Alquiler alquiler) {
        // TODO Auto-generated method stub
        return alquilerRepository.save(alquiler); //!!!HAY QUE HACER VALIDACIONES PARA INSERTAR DATOS (INQUILINO, VIVIENDA, HABITACIÓN)!!!
    }
    @Override
    public void deleteAlquiler(Long id) {
        // TODO Auto-generated method stub
        alquilerRepository.deleteById(id);
    }
    
    
}
