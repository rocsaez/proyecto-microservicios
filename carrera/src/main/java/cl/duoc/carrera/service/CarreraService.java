package cl.duoc.carrera.service;

import cl.duoc.carrera.dto.CarreraDTO;
import cl.duoc.carrera.dto.CarreraCreateDTO;
import cl.duoc.carrera.model.CarreraModel;
import cl.duoc.carrera.repository.CarreraRepository;
import cl.duoc.carrera.exceptions.RecursoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraService {

    private static final Logger log = LoggerFactory.getLogger(CarreraService.class);
    private final CarreraRepository repository;

    public CarreraService(CarreraRepository repository) {
        this.repository = repository;
    }

    public CarreraDTO guardar(CarreraCreateDTO dto) {
        log.info("Creando nueva carrera: {}", dto.getNombre());
        CarreraModel carrera = new CarreraModel();
        carrera.setNombre(dto.getNombre());
        carrera.setFacultad(dto.getFacultad());
        carrera.setSemestres(dto.getSemestres());

        CarreraModel guardado = repository.save(carrera);
        log.info("Carrera guardada con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public List<CarreraDTO> obtenerTodas() {
        log.info("Obteniendo listado de todas las carreras");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CarreraDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> {
                    log.warn("Busqueda fallida: Carrera ID {} no existe", id);
                    return new RecursoNoEncontradoException("Carrera no encontrada con ID: " + id);
                });
    }

    public CarreraDTO actualizar(Long id, CarreraCreateDTO dto) {
        CarreraModel carrera = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Intento de actualización fallido: ID {} no encontrado", id);
                    return new RecursoNoEncontradoException("No se puede actualizar: ID " + id + " no encontrado");
                });
        
        carrera.setNombre(dto.getNombre());
        carrera.setFacultad(dto.getFacultad());
        carrera.setSemestres(dto.getSemestres());
        
        log.info("Carrera ID {} actualizada correctamente", id);
        return convertirADTO(repository.save(carrera));
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Carrera ID {} eliminada", id);
            return true;
        }
        log.warn("Intento de eliminación fallido: ID {} no existe", id);
        return false;
    }

    private CarreraDTO convertirADTO(CarreraModel model) {
        return new CarreraDTO(
            model.getId(),
            model.getNombre(),
            model.getFacultad(),
            model.getSemestres()
        );
    }
}