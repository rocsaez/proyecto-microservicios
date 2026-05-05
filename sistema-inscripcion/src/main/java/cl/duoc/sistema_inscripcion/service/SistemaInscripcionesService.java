package cl.duoc.sistema_inscripcion.service;

import cl.duoc.sistema_inscripcion.model.SistemaInscripcionesModel;
import cl.duoc.sistema_inscripcion.respository.SistemaInscripcionesRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SistemaInscripcionesService {

    private final SistemaInscripcionesRepository repository;

    public SistemaInscripcionesService(SistemaInscripcionesRepository repository) {
        this.repository = repository;
    }

    public List<SistemaInscripcionesModel> listarTodas() {
        return repository.findAll();
    }

    public SistemaInscripcionesModel guardar(SistemaInscripcionesModel inscripcion) {
        return repository.save(inscripcion);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public SistemaInscripcionesModel actualizar(SistemaInscripcionesModel inscripcion) {
        return repository.save(inscripcion);
    }

    public SistemaInscripcionesModel obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}