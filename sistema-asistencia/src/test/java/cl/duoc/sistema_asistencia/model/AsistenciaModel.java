package cl.duoc.sistema_asistencia.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class AsistenciaTest {

    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        Asistencia asistencia = new Asistencia();
        assertNotNull(asistencia);
    }

    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        LocalDateTime ahora = LocalDateTime.now();
        Asistencia asistencia = new Asistencia(
            1L, "12345678-9", "INF4141", ahora
        );

        assertEquals(1L, asistencia.getId());
        assertEquals("12345678-9", asistencia.getRutEstudiante());
        assertEquals("INF4141", asistencia.getCodigoClase());
        assertEquals(ahora, asistencia.getFechaHora());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        Asistencia asistencia = new Asistencia();
        LocalDateTime ahora = LocalDateTime.now();

        asistencia.setId(2L);
        asistencia.setRutEstudiante("98765432-1");
        asistencia.setCodigoClase("MAT2020");
        asistencia.setFechaHora(ahora);

        assertEquals(2L, asistencia.getId());
        assertEquals("98765432-1", asistencia.getRutEstudiante());
        assertEquals("MAT2020", asistencia.getCodigoClase());
        assertEquals(ahora, asistencia.getFechaHora());
    }

    @Test
    @DisplayName("equals y hashCode - dos asistencias con los mismos datos deben ser iguales")
    void dosAsistenciasConMismosDatosDebenSerIguales() {
        LocalDateTime ahora = LocalDateTime.now();
        Asistencia a1 = new Asistencia(1L, "12345678-9", "INF4141", ahora);
        Asistencia a2 = new Asistencia(1L, "12345678-9", "INF4141", ahora);

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    @DisplayName("toString - debe contener el rut en la representación de texto")
    void toStringDebeContenerRutDelEstudiante() {
        Asistencia asistencia = new Asistencia(3L, "12345678-9", "INF4141", LocalDateTime.now());

        String texto = asistencia.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("12345678-9"));
    }
}