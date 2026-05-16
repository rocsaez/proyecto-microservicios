package cl.duoc.sistema_inscripcion.service;

import cl.duoc.sistema_inscripcion.model.SistemaInscripcionesModel;
import cl.duoc.sistema_inscripcion.respository.SistemaInscripcionesRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SistemaInscripcionesService {

    private final SistemaInscripcionesRepository repository;

    public SistemaInscripcionesService(SistemaInscripcionesRepository repository) {
        this.repository = repository;
    }

    public SistemaInscripcionesModel guardar(SistemaInscripcionesModel inscripcion) {
        return repository.save(inscripcion);
    }

    public List<SistemaInscripcionesModel> obtenerTodas() {
        return repository.findAll();
    }

    public Optional<SistemaInscripcionesModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public SistemaInscripcionesModel actualizar(Long id, SistemaInscripcionesModel nuevosDatos) {
        Optional<SistemaInscripcionesModel> existente = repository.findById(id);
        
        if (existente.isPresent()) {
            SistemaInscripcionesModel ins = existente.get();
            ins.setRutEstudiante(nuevosDatos.getRutEstudiante());
            ins.setNombreAsignatura(nuevosDatos.getNombreAsignatura());
            ins.setPeriodo(nuevosDatos.getPeriodo());
            return repository.save(ins);
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