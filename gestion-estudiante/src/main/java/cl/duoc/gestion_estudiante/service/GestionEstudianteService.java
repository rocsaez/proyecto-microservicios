package cl.duoc.gestion_estudiante.service;

import cl.duoc.gestion_estudiante.dto.EstudianteDTO;
import cl.duoc.gestion_estudiante.dto.EstudianteCreateDTO;
import cl.duoc.gestion_estudiante.model.GestionEstudianteModel;
import cl.duoc.gestion_estudiante.repository.GestionEstudianteRepository;
import cl.duoc.gestion_estudiante.exceptions.RecursoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestionEstudianteService {

    private static final Logger log = LoggerFactory.getLogger(GestionEstudianteService.class);
    private final GestionEstudianteRepository repository;

    public GestionEstudianteService(GestionEstudianteRepository repository) {
        this.repository = repository;
    }

    public EstudianteDTO guardarEstudiante(EstudianteCreateDTO dto) {
        log.info("Registrando nuevo estudiante: RUT {}", dto.getRut());
        GestionEstudianteModel e = new GestionEstudianteModel();
        e.setNombre(dto.getNombre());
        e.setRut(dto.getRut());
        e.setCorreo(dto.getCorreo());

        GestionEstudianteModel guardado = repository.save(e);
        log.info("Estudiante guardado exitosamente con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public List<EstudianteDTO> obtenerTodos() {
        log.info("Consultando lista completa de estudiantes");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public EstudianteDTO obtenerPorId(Long id) {
        return repository.findById(id)
            .map(this::convertirADTO)
            .orElseThrow(() -> {
                log.warn("Búsqueda fallida: ID {} no existe", id);
                return new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id);
            });
    }

    public EstudianteDTO obtenerPorRut(String rut) {
        log.info("Buscando estudiante por RUT: {}", rut);
        return repository.findByRut(rut)
            .map(this::convertirADTO)
            .orElseThrow(() -> {
                log.warn("Búsqueda fallida: RUT {} no encontrado", rut);
                return new RecursoNoEncontradoException("Estudiante con RUT " + rut + " no encontrado");
            });
    }

    public EstudianteDTO actualizarEstudiante(Long id, EstudianteCreateDTO dto) {
        GestionEstudianteModel e = repository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No se puede actualizar: ID " + id + " no encontrado"));

        e.setNombre(dto.getNombre());
        e.setRut(dto.getRut());
        e.setCorreo(dto.getCorreo());
        
        log.info("Estudiante ID {} actualizado correctamente", id);
        return convertirADTO(repository.save(e));
    }

    public boolean eliminarPorId(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Estudiante ID {} eliminado", id);
            return true;
        }
        log.warn("Intento de eliminación fallido: ID {} no existe", id);
        return false;
    }

    private EstudianteDTO convertirADTO(GestionEstudianteModel model) {
        return new EstudianteDTO(
            model.getId(),
            model.getNombre(),
            model.getRut(),
            model.getCorreo()
        );
    }
}