package cl.duoc.sistema_inscripcion.service;

import cl.duoc.sistema_inscripcion.client.AsignaturaClient;
import cl.duoc.sistema_inscripcion.client.EstudianteClient;
import cl.duoc.sistema_inscripcion.dto.InscripcionDTO;
import cl.duoc.sistema_inscripcion.dto.InscripcionCreateDTO;
import cl.duoc.sistema_inscripcion.model.SistemaInscripcionesModel;
import cl.duoc.sistema_inscripcion.repository.SistemaInscripcionesRepository;
import cl.duoc.sistema_inscripcion.exceptions.RecursoNoEncontradoException;
import cl.duoc.sistema_inscripcion.exceptions.ServicioNoDisponibleException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SistemaInscripcionesService {

    private static final Logger log = LoggerFactory.getLogger(SistemaInscripcionesService.class);

    @Autowired
    private SistemaInscripcionesRepository repository;

    @Autowired
    private EstudianteClient estudianteClient;
    
    @Autowired
    private AsignaturaClient asignaturaClient;

    public List<InscripcionDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public InscripcionDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inscripción no encontrada con ID: " + id));
    }

    public InscripcionDTO guardar(InscripcionCreateDTO dto) {
        log.info("Intentando guardar inscripción para RUT: {} en asignatura: {}", dto.getRutEstudiante(), dto.getNombreAsignatura());
        
        // 1. Validar si el alumno existe (Llamada externa)
        validarEstudiante(dto.getRutEstudiante());

        // 2. Validar si la asignatura existe (Llamada externa)
        validarAsignatura(dto.getNombreAsignatura());

        // 3. Validar duplicados (Local)
        validarDuplicado(dto);

        SistemaInscripcionesModel ins = new SistemaInscripcionesModel();
        ins.setRutEstudiante(dto.getRutEstudiante());
        ins.setNombreAsignatura(dto.getNombreAsignatura());
        ins.setPeriodo(dto.getPeriodo());

        SistemaInscripcionesModel guardado = repository.save(ins);
        log.info("Inscripción realizada con éxito. ID: {}", guardado.getId());
        
        return convertirADTO(guardado);
    }

    private void validarAsignatura(String sigla) {
        try {
            log.info("Validando existencia de asignatura: {}", sigla);
            asignaturaClient.buscarPorSigla(sigla);
        } catch (FeignException.NotFound e) {
            log.warn("Asignatura {} no encontrada en el sistema.", sigla);
            throw new RecursoNoEncontradoException("La asignatura '" + sigla + "' no existe.");
        } catch (FeignException e) {
            log.error("Error al conectar con microservicio de Asignaturas: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Asignaturas no disponible.");
        }
    }

    private void validarEstudiante(String rut) {
        try {
            log.info("Validando existencia de estudiante con RUT: {}", rut);
            estudianteClient.obtenerPorRut(rut);
        } catch (FeignException.NotFound e) {
            log.warn("Estudiante con RUT {} no encontrado.", rut);
            throw new RecursoNoEncontradoException("El estudiante con RUT " + rut + " no existe.");
        } catch (FeignException e) {
            log.error("Error al conectar con microservicio de Estudiantes: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Estudiantes no disponible.");
        }
    }

    private void validarDuplicado(InscripcionCreateDTO dto) {
        boolean existe = repository.existsByRutEstudianteAndNombreAsignaturaAndPeriodo(
            dto.getRutEstudiante(), 
            dto.getNombreAsignatura(), 
            dto.getPeriodo()
        );
        
        if (existe) {
            log.warn("Intento de inscripción duplicada para RUT: {} en {}", dto.getRutEstudiante(), dto.getNombreAsignatura());
            throw new RuntimeException("El estudiante ya está inscrito en esta asignatura para el periodo actual.");
        }
    }

    public InscripcionDTO actualizar(Long id, InscripcionCreateDTO dto) {
        SistemaInscripcionesModel ins = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inscripción no encontrada con ID: " + id));

        validarEstudiante(dto.getRutEstudiante());
        validarAsignatura(dto.getNombreAsignatura());
        
        ins.setRutEstudiante(dto.getRutEstudiante());
        ins.setNombreAsignatura(dto.getNombreAsignatura());
        ins.setPeriodo(dto.getPeriodo());

        return convertirADTO(repository.save(ins));
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Inscripción ID {} eliminada.", id);
            return true;
        }
        return false;
    }

    private InscripcionDTO convertirADTO(SistemaInscripcionesModel model) {
        return new InscripcionDTO(
                model.getId(), 
                model.getRutEstudiante(), 
                model.getNombreAsignatura(), 
                model.getPeriodo()
        );
    }
}