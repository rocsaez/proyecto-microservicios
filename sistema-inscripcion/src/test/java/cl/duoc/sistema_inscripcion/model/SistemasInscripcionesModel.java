package cl.duoc.sistema_inscripcion.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SistemaInscripcionesModelTest {

    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        SistemaInscripcionesModel modelo = new SistemaInscripcionesModel();
        assertNotNull(modelo);
    }

    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        SistemaInscripcionesModel modelo = new SistemaInscripcionesModel(
            1L, "12345678-9", "ASY4131", "2026-1"
        );

        assertEquals(1L, modelo.getId());
        assertEquals("12345678-9", modelo.getRutEstudiante());
        assertEquals("ASY4131", modelo.getNombreAsignatura());
        assertEquals("2026-1", modelo.getPeriodo());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        SistemaInscripcionesModel modelo = new SistemaInscripcionesModel();

        modelo.setId(3L);
        modelo.setRutEstudiante("9876543-2");
        modelo.setNombreAsignatura("MDY3131");
        modelo.setPeriodo("2026-2");

        assertEquals(3L, modelo.getId());
        assertEquals("9876543-2", modelo.getRutEstudiante());
        assertEquals("MDY3131", modelo.getNombreAsignatura());
        assertEquals("2026-2", modelo.getPeriodo());
    }

    @Test
    @DisplayName("equals y hashCode - dos inscripciones con idénticos datos deben ser iguales")
    void dosModelosConMismosDatosDebenSerIguales() {
        SistemaInscripcionesModel m1 = new SistemaInscripcionesModel(1L, "12345678-9", "ASY4131", "2026-1");
        SistemaInscripcionesModel m2 = new SistemaInscripcionesModel(1L, "12345678-9", "ASY4131", "2026-1");

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    @DisplayName("toString - debe contener el RUT del estudiante en la cadena representativa")
    void toStringDebeContenerRutDelEstudiante() {
        SistemaInscripcionesModel modelo = new SistemaInscripcionesModel(1L, "12345678-9", "ASY4131", "2026-1");

        String texto = modelo.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("12345678-9"));
    }
}