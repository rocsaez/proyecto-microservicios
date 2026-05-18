package cl.duoc.evaluacion.service;

import cl.duoc.evaluacion.client.InscripcionClient;
import cl.duoc.evaluacion.dto.EvaluacionDTO;
import cl.duoc.evaluacion.dto.EvaluacionCreateDTO;
import cl.duoc.evaluacion.model.EvaluacionModel;
import cl.duoc.evaluacion.repository.EvaluacionInterface;
import cl.duoc.evaluacion.exceptions.RecursoNoEncontradoException;
import cl.duoc.evaluacion.exceptions.ServicioNoDisponibleException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluacionService {

    private static final Logger log = LoggerFactory.getLogger(EvaluacionService.class);

    @Autowired
    private EvaluacionInterface repository;

    @Autowired
    private InscripcionClient inscripcionClient;

    public EvaluacionDTO guardar(EvaluacionCreateDTO dto) {
        log.info("Registrando nueva evaluación para: {} en {}", dto.getNombreEstudiante(), dto.getAsignatura());
        
        // Validación: El alumno debe estar inscrito para tener nota
        validarInscripcion(dto.getNombreEstudiante());

        EvaluacionModel model = new EvaluacionModel();
        model.setNombreEstudiante(dto.getNombreEstudiante());
        model.setAsignatura(dto.getAsignatura());
        model.setNota(dto.getNota());

        EvaluacionModel guardado = repository.save(model);
        log.info("Evaluación guardada exitosamente con ID: {}", guardado.getId());
        
        return convertirADTO(guardado);
    }

    public EvaluacionDTO actualizar(Long id, EvaluacionCreateDTO dto) {
        EvaluacionModel model = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evaluación no encontrada con ID: " + id));
        
        validarInscripcion(dto.getNombreEstudiante());

        model.setNombreEstudiante(dto.getNombreEstudiante());
        model.setAsignatura(dto.getAsignatura());
        model.setNota(dto.getNota());

        return convertirADTO(repository.save(model));
    }

    private void validarInscripcion(String nombre) {
        try {
            log.info("Verificando inscripciones en microservicio externo para: {}", nombre);
            var inscripciones = inscripcionClient.obtenerInscripcionesPorRut(nombre);
            
            if (inscripciones == null || inscripciones.isEmpty()) {
                log.warn("El estudiante {} no registra inscripciones", nombre);
                throw new RecursoNoEncontradoException("El estudiante " + nombre + " no registra inscripciones vigentes.");
            }
        } catch (FeignException.NotFound e) {
            log.warn("No se encontró registro de inscripciones para: {}", nombre);
            throw new RecursoNoEncontradoException("No se encontraron registros de inscripción.");
        } catch (FeignException e) {
            log.error("Error de conexión con micro de Inscripciones: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Inscripciones no disponible.");
        }
    }

    public List<EvaluacionDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public EvaluacionDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evaluación no encontrada con ID: " + id));
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Evaluación ID {} eliminada", id);
            return true;
        }
        return false;
    }

    private EvaluacionDTO convertirADTO(EvaluacionModel model) {
        return new EvaluacionDTO(
            model.getId(),
            model.getNombreEstudiante(),
            model.getAsignatura(),
            model.getNota()
        );
    }
}