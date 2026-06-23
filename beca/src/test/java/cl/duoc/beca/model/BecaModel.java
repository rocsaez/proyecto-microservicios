package cl.duoc.beca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BecaModelTest {

    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        BecaModel beca = new BecaModel();
        assertNotNull(beca);
    }

    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        BecaModel beca = new BecaModel(1L, "Beca Excelencia", 50.0, "12345678-9", "Activo");

        assertEquals(1L, beca.getId());
        assertEquals("Beca Excelencia", beca.getNombreBeca());
        assertEquals(50.0, beca.getPorcentajeDescuento());
        assertEquals("12345678-9", beca.getRutEstudiante());
        assertEquals("Activo", beca.getEstado());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void settersDebenPermitirModificarCampos() {
        BecaModel beca = new BecaModel();

        beca.setId(2L);
        beca.setNombreBeca("Beca Prueba");
        beca.setPorcentajeDescuento(15.0);
        beca.setRutEstudiante("11111111-1");
        beca.setEstado("Inactivo");

        assertEquals(2L, beca.getId());
        assertEquals("Beca Prueba", beca.getNombreBeca());
        assertEquals(15.0, beca.getPorcentajeDescuento());
        assertEquals("11111111-1", beca.getRutEstudiante());
        assertEquals("Inactivo", beca.getEstado());
    }

    @Test
    @DisplayName("equals y hashCode - dos becas con los mismos datos deben ser iguales")
    void dosBecasConMismosDatosDebenSerIguales() {
        BecaModel b1 = new BecaModel(1L, "Beca Excelencia", 50.0, "12345678-9", "Activo");
        BecaModel b2 = new BecaModel(1L, "Beca Excelencia", 50.0, "12345678-9", "Activo");

        assertEquals(b1, b2);
        assertEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    @DisplayName("toString - debe contener el nombre de la beca en la representación")
    void toStringDebeContenerNombreDeLaBeca() {
        BecaModel beca = new BecaModel(1L, "Beca Excelencia", 50.0, "12345678-9", "Activo");

        String texto = beca.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("Beca Excelencia"));
    }
}