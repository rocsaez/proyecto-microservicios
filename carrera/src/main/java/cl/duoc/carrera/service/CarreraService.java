package cl.duoc.carrera.service;

import cl.duoc.carrera.model.CarreraModel;
import cl.duoc.carrera.repository.CarreraRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarreraService {

    private final CarreraRepository repository;

    public CarreraService(CarreraRepository repository) {
        this.repository = repository;
    }

    public CarreraModel guardar(CarreraModel carrera) {
        return repository.save(carrera);
    }

    public List<CarreraModel> obtenerTodas() {
        return repository.findAll();
    }

    public Optional<CarreraModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public CarreraModel actualizar(Long id, CarreraModel nuevosDatos) {
        Optional<CarreraModel> existente = repository.findById(id);
        if (existente.isPresent()) {
            CarreraModel carrera = existente.get();
            carrera.setNombre(nuevosDatos.getNombre());
            carrera.setFacultad(nuevosDatos.getFacultad());
            carrera.setSemestres(nuevosDatos.getSemestres());
            return repository.save(carrera);
        }
        return null;
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}