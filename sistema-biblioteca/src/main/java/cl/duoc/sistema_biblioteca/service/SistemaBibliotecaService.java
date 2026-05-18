package cl.duoc.sistema_biblioteca.service;

import cl.duoc.sistema_biblioteca.dto.BibliotecaDTO;
import cl.duoc.sistema_biblioteca.dto.BibliotecaCreateDTO;
import cl.duoc.sistema_biblioteca.model.SistemaBibliotecaModel;
import cl.duoc.sistema_biblioteca.repository.SistemaBibliotecaRepository;
import cl.duoc.sistema_biblioteca.exceptions.RecursoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SistemaBibliotecaService {

    private static final Logger log = LoggerFactory.getLogger(SistemaBibliotecaService.class);
    private final SistemaBibliotecaRepository repository;

    public SistemaBibliotecaService(SistemaBibliotecaRepository repository) {
        this.repository = repository;
    }

    public BibliotecaDTO guardar(BibliotecaCreateDTO dto) {
        log.info("Registrando nuevo libro en biblioteca: {}", dto.getTitulo());
        SistemaBibliotecaModel libro = new SistemaBibliotecaModel();
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());

        SistemaBibliotecaModel guardado = repository.save(libro);
        log.info("Libro guardado exitosamente con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public List<BibliotecaDTO> obtenerTodos() {
        log.info("Consultando catálogo completo de biblioteca");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public BibliotecaDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> {
                    log.warn("Búsqueda fallida: Libro con ID {} no encontrado", id);
                    return new RecursoNoEncontradoException("Libro no encontrado con ID: " + id);
                });
    }

    public BibliotecaDTO actualizar(Long id, BibliotecaCreateDTO dto) {
        SistemaBibliotecaModel libro = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Intento de actualización fallido: Libro ID {} no existe", id);
                    return new RecursoNoEncontradoException("No se puede actualizar: Libro ID " + id + " no encontrado");
                });
        
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());
        
        log.info("Libro ID {} actualizado correctamente", id);
        return convertirADTO(repository.save(libro));
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Libro ID {} eliminado del catálogo", id);
            return true;
        }
        log.warn("Intento de eliminación fallido: Libro ID {} no existe", id);
        return false;
    }

    private BibliotecaDTO convertirADTO(SistemaBibliotecaModel model) {
        return new BibliotecaDTO(
            model.getId(),
            model.getTitulo(),
            model.getAutor(),
            model.getIsbn()
        );
    }
}