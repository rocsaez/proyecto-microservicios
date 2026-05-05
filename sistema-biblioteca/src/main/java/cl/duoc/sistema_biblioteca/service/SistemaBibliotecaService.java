package cl.duoc.sistema_biblioteca.service;

import cl.duoc.sistema_biblioteca.model.SistemaBibliotecaModel;
import cl.duoc.sistema_biblioteca.repository.SistemaBibliotecaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SistemaBibliotecaService {

    private final SistemaBibliotecaRepository repository;

    public SistemaBibliotecaService(SistemaBibliotecaRepository repository) {
        this.repository = repository;
    }

    public List<SistemaBibliotecaModel> listarTodos() {
        return repository.findAll();
    }

    public SistemaBibliotecaModel guardar(SistemaBibliotecaModel libro) {
        return repository.save(libro);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}