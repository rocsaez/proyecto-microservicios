package cl.duoc.sistema_biblioteca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SistemaBibliotecaModelTest {

    @Test
    @DisplayName("Constructor vacío y Setters - debe instanciar y asignar propiedades")
    void constructorVacioYSettersDebenFuncionar() {
        SistemaBibliotecaModel libro = new SistemaBibliotecaModel();
        libro.setId(1L);
        libro.setTitulo("Metamorfosis");
        libro.setAutor("Franz Kafka");
        libro.setIsbn("9782070380022");

        assertNotNull(libro);
        assertEquals(1L, libro.getId());
        assertEquals("Metamorfosis", libro.getTitulo());
        assertEquals("Franz Kafka", libro.getAutor());
        assertEquals("9782070380022", libro.getIsbn());
    }

    @Test
    @DisplayName("equals y hashCode - dos entidades con los mismos datos deben ser iguales")
    void dosLibrosConMismosDatosDebenSerIguales() {
        SistemaBibliotecaModel l1 = new SistemaBibliotecaModel();
        l1.setId(1L); l1.setTitulo("1984"); l1.setAutor("George Orwell"); l1.setIsbn("9780451524935");

        SistemaBibliotecaModel l2 = new SistemaBibliotecaModel();
        l2.setId(1L); l2.setTitulo("1984"); l2.setAutor("George Orwell"); l2.setIsbn("9780451524935");

        assertEquals(l1, l2);
        assertEquals(l1.hashCode(), l2.hashCode());
    }
}