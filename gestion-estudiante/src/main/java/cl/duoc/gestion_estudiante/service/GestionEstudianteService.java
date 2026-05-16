package cl.duoc.gestion_estudiante.service;

import cl.duoc.gestion_estudiante.model.GestionEstudianteModel;
import cl.duoc.gestion_estudiante.repository.GestionEstudianteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GestionEstudianteService {

    private final GestionEstudianteRepository repository;

    public GestionEstudianteService(GestionEstudianteRepository repository) {
        this.repository = repository;
    }

    // Guarda el estudiante en la BD
    public GestionEstudianteModel guardarEstudiante(GestionEstudianteModel estudiante) {
        return repository.save(estudiante);
    }

    // Trae la lista completa
    public List<GestionEstudianteModel> obtenerTodos() {
        return repository.findAll();
    }

    // Busca por ID
    public Optional<GestionEstudianteModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    // Lógica para ACTUALIZAR (Manual y tranqui)
    public GestionEstudianteModel actualizarEstudiante(Long id, GestionEstudianteModel nuevosDatos) {
        Optional<GestionEstudianteModel> existente = repository.findById(id);
        if (existente.isPresent()) {
            GestionEstudianteModel estudiante = existente.get();
            // Actualizamos los campos uno por uno
            estudiante.setNombre(nuevosDatos.getNombre());
            estudiante.setRut(nuevosDatos.getRut());
            estudiante.setCorreo(nuevosDatos.getCorreo());
            return repository.save(estudiante);
        }
        return null;
    }

    // Borra de la BD
    public boolean eliminarPorId(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}