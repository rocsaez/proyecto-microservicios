package cl.duoc.gestion_profesor.service;
import cl.duoc.gestion_profesor.model.GestionProfesorModel;
import cl.duoc.gestion_profesor.repository.GestionProfesorRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GestionProfesorService {
    private final GestionProfesorRepository repository;
    public GestionProfesorService(GestionProfesorRepository repository) { this.repository = repository; }

    public List<GestionProfesorModel> obtenerTodos() { return repository.findAll(); }
    public Optional<GestionProfesorModel> obtenerPorId(Long id) { return repository.findById(id); }
    public GestionProfesorModel guardar(GestionProfesorModel p) { return repository.save(p); }
    
    public GestionProfesorModel actualizar(Long id, GestionProfesorModel nuevos) {
        return repository.findById(id).map(p -> {
            p.setNombre(nuevos.getNombre());
            p.setAsignatura(nuevos.getAsignatura());
            p.setCorreo(nuevos.getCorreo());
            return repository.save(p);
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}