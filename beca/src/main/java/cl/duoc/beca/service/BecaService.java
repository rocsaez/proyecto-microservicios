package cl.duoc.beca.service;

import cl.duoc.beca.model.BecaModel;
import cl.duoc.beca.repository.BecaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BecaService {

    private final BecaRepository repository;

    public BecaService(BecaRepository repository) {
        this.repository = repository;
    }

    public List<BecaModel> listarTodas() {
        return repository.findAll();
    }

    public BecaModel guardar(BecaModel beca) {
        return repository.save(beca);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public BecaModel obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}