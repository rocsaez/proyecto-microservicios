package cl.duoc.evaluacion.service;

import cl.duoc.evaluacion.client.InscripcionClient;
import cl.duoc.evaluacion.dto.EvaluacionCreateDTO;
import cl.duoc.evaluacion.dto.EvaluacionDTO;
import cl.duoc.evaluacion.exceptions.RecursoNoEncontradoException;
import cl.duoc.evaluacion.model.EvaluacionModel;
import cl.duoc.evaluacion.repository.EvaluacionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository repository;

    @Mock
    private InscripcionClient inscripcionClient;

    @InjectMocks
    private EvaluacionService evaluacionService;

    // ── obtenerTodos ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerTodos - debe retornar lista de DTOs mapeados cuando hay datos")
    void debeRetornarListaDeEvaluaciones() {
        // Given
        List<EvaluacionModel> modelosSimulados = List.of(
            new EvaluacionModel(1L, "19234567-8", "ASY4131", 6.5),
            new EvaluacionModel(2L, "18456123-K", "ASY4131", 5.2)
        );
        when(repository.findAll()).thenReturn(modelosSimulados);

        // When
        List<EvaluacionDTO> resultado = evaluacionService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("19234567-8", resultado.get(0).getNombreEstudiante());
        verify(repository, times(1)).findAll();
    }

    // ── obtenerPorId ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerPorId - debe retornar el DTO correcto cuando la evaluación existe")
    void debeRetornarEvaluacionPorId() {
        // Given
        EvaluacionModel model = new EvaluacionModel(1L, "19234567-8", "ASY4131", 6.5);
        when(repository.findById(1L)).thenReturn(Optional.of(model));

        // When
        EvaluacionDTO resultado = evaluacionService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("19234567-8", resultado.getNombreEstudiante());
    }

    @Test
    @DisplayName("obtenerPorId - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoIdNoExiste() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            evaluacionService.obtenerPorId(999L)
        );
    }

    // ── guardar ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("guardar - debe persistir exitosamente si el alumno tiene inscripciones vigentes por Feign")
    void debeGuardarEvaluacionCorrectamente() {
        // Given
        EvaluacionCreateDTO dto = new EvaluacionCreateDTO("19234567-8", "ASY4131", 7.0);
        
        // Simular que el cliente feign encuentra inscripciones (Lista no vacía con datos ficticios)
        when(inscripcionClient.obtenerInscripcionesPorRut("19234567-8")).thenReturn(List.of("InscripcionActiva"));
        
        EvaluacionModel guardado = new EvaluacionModel(1L, "19234567-8", "ASY4131", 7.0);
        when(repository.save(any(EvaluacionModel.class))).thenReturn(guardado);

        // When
        EvaluacionDTO resultado = evaluacionService.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(7.0, resultado.getNota());
        verify(repository, times(1)).save(any(EvaluacionModel.class));
    }

    // ── eliminar ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar - debe lanzar excepción de recurso no encontrado si el ID no existe")
    void debeLanzarExcepcionAlEliminarInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            evaluacionService.eliminar(999L)
        );
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("eliminar - debe invocar deleteById si el registro de la nota existe")
    void debeEliminarRegistroExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        evaluacionService.eliminar(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }
}