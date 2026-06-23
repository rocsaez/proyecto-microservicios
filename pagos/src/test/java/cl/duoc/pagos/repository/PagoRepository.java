package cl.duoc.pagos.repository;

import cl.duoc.pagos.model.PagoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PagoRepositoryTest {

    @Autowired
    private PagoRepository repository;

    @Test
    @DisplayName("save - debe persistir el registro de pago en H2")
    void debePersistirPagoCorrectamente() {
        // Given
        PagoModel pago = new PagoModel();
        pago.setIdEstudiante(12L);
        pago.setMonto(120000.0);
        pago.setFechaPago(LocalDate.now());
        pago.setEstado("PAGADO");

        // When
        PagoModel guardado = repository.save(pago);

        // Then
        assertNotNull(guardado.getId());
        assertEquals(120000.0, guardado.getMonto());
    }

    @Test
    @DisplayName("findAll - debe recuperar todos los registros")
    void debeRetornarListaDePagos() {
        // Given
        PagoModel p1 = new PagoModel(); p1.setIdEstudiante(1L); p1.setMonto(10.0);
        PagoModel p2 = new PagoModel(); p2.setIdEstudiante(2L); p2.setMonto(20.0);
        repository.saveAll(List.of(p1, p2));

        // When
        List<PagoModel> lista = repository.findAll();

        // Then
        assertEquals(2, lista.size());
    }
}