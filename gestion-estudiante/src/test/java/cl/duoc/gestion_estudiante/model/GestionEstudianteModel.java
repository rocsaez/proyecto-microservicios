package cl.duoc.gestion_estudiante.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GestionEstudianteModelTest {

    @Test
    @DisplayName("Constructor vacío y Setters - debe asignar los campos correctamente")
    void testGettersAndSetters() {
        GestionEstudianteModel model = new GestionEstudianteModel();
        model.setId(1L);
        model.setNombre("Diego Silva");
        model.setRut("11111111-1");
        model.setCorreo("diego@duocuc.cl");

        assertEquals(1L, model.getId());
        assertEquals("Diego Silva", model.getNombre());
        assertEquals("11111111-1", model.getRut());
        assertEquals("diego@duocuc.cl", model.getCorreo());
    }
}