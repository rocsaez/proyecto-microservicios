package cl.duoc.sistema_asistencia.service;

import cl.duoc.sistema_asistencia.dto.AsistenciaCreateDTO;
import cl.duoc.sistema_asistencia.dto.AsistenciaDTO;
import cl.duoc.sistema_asistencia.exception.RecursoNoEncontradoException;
import cl.duoc.sistema_asistencia.model.Asistencia;
import cl.duoc.sistema_asistencia.repository.AsistenciaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsistenciaServiceTest {

    @Mock
    private AsistenciaRepository repository;

    @InjectMocks
    private AsistenciaService asistenciaService;

    // ── findAll ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll - debe retornar lista de asistencias cuando existen registros")
    void debeRetornarListaDeAsistencias() {
        // Given
        LocalDateTime ahora = LocalDateTime.now();
        List<Asistencia> asistenciasSimuladas = List.of(
            new Asistencia(1L, "12345678-9", "INF4141", ahora),
            new Asistencia(2L, "98765432-1", "MAT2020", ahora)
        );
        when(repository.findAll()).thenReturn(asistenciasSimuladas);

        // When
        List<AsistenciaDTO> resultado = asistenciaService.findAll();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRutEstudiante());
        assertEquals("INF4141", resultado.get(0).getCodigoClase());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll - debe retornar lista vacía cuando no hay asistencias")
    void debeRetornarListaVaciaSiNoHayAsistencias() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<AsistenciaDTO> resultado = asistenciaService.findAll();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ── findById ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findById - debe retornar el DTO correcto cuando la asistencia existe")
    void debeRetornarAsistenciaPorId() {
        // Given
        Asistencia asistencia = new Asistencia(1L, "12345678-9", "INF4141", LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(asistencia));

        // When
        AsistenciaDTO resultado = asistenciaService.findById(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("12345678-9", resultado.getRutEstudiante());
        assertEquals("INF4141", resultado.getCodigoClase());
    }

    @Test
    @DisplayName("findById - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoAsistenciaNoExiste() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            asistenciaService.findById(999L)
        );
    }

    // ── crear ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("crear - debe persistir y retornar la asistencia con ID generado")
    void debeCrearAsistenciaCorrectamente() {
        // Given
        AsistenciaCreateDTO dto = new AsistenciaCreateDTO("12345678-9", "INF4141");
        Asistencia guardado = new Asistencia(3L, "12345678-9", "INF4141", LocalDateTime.now());
        when(repository.save(any(Asistencia.class))).thenReturn(guardado);

        // When
        AsistenciaDTO resultado = asistenciaService.crear(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("12345678-9", resultado.getRutEstudiante());
        assertEquals("INF4141", resultado.getCodigoClase());
        verify(repository, times(1)).save(any(Asistencia.class));
    }

    // ── eliminar ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarAsistenciaInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            asistenciaService.eliminar(999L)
        );
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("eliminar - debe invocar deleteById cuando la asistencia existe")
    void debeEliminarAsistenciaExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        asistenciaService.eliminar(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }
}