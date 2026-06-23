package cl.duoc.beca.repository;

import cl.duoc.beca.model.BecaModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BecaRepositoryTest {

    @Autowired
    private BecaRepository repository;

    @Test
    @DisplayName("save - debe persistir la beca y asignar un ID generado automáticamente")
    void debePersistirBecaYAsignarIdGenerado() {
        // Given
        BecaModel beca = new BecaModel(null, "Beca Conectividad", 100.0, "22222222-2", "Activo");

        // When
        BecaModel guardado = repository.save(beca);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("Beca Conectividad", guardado.getNombreBeca());
    }

    @Test
    @DisplayName("findAll - debe retornar todas las becas guardadas en la BD")
    void debeRetornarTodasLasBecasGuardadas() {
        // Given
        repository.save(new BecaModel(null, "Beca A", 10.0, "1-1", "Activo"));
        repository.save(new BecaModel(null, "Beca B", 20.0, "2-2", "Activo"));

        // When
        List<BecaModel> lista = repository.findAll();

        // Then
        assertNotNull(lista);
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("findById - debe retornar la beca correcta cuando el ID existe")
    void debeEncontrarBecaPorIdExistente() {
        // Given
        BecaModel guardado = repository.save(new BecaModel(null, "Beca Parcial", 30.0, "3-3", "Activo"));

        // When
        Optional<BecaModel> resultado = repository.findById(guardado.getId());

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Beca Parcial", resultado.get().getNombreBeca());
    }

    @Test
    @DisplayName("deleteById - debe eliminar la beca de la base de datos")
    void debeEliminarBecaPorId() {
        // Given
        BecaModel guardado = repository.save(new BecaModel(null, "Beca Temporal", 15.0, "4-4", "Activo"));
        Long id = guardado.getId();

        // When
        repository.deleteById(id);

        // Then
        assertFalse(repository.findById(id).isPresent());
    }
}