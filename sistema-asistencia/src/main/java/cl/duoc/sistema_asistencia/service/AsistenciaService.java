package cl.duoc.sistema_asistencia.service;

import cl.duoc.sistema_asistencia.model.AsistenciaModel;
import cl.duoc.sistema_asistencia.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public List<AsistenciaModel> listarTodas() {
        return asistenciaRepository.findAll();
    }

    public AsistenciaModel registrarAsistencia(AsistenciaModel asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    public void eliminarAsistencia(Long id) {
    asistenciaRepository.deleteById(id);
}




}
