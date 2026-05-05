package cl.duoc.evaluacion.service;

import cl.duoc.evaluacion.model.EvaluacionModel;
import cl.duoc.evaluacion.repository.EvaluacionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionInterface repository;

    public List<EvaluacionModel> listarTodas() {
        return repository.findAll();
    }

    public EvaluacionModel guardar(EvaluacionModel evaluacion) {
        return repository.save(evaluacion);
    }

    public void eliminar(Long id) {
    repository.deleteById(id); // Asegúrate de que NO diga "Unimplemented method"
}

    public EvaluacionModel actualizar(EvaluacionModel evaluacion) {
        return repository.save(evaluacion);
    }

    public EvaluacionModel obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}