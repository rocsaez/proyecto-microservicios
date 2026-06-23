package cl.duoc.beca.service;

import cl.duoc.beca.client.EstudianteClient;
import cl.duoc.beca.dto.BecaCreateDTO;
import cl.duoc.beca.dto.BecaDTO;
import cl.duoc.beca.dto.EstudianteDTO;
import cl.duoc.beca.exceptions.RecursoNoEncontradoException;
import cl.duoc.beca.exceptions.ServicioNoDisponibleException;
import cl.duoc.beca.model.BecaModel;
import cl.duoc.beca.repository.BecaRepository;
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

    @Autowired
    private BecaRepository repository;

    @Autowired
    private EstudianteClient estudianteClient;

    public List<BecaDTO> obtenerTodos() {
        log.info("Consultando todas las becas asignadas");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public BecaDTO obtenerPorId(Long id) {
        log.info("Buscando beca con ID: {}", id);
        BecaModel beca = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Beca no encontrada con ID: " + id));
        return convertirADTO(beca);
    }

    public BecaDTO guardar(BecaCreateDTO dto) {
        log.info("Iniciando asignación de beca para el estudiante RUT: {}", dto.getRutEstudiante());
        
        // REGLA CRÍTICA DE EVALUACIÓN: Validar mediante Feign Client antes de guardar
        validarEstudiantePorFeign(dto.getRutEstudiante());

        BecaModel beca = new BecaModel();
        beca.setNombreBeca(dto.getNombreBeca());
        beca.setPorcentajeDescuento(dto.getPorcentajeDescuento());
        beca.setRutEstudiante(dto.getRutEstudiante());
        beca.setEstado(dto.getEstado());

        BecaModel guardado = repository.save(beca);
        log.info("Beca guardada exitosamente con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public BecaDTO actualizar(Long id, BecaCreateDTO dto) {
        log.info("Actualizando beca ID: {}", id);
        BecaModel beca = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Beca no encontrada con ID: " + id));
        
        // Validar mediante Feign Client que el nuevo RUT asignado exista
        validarEstudiantePorFeign(dto.getRutEstudiante());

        beca.setNombreBeca(dto.getNombreBeca());
        beca.setPorcentajeDescuento(dto.getPorcentajeDescuento());
        beca.setRutEstudiante(dto.getRutEstudiante());
        beca.setEstado(dto.getEstado());
        
        return convertirADTO(repository.save(beca));
    }

    public void eliminar(Long id) {
        log.info("Eliminando beca ID: {}", id);
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Beca no encontrada con ID: " + id);
        }
        repository.deleteById(id);
        log.info("Beca ID {} eliminada de manera conforme", id);
    }

    // ── MÉTODOS AUXILIARES DE CONTROL ─────────────────────────────────────────

    private void validarEstudiantePorFeign(String rut) {
        try {
            log.info("Llamando a gestion-estudiante para verificar RUT: {}", rut);
            EstudianteDTO estudiante = estudianteClient.buscarPorRut(rut);
            if (estudiante == null) {
                throw new RecursoNoEncontradoException("El estudiante con RUT " + rut + " no existe en el sistema.");
            }
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("El estudiante con RUT " + rut + " no fue encontrado en el microservicio externo.");
        } catch (FeignException e) {
            log.error("Error de comunicación con gestion-estudiante: {}", e.getMessage());
            throw new ServicioNoDisponibleException("El servicio de gestión de estudiantes no se encuentra disponible temporalmente.");
        }
    }

    private BecaDTO convertirADTO(BecaModel model) {
        return new BecaDTO(
                model.getId(),
                model.getNombreBeca(),
                model.getRutEstudiante(),
                model.getPorcentajeDescuento(),
                model.getEstado()
        );
    }
}