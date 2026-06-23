package cl.duoc.carrera.service;

import cl.duoc.carrera.dto.CarreraCreateDTO;
import cl.duoc.carrera.dto.CarreraDTO;
import cl.duoc.carrera.exceptions.RecursoNoEncontradoException;
import cl.duoc.carrera.model.CarreraModel;
import cl.duoc.carrera.repository.CarreraRepository;
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
class CarreraServiceTest {

    @Mock
    private CarreraRepository repository;

    @InjectMocks
    private CarreraService carreraService;

    // ── obtenerTodas ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerTodas - debe retornar lista de carreras cuando existen registros")
    void debeRetornarListaDeCarreras() {
        // Given
        List<CarreraModel> carrerasSimuladas = List.of(
            new CarreraModel(1L, "Ingeniería en Informática", "Ingeniería", 8),
            new CarreraModel(2L, "Diseño Gráfico", "Diseño", 8)
        );
        when(repository.findAll()).thenReturn(carrerasSimuladas);

        // When
        List<CarreraDTO> resultado = carreraService.obtenerTodas();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Ingeniería en Informática", resultado.get(0).getNombre());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerTodas - debe retornar lista vacía cuando no hay carreras")
    void debeRetornarListaVaciaSiNoHayCarreras() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<CarreraDTO> resultado = carreraService.obtenerTodas();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ── obtenerPorId ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerPorId - debe retornar el DTO correcto cuando la carrera existe")
    void debeRetornarCarreraPorId() {
        // Given
        CarreraModel model = new CarreraModel(1L, "Ingeniería en Informática", "Ingeniería", 8);
        when(repository.findById(1L)).thenReturn(Optional.of(model));

        // When
        CarreraDTO resultado = carreraService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Ingeniería en Informática", resultado.getNombre());
    }

    @Test
    @DisplayName("obtenerPorId - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoCarreraNoExiste() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            carreraService.obtenerPorId(999L)
        );
    }

    // ── guardar ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("guardar - debe persistir y retornar la carrera con ID generado")
    void debeCrearCarreraCorrectamente() {
        // Given
        CarreraCreateDTO dto = new CarreraCreateDTO("Enfermería", "Salud", 10);
        CarreraModel guardado = new CarreraModel(3L, "Enfermería", "Salud", 10);
        when(repository.save(any(CarreraModel.class))).thenReturn(guardado);

        // When
        CarreraDTO resultado = carreraService.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Enfermería", resultado.getNombre());
        verify(repository, times(1)).save(any(CarreraModel.class));
    }

    // ── eliminar ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarCarreraInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            carreraService.eliminar(999L)
        );
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("eliminar - debe invocar deleteById cuando la carrera existe")
    void debeEliminarCarreraExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        carreraService.eliminar(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }
}