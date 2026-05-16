package cl.duoc.sistema_biblioteca.service;

import cl.duoc.sistema_biblioteca.model.SistemaBibliotecaModel;
import cl.duoc.sistema_biblioteca.repository.SistemaBibliotecaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional; // Importante para que no de error

@Service
public class SistemaBibliotecaService {

    private final SistemaBibliotecaRepository repository;

    public SistemaBibliotecaService(SistemaBibliotecaRepository repository) {
        this.repository = repository;
    }

    public SistemaBibliotecaModel guardar(SistemaBibliotecaModel libro) {
        return repository.save(libro);
    }

    public List<SistemaBibliotecaModel> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<SistemaBibliotecaModel> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public SistemaBibliotecaModel actualizar(Long id, SistemaBibliotecaModel nuevosDatos) {
        Optional<SistemaBibliotecaModel> existente = repository.findById(id);
        
        if (existente.isPresent()) {
            SistemaBibliotecaModel libro = existente.get();
            libro.setTitulo(nuevosDatos.getTitulo());
            libro.setAutor(nuevosDatos.getAutor());
            libro.setIsbn(nuevosDatos.getIsbn());
            return repository.save(libro);
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