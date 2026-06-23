package cl.duoc.evaluacion.repository;

import cl.duoc.evaluacion.model.EvaluacionModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EvaluacionRepositoryTest {

    @Autowired
    private EvaluacionRepository repository;

    @Test
    @DisplayName("save - debe persistir el registro y asignar una clave primaria auto-incremental")
    void debePersistirEvaluacionYAsignarId() {
        // Given
        EvaluacionModel evaluacion = new EvaluacionModel(null, "19234567-8", "ASY4131", 6.2);

        // When
        EvaluacionModel guardado = repository.save(evaluacion);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("19234567-8", guardado.getNombreEstudiante());
        assertEquals(6.2, guardado.getNota());
    }

    @Test
    @DisplayName("findAll - debe retornar el listado completo de notas en la BD")
    void debeRetornarTodasLasEvaluaciones() {
        // Given
        repository.save(new EvaluacionModel(null, "19234567-8", "ASY4131", 5.8));
        repository.save(new EvaluacionModel(null, "18456123-K", "MDY3131", 4.5));

        // When
        List<EvaluacionModel> lista = repository.findAll();

        // Then
        assertNotNull(lista);
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("findById - debe retornar la evaluación correspondiente cuando el ID es válido")
    void debeEncontrarEvaluacionPorId() {
        // Given
        EvaluacionModel guardado = repository.save(
            new EvaluacionModel(null, "19234567-8", "ASY4131", 7.0)
        );

        // When
        Optional<EvaluacionModel> resultado = repository.findById(guardado.getId());

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("19234567-8", resultado.get().getNombreEstudiante());
        assertEquals(7.0, resultado.get().getNota());
    }

    @Test
    @DisplayName("deleteById - debe remover físicamente el registro de la BD")
    void debeEliminarEvaluacionPorId() {
        // Given
        EvaluacionModel guardado = repository.save(
            new EvaluacionModel(null, "19234567-8", "ASY4131", 5.5)
        );
        Long id = guardado.getId();

        // When
        repository.deleteById(id);

        // Then
        assertFalse(repository.findById(id).isPresent());
    }
}