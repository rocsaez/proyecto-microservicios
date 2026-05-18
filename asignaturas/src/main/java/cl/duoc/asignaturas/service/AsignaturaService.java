package cl.duoc.asignaturas.service;

import cl.duoc.asignaturas.client.CarreraClient;
import cl.duoc.asignaturas.client.ProfesorClient;
import cl.duoc.asignaturas.dto.AsignaturaDTO;
import cl.duoc.asignaturas.dto.AsignaturaCreateDTO;
import cl.duoc.asignaturas.model.AsignaturaModel;
import cl.duoc.asignaturas.repository.AsignaturaRepository;
import cl.duoc.asignaturas.exceptions.RecursoNoEncontradoException;
import cl.duoc.asignaturas.exceptions.ServicioNoDisponibleException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignaturaService {

    private static final Logger log = LoggerFactory.getLogger(AsignaturaService.class);

    @Autowired
    private AsignaturaRepository repository;

    @Autowired
    private CarreraClient carreraClient;

    @Autowired
    private ProfesorClient profesorClient;

    public AsignaturaDTO guardar(AsignaturaCreateDTO dto) {
        log.info("Iniciando creación de asignatura: {}", dto.getNombre());
        
        validarCarrera(dto.getCodigoCarrera());
        validarProfesor(dto.getIdProfesor());

        AsignaturaModel asig = new AsignaturaModel();
        asig.setNombre(dto.getNombre());
        asig.setSigla(dto.getSigla());
        asig.setCreditos(dto.getCreditos());
        // asig.setIdProfesor(dto.getIdProfesor()); // Activa si existe en tu Model

        AsignaturaModel guardado = repository.save(asig);
        log.info("Asignatura guardada exitosamente con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public AsignaturaDTO actualizar(Long id, AsignaturaCreateDTO dto) {
        AsignaturaModel asig = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asignatura no encontrada con ID: " + id));
        
        validarCarrera(dto.getCodigoCarrera());
        validarProfesor(dto.getIdProfesor());
        
        asig.setNombre(dto.getNombre());
        asig.setSigla(dto.getSigla());
        asig.setCreditos(dto.getCreditos());
        
        return convertirADTO(repository.save(asig));
    }

    private void validarCarrera(String codigo) {
        try {
            log.info("Validando carrera con código: {}", codigo);
            carreraClient.buscarPorCodigo(codigo);
        } catch (FeignException.NotFound e) {
            log.warn("Carrera código {} no encontrada", codigo);
            throw new RecursoNoEncontradoException("La carrera con código " + codigo + " no existe.");
        } catch (FeignException e) {
            log.error("Error de conexión con microservicio Carreras: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Carreras no disponible.");
        }
    }

    private void validarProfesor(Long id) {
        try {
            log.info("Validando profesor con ID: {}", id);
            profesorClient.obtenerProfesor(id);
        } catch (FeignException.NotFound e) {
            log.warn("Profesor ID {} no encontrado", id);
            throw new RecursoNoEncontradoException("El profesor con ID " + id + " no existe.");
        } catch (FeignException e) {
            log.error("Error de conexión con microservicio Profesores: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Profesores no disponible.");
        }
    }

    public List<AsignaturaDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public AsignaturaDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asignatura no encontrada con ID: " + id));
    }

    public AsignaturaDTO obtenerPorSigla(String sigla) {
        AsignaturaModel model = repository.findBySigla(sigla)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asignatura con sigla " + sigla + " no encontrada"));
        return convertirADTO(model);
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Asignatura ID {} eliminada", id);
            return true;
        }
        return false;
    }

    private AsignaturaDTO convertirADTO(AsignaturaModel model) {
        return new AsignaturaDTO(
            model.getId(),
            model.getNombre(),
            model.getSigla(),
            model.getCreditos(),
            "VALIDADO"
        );
    }
}