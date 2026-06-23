package cl.duoc.sistema_biblioteca.service;

import cl.duoc.sistema_biblioteca.dto.BibliotecaDTO;
import cl.duoc.sistema_biblioteca.dto.BibliotecaCreateDTO;
import cl.duoc.sistema_biblioteca.model.SistemaBibliotecaModel;
import cl.duoc.sistema_biblioteca.repository.SistemaBibliotecaRepository;
import cl.duoc.sistema_biblioteca.exceptions.RecursoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SistemaBibliotecaService {

    private static final Logger log = LoggerFactory.getLogger(SistemaBibliotecaService.class);

    @Autowired
    private SistemaBibliotecaRepository repository;

    public List<BibliotecaDTO> obtenerTodos() {
        log.info("Consultando catálogo completo de biblioteca");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public BibliotecaDTO obtenerPorId(Long id) {
        log.info("Buscando libro id={}", id);
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> {
                    log.warn("Búsqueda fallida: Libro con ID {} no encontrado", id);
                    return new RecursoNoEncontradoException("Libro no encontrado con ID: " + id);
                });
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

    public BibliotecaDTO actualizar(Long id, BibliotecaCreateDTO dto) {
        log.info("Actualizando libro id={}", id);
        SistemaBibliotecaModel libro = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Intento de actualización fallido: Libro ID {} no existe", id);
                    return new RecursoNoEncontradoException("No se puede actualizar: Libro ID " + id + " no encontrado");
                });
        
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());
        
        return convertirADTO(repository.save(libro));
    }

    public void eliminar(Long id) {
        log.info("Eliminando libro id={}", id);
        if (!repository.existsById(id)) {
            log.warn("Intento de eliminación fallido: Libro ID {} no existe", id);
            throw new RecursoNoEncontradoException("Libro no encontrado con ID: " + id);
        }
        repository.deleteById(id);
        log.info("Libro ID {} eliminado del catálogo", id);
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