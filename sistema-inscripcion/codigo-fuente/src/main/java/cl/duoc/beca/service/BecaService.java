package cl.duoc.beca.service;

import cl.duoc.beca.client.EstudianteClient;
import cl.duoc.beca.dto.BecaDTO;
import cl.duoc.beca.dto.BecaCreateDTO;
import cl.duoc.beca.model.BecaModel;
import cl.duoc.beca.repository.BecaRepository;
import cl.duoc.beca.exceptions.RecursoNoEncontradoException;
import cl.duoc.beca.exceptions.ServicioNoDisponibleException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BecaService {

    private static final Logger log = LoggerFactory.getLogger(BecaService.class);
    private final BecaRepository repository;
    
    @Autowired
    private EstudianteClient estudianteClient;

    public BecaService(BecaRepository repository) { 
        this.repository = repository; 
    }

    public List<BecaDTO> obtenerTodos() { 
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList()); 
    }

    public BecaDTO obtenerPorId(Long id) { 
        BecaModel b = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Beca no encontrada con ID: " + id));
        return convertirADTO(b);
    }

    public BecaDTO guardar(BecaCreateDTO dto) { 
        log.info("Iniciando proceso de beca para estudiante RUT: {}", dto.getRutEstudiante());
        
        // Validación del estudiante vía Feign
        validarEstudiante(dto.getRutEstudiante());

        BecaModel b = new BecaModel();
        b.setNombreBeca(dto.getNombreBeca());
        b.setRutEstudiante(dto.getRutEstudiante());
        b.setPorcentajeDescuento(dto.getPorcentajeDescuento());
        b.setEstado(dto.getEstado());
        
        BecaModel guardado = repository.save(b);
        log.info("Beca guardada con éxito. ID: {}", guardado.getId());
        return convertirADTO(guardado); 
    }

    public BecaDTO actualizar(Long id, BecaCreateDTO dto) {
        BecaModel b = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Beca ID " + id + " no encontrada"));
        
        validarEstudiante(dto.getRutEstudiante());
        
        b.setNombreBeca(dto.getNombreBeca());
        b.setRutEstudiante(dto.getRutEstudiante());
        b.setPorcentajeDescuento(dto.getPorcentajeDescuento());
        b.setEstado(dto.getEstado());
        
        return convertirADTO(repository.save(b));
    }

    private void validarEstudiante(String rut) {
        try {
            log.info("Consultando existencia de estudiante RUT: {}", rut);
            estudianteClient.buscarPorRut(rut); // Asegúrate que el método en el Client se llame así
        } catch (FeignException.NotFound e) {
            log.warn("Estudiante RUT {} no existe en el sistema", rut);
            throw new RecursoNoEncontradoException("No se puede otorgar beca: El estudiante con RUT " + rut + " no existe.");
        } catch (FeignException e) {
            log.error("Error de conexión con microservicio Estudiantes: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Estudiantes no disponible actualmente.");
        }
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) { 
            repository.deleteById(id); 
            log.info("Beca ID {} eliminada del sistema", id);
            return true; 
        }
        return false;
    }

    private BecaDTO convertirADTO(BecaModel modelo) {
        return new BecaDTO(
            modelo.getId(),
            modelo.getNombreBeca(),
            modelo.getRutEstudiante(),
            modelo.getPorcentajeDescuento(),
            modelo.getEstado()
        );
    }
}