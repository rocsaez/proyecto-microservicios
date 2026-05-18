package cl.duoc.pagos.service;

import cl.duoc.pagos.client.EstudianteClient;
import cl.duoc.pagos.client.BecaClient;
import cl.duoc.pagos.dto.PagoDTO;
import cl.duoc.pagos.dto.PagoCreateDTO;
import cl.duoc.pagos.dto.EstudianteDTO;
import cl.duoc.pagos.dto.BecaDTO;
import cl.duoc.pagos.model.PagoModel;
import cl.duoc.pagos.repository.PagoRepository;
import cl.duoc.pagos.exceptions.RecursoNoEncontradoException;
import cl.duoc.pagos.exceptions.ServicioNoDisponibleException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    @Autowired
    private PagoRepository repository;

    @Autowired
    private EstudianteClient estudianteClient;

    @Autowired
    private BecaClient becaClient;

    public PagoDTO guardar(PagoCreateDTO dto) {
        log.info("Iniciando proceso de pago para Estudiante ID: {}", dto.getIdEstudiante());
        
        // 1. Validar Estudiante
        EstudianteDTO estudiante = validarEstudiante(dto.getIdEstudiante());

        // 2. Calcular Monto con Beca
        Double montoFinal = aplicarBeca(estudiante.getRut(), dto.getMonto());

        PagoModel pago = new PagoModel();
        pago.setIdEstudiante(dto.getIdEstudiante());
        pago.setMonto(montoFinal); 
        pago.setFechaPago(dto.getFechaPago());
        pago.setEstado(dto.getEstado());

        PagoModel guardado = repository.save(pago);
        log.info("Pago registrado exitosamente. ID: {}, Monto Final: {}", guardado.getId(), montoFinal);
        
        return convertirADTO(guardado);
    }

    public PagoDTO actualizar(Long id, PagoCreateDTO dto) {
        PagoModel pago = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado con ID: " + id));
        
        EstudianteDTO estudiante = validarEstudiante(dto.getIdEstudiante());
        Double montoFinal = aplicarBeca(estudiante.getRut(), dto.getMonto());

        pago.setIdEstudiante(dto.getIdEstudiante());
        pago.setMonto(montoFinal);
        pago.setFechaPago(dto.getFechaPago());
        pago.setEstado(dto.getEstado());
        
        return convertirADTO(repository.save(pago));
    }

    private EstudianteDTO validarEstudiante(Long id) {
        try {
            log.info("Consultando datos del estudiante ID: {}", id);
            return estudianteClient.obtenerPorId(id);
        } catch (FeignException.NotFound e) {
            log.warn("Estudiante ID {} no encontrado", id);
            throw new RecursoNoEncontradoException("El estudiante con ID " + id + " no existe.");
        } catch (FeignException e) {
            log.error("Error de conexión con microservicio Estudiantes: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Estudiantes no disponible.");
        }
    }

    private Double aplicarBeca(String rut, Double montoOriginal) {
        try {
            log.info("Buscando becas activas para RUT: {}", rut);
            BecaDTO beca = becaClient.obtenerBecaPorRut(rut);
            
            if (beca != null && "ACTIVA".equalsIgnoreCase(beca.getEstado())) {
                Double descuento = montoOriginal * (beca.getPorcentajeDescuento() / 100);
                Double total = montoOriginal - descuento;
                log.info("Beca aplicada: {}%. Monto original: {}, Monto con descuento: {}", 
                         beca.getPorcentajeDescuento(), montoOriginal, total);
                return total;
            }
        } catch (FeignException.NotFound e) {
            log.info("El estudiante RUT {} no posee becas registradas. Se aplica monto original.", rut);
        } catch (FeignException e) {
            log.error("Error al consultar servicio de Becas. Se cobrará monto original por seguridad.");
        }
        return montoOriginal;
    }

    public List<PagoDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public PagoDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado con ID: " + id));
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Pago ID {} eliminado", id);
            return true;
        }
        return false;
    }

    private PagoDTO convertirADTO(PagoModel model) {
        return new PagoDTO(
            model.getId(),
            model.getIdEstudiante(),
            model.getMonto(),
            model.getFechaPago(),
            model.getEstado()
        );
    }
}