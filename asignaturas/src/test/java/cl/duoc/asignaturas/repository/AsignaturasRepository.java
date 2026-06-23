package cl.duoc.asignaturas.repository;

import cl.duoc.asignaturas.model.AsignaturaModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AsignaturaRepositoryTest {

    @Autowired
    private AsignaturaRepository repository;

    @Test
    @DisplayName("findBySigla - debe retornar la asignatura correcta cuando la sigla existe")
    void debeEncontrarAsignaturaPorSiglaExistente() {
        // Given
        AsignaturaModel asignatura = new AsignaturaModel();
        asignatura.setNombre("Diseño de Software");
        asignatura.setSigla("DDS4401");
        asignatura.setCreditos(4);
        repository.save(asignatura);

        // When
        Optional<AsignaturaModel> resultado = repository.findBySigla("DDS4401");

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Diseño de Software", resultado.get().getNombre());
        assertEquals(4, resultado.get().getCreditos());
    }

    @Test
    @DisplayName("findBySigla - debe retornar un Optional vacío si la sigla no existe")
    void debeRetornarOptionalVacioCuandoSiglaNoExiste() {
        // When
        Optional<AsignaturaModel> resultado = repository.findBySigla("NON_EXISTENT");

        // Then
        assertFalse(resultado.isPresent());
    }
}