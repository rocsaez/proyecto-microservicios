package cl.duoc.asignaturas.service;

import cl.duoc.asignaturas.model.AsignaturaModel;
import cl.duoc.asignaturas.repository.AsignaturaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AsignaturaService {

    private final AsignaturaRepository repository;

    public AsignaturaService(AsignaturaRepository repository) {
        this.repository = repository;
    }

    public AsignaturaModel guardar(AsignaturaModel asignatura) {
        return repository.save(asignatura);
    }

    public List<AsignaturaModel> obtenerTodas() {
        return repository.findAll();
    }

    public Optional<AsignaturaModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public AsignaturaModel actualizar(Long id, AsignaturaModel nuevosDatos) {
        Optional<AsignaturaModel> existente = repository.findById(id);
        if (existente.isPresent()) {
            AsignaturaModel asig = existente.get();
            asig.setNombre(nuevosDatos.getNombre());
            asig.setSigla(nuevosDatos.getSigla());
            asig.setCreditos(nuevosDatos.getCreditos());
            return repository.save(asig);
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