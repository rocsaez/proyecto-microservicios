package cl.duoc.gestion_sala.service;

import cl.duoc.gestion_sala.dto.SalaDTO;
import cl.duoc.gestion_sala.dto.SalaCreateDTO;
import cl.duoc.gestion_sala.model.SalaModel;
import cl.duoc.gestion_sala.repository.SalaRepository;
import cl.duoc.gestion_sala.exceptions.RecursoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaService {

    private static final Logger log = LoggerFactory.getLogger(SalaService.class);

    @Autowired
    private SalaRepository repository;

    public List<SalaDTO> obtenerTodas() {
        log.info("Consultando todas las salas");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public SalaDTO obtenerPorId(Long id) {
        log.info("Buscando sala id={}", id);
         SalaModel sala = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Sala no encontrada con ID: " + id));
        log.info("Sala encontrada: nombre={}, capacidad={}", sala.getNombreSala(), sala.getCapacidad());
        return convertirADTO(sala);
    }

    public SalaDTO guardar(SalaCreateDTO dto) {
        log.info("Creando sala nombre={}", dto.getNombreSala());
        SalaModel sala = new SalaModel();
        sala.setNombreSala(dto.getNombreSala());
        sala.setCapacidad(dto.getCapacidad());
        sala.setTipo(dto.getTipo());
        sala.setUbicacion(dto.getUbicacion());
        
        SalaModel guardado = repository.save(sala);
        log.info("Sala creada id={}", guardado.getId());
        return convertirADTO(guardado);
    }

    public SalaDTO actualizar(Long id, SalaCreateDTO dto) {
        log.info("Actualizando sala id={}", id);
        SalaModel sala = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Sala no encontrada con ID: " + id));
        
        sala.setNombreSala(dto.getNombreSala());
        sala.setCapacidad(dto.getCapacidad());
        sala.setTipo(dto.getTipo());
        sala.setUbicacion(dto.getUbicacion());
        
        return convertirADTO(repository.save(sala));
    }

    public void eliminar(Long id) {
        log.info("Eliminando sala id={}", id);
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Sala no encontrada con ID: " + id);
        }
        repository.deleteById(id);
        log.info("Sala id={} eliminada", id);
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