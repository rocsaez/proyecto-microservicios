package cl.duoc.sistema_biblioteca.repository;

import cl.duoc.sistema_biblioteca.model.SistemaBibliotecaModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SistemaBibliotecaRepositoryTest {

    @Autowired
    private SistemaBibliotecaRepository repository;

    @Test
    @DisplayName("save - debe persistir el libro y generar ID automáticamente")
    void debePersistirLibroYAsignarId() {
        // Given
        SistemaBibliotecaModel libro = new SistemaBibliotecaModel();
        libro.setTitulo("El Señor de los Anillos");
        libro.setAutor("J.R.R. Tolkien");
        libro.setIsbn("9780261103251");

        // When
        SistemaBibliotecaModel guardado = repository.save(libro);

        // Then
        assertNotNull(guardado.getId());
        assertEquals("El Señor de los Anillos", guardado.getTitulo());
    }

    @Test
    @DisplayName("findAll - debe retornar todos los libros persistidos")
    void debeRetornarTodosLosLibros() {
        // Given
        SistemaBibliotecaModel l1 = new SistemaBibliotecaModel();
        l1.setTitulo("Libro A"); l1.setAutor("Autor A"); l1.setIsbn("9780261103251");
        SistemaBibliotecaModel l2 = new SistemaBibliotecaModel();
        l2.setTitulo("Libro B"); l2.setAutor("Autor B"); l2.setIsbn("9789500403344");

        repository.save(l1);
        repository.save(l2);

        // When
        List<SistemaBibliotecaModel> libros = repository.findAll();

        // Then
        assertNotNull(libros);
        assertEquals(2, libros.size());
    }

    @Test
    @DisplayName("findById - debe retornar vacío si el ID no existe")
    void debeRetornarVacioSiIdNoExiste() {
        Optional<SistemaBibliotecaModel> resultado = repository.findById(999L);
        assertFalse(resultado.isPresent());
    }
}