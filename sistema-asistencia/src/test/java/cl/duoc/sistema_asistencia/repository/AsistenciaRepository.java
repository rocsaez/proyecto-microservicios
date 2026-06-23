package cl.duoc.sistema_asistencia.repository;

import cl.duoc.sistema_asistencia.model.Asistencia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AsistenciaRepositoryTest {

    @Autowired
    private AsistenciaRepository repository;

    @Test
    @DisplayName("save - debe persistir la asistencia y asignar un ID generado automáticamente")
    void debePersistirAsistenciaYAsignarIdGenerado() {
        // Given
        Asistencia asistencia = new Asistencia(null, "12345678-9", "INF4141", LocalDateTime.now());

        // When
        Asistencia guardado = repository.save(asistencia);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("12345678-9", guardado.getRutEstudiante());
        assertEquals("INF4141", guardado.getCodigoClase());
    }

    @Test
    @DisplayName("findAll - debe retornar todas las asistencias guardadas en la BD")
    void debeRetornarTodasLasAsistenciasGuardadas() {
        // Given
        repository.save(new Asistencia(null, "12345678-9", "INF4141", LocalDateTime.now()));
        repository.save(new Asistencia(null, "98765432-1", "MAT2020", LocalDateTime.now()));

        // When
        List<Asistencia> asistencias = repository.findAll();

        // Then
        assertNotNull(asistencias);
        assertEquals(2, asistencias.size());
    }

    @Test
    @DisplayName("findById - debe retornar la asistencia correcta cuando el ID existe")
    void debeEncontrarAsistenciaPorIdExistente() {
        // Given
        Asistencia guardado = repository.save(
            new Asistencia(null, "12345678-9", "INF4141", LocalDateTime.now())
        );

        // When
        Optional<Asistencia> resultado = repository.findById(guardado.getId());

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("12345678-9", resultado.get().getRutEstudiante());
    }

    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        // When
        Optional<Asistencia> resultado = repository.findById(999L);

        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("deleteById - debe eliminar la asistencia de la base de datos")
    void debeEliminarAsistenciaPorId() {
        // Given
        Asistencia guardado = repository.save(
            new Asistencia(null, "12345678-9", "INF4141", LocalDateTime.now())
        );
        Long id = guardado.getId();

        // When
        repository.deleteById(id);

        // Then
        assertFalse(repository.findById(id).isPresent());
    }
}