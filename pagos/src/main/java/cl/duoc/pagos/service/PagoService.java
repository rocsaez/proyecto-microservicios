package cl.duoc.pagos.service;

import cl.duoc.pagos.model.PagoModel;
import cl.duoc.pagos.repository.PagoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    private final PagoRepository repository;

    public PagoService(PagoRepository repository) {
        this.repository = repository;
    }

    public PagoModel guardar(PagoModel pago) {
        return repository.save(pago);
    }

    public List<PagoModel> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<PagoModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public PagoModel actualizar(Long id, PagoModel nuevosDatos) {
        Optional<PagoModel> existente = repository.findById(id);
        if (existente.isPresent()) {
            PagoModel pago = existente.get();
            pago.setIdEstudiante(nuevosDatos.getIdEstudiante());
            pago.setMonto(nuevosDatos.getMonto());
            pago.setFechaPago(nuevosDatos.getFechaPago());
            pago.setEstado(nuevosDatos.getEstado());
            return repository.save(pago);
        }
        return null;
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}