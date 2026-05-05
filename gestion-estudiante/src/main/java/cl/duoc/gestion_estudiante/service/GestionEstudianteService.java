package cl.duoc.gestion_estudiante.service;

import cl.duoc.gestion_estudiante.model.GestionEstudianteModel;
import cl.duoc.gestion_estudiante.repository.GestionEstudianteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * @Service indica que esta clase contiene la lógica de negocio.
 */
@Service
public class GestionEstudianteService {

    // Se declara el repositorio para poder usar las funciones de la base de datos.
    private final GestionEstudianteRepository repository;

    public GestionEstudianteService(GestionEstudianteRepository repository) {
        this.repository = repository;
    }

    /**
     * CREATE (Crear): Guarda un nuevo estudiante validando que el RUT sea único.
     */
    public GestionEstudianteModel guardarEstudiante(GestionEstudianteModel estudiante) {
        if (repository.findByRut(estudiante.getRut()).isPresent()) {
            return null; 
        }
        return repository.save(estudiante);
    }

    /**
     * READ (Leer todos): Obtiene una lista con todos los estudiantes guardados.
     */
    public List<GestionEstudianteModel> obtenerTodos() {
        return repository.findAll();
    }

    /**
     * READ (Leer por ID): Busca un estudiante específico usando su identificador único.
     */
    public Optional<GestionEstudianteModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * READ (Leer por RUT): Busca un estudiante específico usando su RUT.
     */
    public Optional<GestionEstudianteModel> obtenerPorRut(String rut) {
        return repository.findByRut(rut);
    }

    /**
     * UPDATE (Actualizar): Reemplaza los datos de un estudiante existente.
     */
    public GestionEstudianteModel actualizarEstudiante(Long id, GestionEstudianteModel detalles) {
        Optional<GestionEstudianteModel> existente = repository.findById(id);
        
        if (existente.isPresent()) {
            GestionEstudianteModel estudianteAActualizar = existente.get();
            estudianteAActualizar.setNombreCompleto(detalles.getNombreCompleto());
            estudianteAActualizar.setCorreo(detalles.getCorreo());
            return repository.save(estudianteAActualizar);
        }
        return null;
    }

    /**
     * DELETE (Eliminar por ID): Borra un estudiante usando su ID.
     */
    public boolean eliminarPorId(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * DELETE (Eliminar por RUT): Borra un estudiante usando su RUT.
     */
    public boolean eliminarPorRut(String rut) {
        if (repository.findByRut(rut).isPresent()) {
            repository.deleteByRut(rut);
            return true;
        }
        return false;
    }
}