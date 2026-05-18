package cl.duoc.sistema_asistencia.service;

import cl.duoc.sistema_asistencia.client.EstudianteClient;
import cl.duoc.sistema_asistencia.client.InscripcionClient;
import cl.duoc.sistema_asistencia.dto.AsistenciaDTO;
import cl.duoc.sistema_asistencia.dto.AsistenciaCreateDTO;
import cl.duoc.sistema_asistencia.model.AsistenciaModel;
import cl.duoc.sistema_asistencia.repository.AsistenciaRepository;
import cl.duoc.sistema_asistencia.exceptions.RecursoNoEncontradoException;
import cl.duoc.sistema_asistencia.exceptions.ServicioNoDisponibleException;
import feign.FeignException;
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

    @Autowired
    private EstudianteClient estudianteClient;

    @Autowired
    private InscripcionClient inscripcionClient;

    public AsistenciaDTO guardar(AsistenciaCreateDTO dto) {
        log.info("Iniciando registro de asistencia para RUT: {}", dto.getRutEstudiante());
        
        // 1. Validar existencia del estudiante
        validarEstudiante(dto.getRutEstudiante());

        // 2. Validar que tenga inscripciones
        validarInscripcion(dto.getRutEstudiante());

        AsistenciaModel asistencia = new AsistenciaModel();
        asistencia.setRutEstudiante(dto.getRutEstudiante());
        asistencia.setCodigoClase(dto.getCodigoClase());

        AsistenciaModel guardado = repository.save(asistencia);
        log.info("Asistencia registrada exitosamente. ID: {}", guardado.getId());
        
        return convertirADTO(guardado);
    }

    public AsistenciaDTO actualizar(Long id, AsistenciaCreateDTO dto) {
        AsistenciaModel asistencia = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asistencia no encontrada con ID: " + id));
        
        validarEstudiante(dto.getRutEstudiante());
        validarInscripcion(dto.getRutEstudiante());

        asistencia.setRutEstudiante(dto.getRutEstudiante());
        asistencia.setCodigoClase(dto.getCodigoClase());
        
        return convertirADTO(repository.save(asistencia));
    }

    private void validarEstudiante(String rut) {
        try {
            log.info("Consultando Micro Estudiantes por RUT: {}", rut);
            estudianteClient.buscarPorRut(rut);
        } catch (FeignException.NotFound e) {
            log.warn("RUT {} no encontrado en Estudiantes", rut);
            throw new RecursoNoEncontradoException("El estudiante con RUT " + rut + " no existe.");
        } catch (FeignException e) {
            log.error("Error de conexión con Estudiantes: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Estudiantes no disponible.");
        }
    }

    private void validarInscripcion(String rut) {
        try {
            log.info("Validando inscripciones para RUT: {}", rut);
            var inscripciones = inscripcionClient.obtenerInscripcionesPorRut(rut);
            if (inscripciones == null || inscripciones.isEmpty()) {
                log.warn("El estudiante {} no tiene materias inscritas", rut);
                throw new RecursoNoEncontradoException("El estudiante no registra inscripciones vigentes.");
            }
        } catch (FeignException.NotFound e) {
            log.warn("Inscripciones para RUT {} no encontradas", rut);
            throw new RecursoNoEncontradoException("No se encontraron registros de inscripción para este RUT.");
        } catch (FeignException e) {
            log.error("Error de conexión con Inscripciones: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Inscripciones no disponible.");
        }
    }

    public List<AsistenciaDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public AsistenciaDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asistencia no encontrada con ID: " + id));
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Asistencia ID {} eliminada", id);
            return true;
        }
        return false;
    }

    private AsistenciaDTO convertirADTO(AsistenciaModel model) {
        return new AsistenciaDTO(
            model.getId(),
            model.getRutEstudiante(),
            model.getCodigoClase(),
            model.getFechaHora()
        );
    }
}