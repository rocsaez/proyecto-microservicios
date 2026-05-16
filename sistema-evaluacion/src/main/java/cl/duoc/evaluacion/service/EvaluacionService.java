package cl.duoc.evaluacion.service;

import cl.duoc.evaluacion.model.EvaluacionModel;
import cl.duoc.evaluacion.repository.EvaluacionInterface;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionService {

    private final EvaluacionInterface repository;

    public EvaluacionService(EvaluacionInterface repository) {
        this.repository = repository;
    }

    public EvaluacionModel guardar(EvaluacionModel evaluacion) {
        return repository.save(evaluacion);
    }

    public List<EvaluacionModel> obtenerTodas() {
        return repository.findAll();
    }

    public Optional<EvaluacionModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public EvaluacionModel actualizar(Long id, EvaluacionModel nuevosDatos) {
        Optional<EvaluacionModel> existente = repository.findById(id);
        
        if (existente.isPresent()) {
            EvaluacionModel eval = existente.get();
            eval.setNombreEstudiante(nuevosDatos.getNombreEstudiante());
            eval.setAsignatura(nuevosDatos.getAsignatura());
            eval.setNota(nuevosDatos.getNota());
            return repository.save(eval);
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