package cl.duoc.sistema_inscripcion.repository;

import cl.duoc.sistema_inscripcion.model.SistemaInscripcionesModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SistemaInscripcionesRepositoryTest {

    @Autowired
    private SistemaInscripcionesRepository repository;

    @Test
    @DisplayName("save - debe persistir la inscripción y asignar un ID dinámico")
    void debePersistirInscripcionYAsignarId() {
        // Given
        SistemaInscripcionesModel modelo = new SistemaInscripcionesModel(null, "12345678-9", "ASY4131", "2026-1");

        // When
        SistemaInscripcionesModel guardado = repository.save(modelo);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("12345678-9", guardado.getRutEstudiante());
    }

    @Test
    @DisplayName("existsByRutEstudianteAndNombreAsignaturaAndPeriodo - debe retornar true si ya existe el registro")
    void debeRetornarTrueCuandoExisteDuplicado() {
        // Given
        repository.save(new SistemaInscripcionesModel(null, "12345678-9", "ASY4131", "2026-1"));

        // When
        boolean existe = repository.existsByRutEstudianteAndNombreAsignaturaAndPeriodo("12345678-9", "ASY4131", "2026-1");

        // Then
        assertTrue(existe);
    }

    @Test
    @DisplayName("findAll - debe retornar el listado completo de la base de datos")
    void debeRetornarTodasLasInscripciones() {
        // Given
        repository.save(new SistemaInscripcionesModel(null, "12345678-9", "ASY4131", "2026-1"));
        repository.save(new SistemaInscripcionesModel(null, "9876543-2", "MDY3131", "2026-1"));

        // When
        List<SistemaInscripcionesModel> lista = repository.findAll();

        // Then
        assertNotNull(lista);
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("deleteById - debe vaciar o remover el registro correctamente")
    void debeEliminarInscripcionPorId() {
        // Given
        SistemaInscripcionesModel guardado = repository.save(
            new SistemaInscripcionesModel(null, "12345678-9", "ASY4131", "2026-1")
        );
        Long id = guardado.getId();

        // When
        repository.deleteById(id);

        // Then
        assertFalse(repository.findById(id).isPresent());
    }
}