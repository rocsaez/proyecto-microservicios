package cl.duoc.gestion_profesor.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestionProfesorModelTest {

    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        GestionProfesorModel profesor = new GestionProfesorModel();
        assertNotNull(profesor);
    }

    @Test
    @DisplayName("Setters y Getters - debe permitir modificar y obtener cada campo")
    void settersYGettersDebenFuncionarCorrectamente() {
        GestionProfesorModel profesor = new GestionProfesorModel();

        profesor.setId(1L);
        profesor.setNombre("Erick González");
        profesor.setAsignatura("Arquitectura de Software");
        profesor.setCorreo("erick.gonzalez@duocuc.cl");

        assertEquals(1L, profesor.getId());
        assertEquals("Erick González", profesor.getNombre());
        assertEquals("Arquitectura de Software", profesor.getAsignatura());
        assertEquals("erick.gonzalez@duocuc.cl", profesor.getCorreo());
    }

    @Test
    @DisplayName("equals y hashCode - dos modelos con los mismos datos deben ser iguales")
    void dosProfesoresConMismosDatosDebenSerIguales() {
        GestionProfesorModel p1 = new GestionProfesorModel();
        p1.setId(1L); p1.setNombre("Carlos"); p1.setAsignatura("Calidad"); p1.setCorreo("c@d.cl");

        GestionProfesorModel p2 = new GestionProfesorModel();
        p2.setId(1L); p2.setNombre("Carlos"); p2.setAsignatura("Calidad"); p2.setCorreo("c@d.cl");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}