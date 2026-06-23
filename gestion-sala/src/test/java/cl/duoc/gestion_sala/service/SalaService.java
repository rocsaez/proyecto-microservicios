package cl.duoc.gestion_sala.service;

import cl.duoc.gestion_sala.dto.SalaCreateDTO;
import cl.duoc.gestion_sala.dto.SalaDTO;
import cl.duoc.gestion_sala.exceptions.RecursoNoEncontradoException;
import cl.duoc.gestion_sala.model.SalaModel;
import cl.duoc.gestion_sala.repository.SalaRepository;
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
class SalaServiceTest {

    @Mock
    private SalaRepository repository;

    @InjectMocks
    private SalaService salaService;

    // ── obtenerTodas ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerTodas - debe retornar lista de salas cuando existen registros")
    void debeRetornarListaDeSalas() {
        // Given
        SalaModel s1 = new SalaModel(); s1.setId(1L); s1.setNombreSala("Laboratorio 402"); s1.setCapacidad(30);
        SalaModel s2 = new SalaModel(); s2.setId(2L); s2.setNombreSala("Auditorio"); s2.setCapacidad(150);
        
        when(repository.findAll()).thenReturn(List.of(s1, s2));

        // When
        List<SalaDTO> resultado = salaService.obtenerTodas();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Laboratorio 402", resultado.get(0).getNombreSala());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerTodas - debe retornar lista vacía cuando no hay salas")
    void debeRetornarListaVaciaSiNoHaySalas() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<SalaDTO> resultado = salaService.obtenerTodas();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ── obtenerPorId ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerPorId - debe retornar el DTO correcto cuando la sala existe")
    void debeRetornarSalaPorId() {
        // Given
        SalaModel sala = new SalaModel(); sala.setId(1L); sala.setNombreSala("Laboratorio 402"); sala.setCapacidad(30);
        when(repository.findById(1L)).thenReturn(Optional.of(sala));

        // When
        SalaDTO resultado = salaService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Laboratorio 402", resultado.getNombreSala());
    }

    @Test
    @DisplayName("obtenerPorId - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoSalaNoExiste() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            salaService.obtenerPorId(999L)
        );
    }

    // ── guardar ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("guardar - debe persistir y retornar la sala con ID generado")
    void debeCrearSalaCorrectamente() {
        // Given
        SalaCreateDTO dto = new SalaCreateDTO("Sala de Robótica", 20, "Laboratorio", "Piso 3");
        SalaModel guardado = new SalaModel(); 
        guardado.setId(3L); guardado.setNombreSala("Sala de Robótica"); guardado.setCapacidad(20);
        
        when(repository.save(any(SalaModel.class))).thenReturn(guardado);

        // When
        SalaDTO resultado = salaService.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Sala de Robótica", resultado.getNombreSala());
        verify(repository, times(1)).save(any(SalaModel.class));
    }

    // ── eliminar ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarSalaInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->
            salaService.eliminar(999L)
        );
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("eliminar - debe invocar deleteById cuando la sala existe")
    void debeEliminarSalaExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        salaService.eliminar(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }
}