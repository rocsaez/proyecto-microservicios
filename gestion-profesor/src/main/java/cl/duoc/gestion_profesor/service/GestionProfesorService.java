package cl.duoc.gestion_profesor.service;

import cl.duoc.gestion_profesor.model.GestionProfesorModel;
import cl.duoc.gestion_profesor.repository.GestionProfesorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GestionProfesorService {

    private final GestionProfesorRepository repository;

    public GestionProfesorService(GestionProfesorRepository repository) {
        this.repository = repository;
    }

    // CREATE: Guarda validando que el RUT sea único
    public GestionProfesorModel guardarProfesor(GestionProfesorModel profesor) {
        if (repository.findByRut(profesor.getRut()).isPresent()) {
            return null; 
        }
        return repository.save(profesor);
    }

    // READ ALL
    public List<GestionProfesorModel> obtenerTodos() {
        return repository.findAll();
    }

    // READ BY ID
    public Optional<GestionProfesorModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    // READ BY RUT
    public Optional<GestionProfesorModel> obtenerPorRut(String rut) {
        return repository.findByRut(rut);
    }

    // UPDATE
    public GestionProfesorModel actualizarProfesor(Long id, GestionProfesorModel detalles) {
        Optional<GestionProfesorModel> existente = repository.findById(id);
        if (existente.isPresent()) {
            GestionProfesorModel prof = existente.get();
            prof.setNombreCompleto(detalles.getNombreCompleto());
            prof.setCorreo(detalles.getCorreo());
            return repository.save(prof);
        }
        return null;
    }

    // DELETE BY ID
    public boolean eliminarPorId(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // DELETE BY RUT
    public boolean eliminarPorRut(String rut) {
        if (repository.findByRut(rut).isPresent()) {
            repository.deleteByRut(rut);
            return true;
        }
        return false;
    }
}