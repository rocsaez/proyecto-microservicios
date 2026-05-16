package cl.duoc.sistema_asistencia.service;

import cl.duoc.sistema_asistencia.model.AsistenciaModel;
import cl.duoc.sistema_asistencia.repository.AsistenciaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService {

    private final AsistenciaRepository repository;

    public AsistenciaService(AsistenciaRepository repository) {
        this.repository = repository;
    }

    public AsistenciaModel guardar(AsistenciaModel asistencia) {
        return repository.save(asistencia);
    }

    public List<AsistenciaModel> obtenerTodas() {
        return repository.findAll();
    }

    public Optional<AsistenciaModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public AsistenciaModel actualizar(Long id, AsistenciaModel nuevosDatos) {
        Optional<AsistenciaModel> existente = repository.findById(id);
        
        if (existente.isPresent()) {
            AsistenciaModel asistencia = existente.get();
            // Actualizamos los campos de tu modelo
            asistencia.setRutEstudiante(nuevosDatos.getRutEstudiante());
            asistencia.setCodigoClase(nuevosDatos.getCodigoClase());
            // No actualizamos fechaHora porque se genera sola al crear
            return repository.save(asistencia);
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