package cl.duoc.sistema_asistencia.service;

import cl.duoc.sistema_asistencia.dto.AsistenciaCreateDTO;
import cl.duoc.sistema_asistencia.dto.AsistenciaDTO;
import cl.duoc.sistema_asistencia.exceptions.RecursoNoEncontradoException;
import cl.duoc.sistema_asistencia.model.Asistencia;
import cl.duoc.sistema_asistencia.repository.AsistenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsistenciaService {

    private static final Logger log = LoggerFactory.getLogger(AsistenciaService.class);

    @Autowired
    private AsistenciaRepository repository;

    public List<AsistenciaDTO> findAll() {
        log.info("Consultando todas las asistencias");
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AsistenciaDTO findById(Long id) {
        log.info("Buscando asistencia id={}", id);
        Asistencia a = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asistencia no encontrada: " + id));
        log.info("Asistencia encontrada: rutEstudiante={}, clase={}", a.getRutEstudiante(), a.getCodigoClase());
        return toDTO(a);
    }

    public AsistenciaDTO crear(AsistenciaCreateDTO dto) {
        log.info("Creando asistencia para estudiante rut={}", dto.getRutEstudiante());
        Asistencia a = new Asistencia();
        a.setRutEstudiante(dto.getRutEstudiante());
        a.setCodigoClase(dto.getCodigoClase()); // Corregido sin tilde
        
        Asistencia guardado = repository.save(a);
        log.info("Asistencia registrada id={}", guardado.getId());
        return toDTO(guardado);
    }

    public AsistenciaDTO actualizar(Long id, AsistenciaCreateDTO dto) {
        log.info("Actualizando asistencia id={}", id);
        Asistencia a = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asistencia no encontrada: " + id));
        
        a.setRutEstudiante(dto.getRutEstudiante());
        a.setCodigoClase(dto.getCodigoClase()); // Corregido sin tilde
        return toDTO(repository.save(a));
    }

    public void eliminar(Long id) {
        log.info("Eliminando asistencia id={}", id);
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Asistencia no encontrada: " + id);
        }
        repository.deleteById(id);
        log.info("Asistencia id={} eliminada", id);
    }

    private AsistenciaDTO toDTO(Asistencia a) {
        return new AsistenciaDTO(
                a.getId(),
                a.getRutEstudiante(),
                a.getCodigoClase(), // Corregido sin tilde
                a.getFechaHora()
        );
    }
}