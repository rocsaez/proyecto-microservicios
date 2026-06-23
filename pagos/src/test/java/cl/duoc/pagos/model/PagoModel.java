package cl.duoc.pagos.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PagoModelTest {

    @Test
    @DisplayName("Model - estructura de datos asignable y getters")
    void testGettersAndSetters() {
        PagoModel modelo = new PagoModel();
        modelo.setId(1L);
        modelo.setIdEstudiante(5L);
        modelo.setMonto(25000.0);
        modelo.setFechaPago(LocalDate.of(2026, 6, 21));
        modelo.setEstado("PAGADO");

        assertEquals(1L, modelo.getId());
        assertEquals(5L, modelo.getIdEstudiante());
        assertEquals(25000.0, modelo.getMonto());
        assertNotNull(modelo.toString());
    }
}