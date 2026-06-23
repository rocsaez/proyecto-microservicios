package cl.duoc.beca.service;

import cl.duoc.beca.client.EstudianteClient;
import cl.duoc.beca.dto.BecaCreateDTO;
import cl.duoc.beca.dto.BecaDTO;
import cl.duoc.beca.dto.EstudianteDTO;
import cl.duoc.beca.exceptions.RecursoNoEncontradoException;
import cl.duoc.beca.model.BecaModel;
import cl.duoc.beca.repository.BecaRepository;
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
class BecaServiceTest {

    @Mock
    private BecaRepository repository;

    @Mock
    private EstudianteClient estudianteClient;

    @InjectMocks
    private BecaService becaService;

    // ── obtenerTodos ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerTodos - debe retornar lista de DTOs si existen becas")
    void debeRetornarListaDeBecas() {
        // Given
        List<BecaModel> simulados = List.of(
            new BecaModel(1L, "Beca Excelencia", 50.0, "12345678-9", "Activo")
        );
        when(repository.findAll()).thenReturn(simulados);

        // When
        List<BecaDTO> resultado = becaService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Beca Excelencia", resultado.get(0).getNombreBeca());
    }

    // ── obtenerPorId ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerPorId - debe retornar el DTO correcto cuando la beca existe")
    void debeRetornarBecaPorId() {
        // Given
        BecaModel model = new BecaModel(1L, "Beca Excelencia", 50.0, "12345678-9", "Activo");
        when(repository.findById(1L)).thenReturn(Optional.of(model));

        // When
        BecaDTO resultado = becaService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("obtenerPorId - debe lanzar RecursoNoEncontradoException si el ID no existe")
    void debeLanzarExcepcionBecaInexistente() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> becaService.obtenerPorId(999L));
    }

    // ── guardar (Con Validación Feign) ────────────────────────────────────────

    @Test
    @DisplayName("guardar - debe registrar la beca si el estudiante existe en el microservicio externo")
    void debeGuardarBecaSiEstudianteExiste() {
        // Given
        BecaCreateDTO dto = new BecaCreateDTO("Beca Excelencia", "12345678-9", 50.0, "Activo");
        EstudianteDTO estudianteSimulado = new EstudianteDTO(10L, "Juan Pérez", "12345678-9", "juan@duoc.cl");
        BecaModel guardado = new BecaModel(1L, "Beca Excelencia", 50.0, "12345678-9", "Activo");

        // Simulamos que Feign SÍ encuentra al estudiante y el Repositorio guarda
        when(estudianteClient.buscarPorRut("12345678-9")).thenReturn(estudianteSimulado);
        when(repository.save(any(BecaModel.class))).thenReturn(guardado);

        // When
        BecaDTO resultado = becaService.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(estudianteClient, times(1)).buscarPorRut("12345678-9");
        verify(repository, times(1)).save(any(BecaModel.class));
    }

    // ── eliminar ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarBecaInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> becaService.eliminar(999L));
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("eliminar - debe invocar deleteById cuando la beca existe")
    void debeEliminarBecaExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        becaService.eliminar(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }
}