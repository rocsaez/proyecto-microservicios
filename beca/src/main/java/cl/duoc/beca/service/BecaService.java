package cl.duoc.beca.service;
import cl.duoc.beca.model.BecaModel;
import cl.duoc.beca.repository.BecaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional; // ¡Importante!

@Service
public class BecaService {
    private final BecaRepository repository;
    public BecaService(BecaRepository repository) { this.repository = repository; }

    public List<BecaModel> obtenerTodos() { return repository.findAll(); }
    public Optional<BecaModel> obtenerPorId(Long id) { return repository.findById(id); }
    public BecaModel guardar(BecaModel b) { return repository.save(b); }

    public BecaModel actualizar(Long id, BecaModel datos) {
        Optional<BecaModel> ex = repository.findById(id);
        if (ex.isPresent()) {
            BecaModel b = ex.get();
            b.setNombreBeca(datos.getNombreBeca());
            b.setPorcentajeDescuento(datos.getPorcentajeDescuento());
            b.setRutEstudiante(datos.getRutEstudiante());
            b.setEstado(datos.getEstado());
            return repository.save(b);
        }
        return null;
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) { repository.deleteById(id); return true; }
        return false;
    }
}