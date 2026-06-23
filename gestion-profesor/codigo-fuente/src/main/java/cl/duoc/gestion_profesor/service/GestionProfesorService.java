package cl.duoc.gestion_profesor.service;

import cl.duoc.gestion_profesor.dto.ProfesorDTO;
import cl.duoc.gestion_profesor.dto.ProfesorCreateDTO;
import cl.duoc.gestion_profesor.model.GestionProfesorModel;
import cl.duoc.gestion_profesor.repository.GestionProfesorRepository;
import cl.duoc.gestion_profesor.exceptions.RecursoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestionProfesorService {

    private static final Logger log = LoggerFactory.getLogger(GestionProfesorService.class);
    private final GestionProfesorRepository repository;

    public GestionProfesorService(GestionProfesorRepository repository) { 
        this.repository = repository; 
    }

    public List<ProfesorDTO> obtenerTodos() { 
        log.info("Consultando lista de todos los profesores");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList()); 
    }

    public ProfesorDTO obtenerPorId(Long id) { 
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> {
                    log.warn("Búsqueda fallida: Profesor ID {} no encontrado", id);
                    return new RecursoNoEncontradoException("Profesor no encontrado con ID: " + id);
                });
    }

    public ProfesorDTO guardar(ProfesorCreateDTO dto) { 
        log.info("Guardando nuevo profesor: {}", dto.getNombre());
        GestionProfesorModel p = new GestionProfesorModel();
        p.setNombre(dto.getNombre());
        p.setAsignatura(dto.getAsignatura());
        p.setCorreo(dto.getCorreo());
        
        GestionProfesorModel guardado = repository.save(p);
        log.info("Profesor guardado con ID: {}", guardado.getId());
        return convertirADTO(guardado); 
    }
    
    public ProfesorDTO actualizar(Long id, ProfesorCreateDTO dto) {
        GestionProfesorModel p = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Intento de actualización fallido: Profesor ID {} no existe", id);
                    return new RecursoNoEncontradoException("No se puede actualizar: ID " + id + " no encontrado");
                });
        
        p.setNombre(dto.getNombre());
        p.setAsignatura(dto.getAsignatura());
        p.setCorreo(dto.getCorreo());
        
        log.info("Profesor ID {} actualizado correctamente", id);
        return convertirADTO(repository.save(p));
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) { 
            repository.deleteById(id); 
            log.info("Profesor ID {} eliminado", id);
            return true; 
        }
        log.warn("Intento de eliminación fallido: ID {} no existe", id);
        return false;
    }

    private ProfesorDTO convertirADTO(GestionProfesorModel model) {
        return new ProfesorDTO(
            model.getId(),
            model.getNombre(),
            model.getAsignatura(),
            model.getCorreo()
        );
    }
}