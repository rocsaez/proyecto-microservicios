package cl.duoc.gestion_sala.service;

import cl.duoc.gestion_sala.dto.SalaDTO;
import cl.duoc.gestion_sala.dto.SalaCreateDTO;
import cl.duoc.gestion_sala.model.SalaModel;
import cl.duoc.gestion_sala.repository.SalaRepository;
import cl.duoc.gestion_sala.exceptions.RecursoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaService {

    private static final Logger log = LoggerFactory.getLogger(SalaService.class);
    private final SalaRepository repository;

    public SalaService(SalaRepository repository) {
        this.repository = repository;
    }

    public SalaDTO guardar(SalaCreateDTO dto) {
        log.info("Creando nueva sala: {}", dto.getNombreSala());
        SalaModel sala = new SalaModel();
        sala.setNombreSala(dto.getNombreSala());
        sala.setCapacidad(dto.getCapacidad());
        sala.setTipo(dto.getTipo());
        sala.setUbicacion(dto.getUbicacion());

        SalaModel guardado = repository.save(sala);
        log.info("Sala guardada exitosamente con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public List<SalaDTO> obtenerTodas() {
        log.info("Consultando todas las salas disponibles");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public SalaDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> {
                    log.warn("Búsqueda fallida: Sala ID {} no existe", id);
                    return new RecursoNoEncontradoException("Sala no encontrada con ID: " + id);
                });
    }

    public SalaDTO actualizar(Long id, SalaCreateDTO dto) {
        SalaModel sala = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Intento de actualización fallido: Sala ID {} no encontrada", id);
                    return new RecursoNoEncontradoException("No se puede actualizar: Sala ID " + id + " no existe");
                });
        
        sala.setNombreSala(dto.getNombreSala());
        sala.setCapacidad(dto.getCapacidad());
        sala.setTipo(dto.getTipo());
        sala.setUbicacion(dto.getUbicacion());
        
        log.info("Sala ID {} actualizada correctamente", id);
        return convertirADTO(repository.save(sala));
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Sala ID {} eliminada", id);
            return true;
        }
        log.warn("Intento de eliminación fallido: Sala ID {} no existe", id);
        return false;
    }

    private SalaDTO convertirADTO(SalaModel model) {
        return new SalaDTO(
            model.getId(),
            model.getNombreSala(),
            model.getCapacidad(),
            model.getTipo(),
            model.getUbicacion()
        );
    }
}