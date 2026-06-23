package cl.duoc.asignaturas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AsignaturaModelTest {

    @Test
    @DisplayName("Constructor y Lombok - debe instanciar y permitir modificar campos de Asignatura")
    void constructorYSettersDebenFuncionarCorrectamente() {
        // Given
        AsignaturaModel asignatura = new AsignaturaModel();

        // When
        asignatura.setId(1L);
        asignatura.setNombre("Programación de Aplicaciones Móviles");
        asignatura.setSigla("PMD8102");
        asignatura.setCreditos(6);

        // Then
        assertNotNull(asignatura);
        assertEquals(1L, asignatura.getId());
        assertEquals("Programación de Aplicaciones Móviles", asignatura.getNombre());
        assertEquals("PMD8102", asignatura.getSigla());
        assertEquals(6, asignatura.getCreditos());
    }
}
