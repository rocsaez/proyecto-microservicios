package cl.duoc.gestion_sala.service;

import cl.duoc.gestion_sala.model.SalaModel;
import cl.duoc.gestion_sala.repository.SalaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional; // Importación clave para evitar errores

@Service
public class SalaService {

    private final SalaRepository repository;

    public SalaService(SalaRepository repository) {
        this.repository = repository;
    }

    // CREATE: Guarda una sala
    public SalaModel guardar(SalaModel sala) {
        return repository.save(sala);
    }

    // READ: Trae todas las salas
    public List<SalaModel> obtenerTodas() {
        return repository.findAll();
    }

    // READ BY ID: Busca una sala específica
    public Optional<SalaModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    // UPDATE: Lógica manual para actualizar datos
    public SalaModel actualizar(Long id, SalaModel datosNuevos) {
        Optional<SalaModel> existente = repository.findById(id);
        
        if (existente.isPresent()) {
            SalaModel sala = existente.get();
            // Actualizamos campo por campo según tu modelo
            sala.setNombreSala(datosNuevos.getNombreSala());
            sala.setCapacidad(datosNuevos.getCapacidad());
            sala.setTipo(datosNuevos.getTipo());
            sala.setUbicacion(datosNuevos.getUbicacion());
            return repository.save(sala);
        }
        return null;
    }

    // DELETE: Borra si el ID existe
    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}