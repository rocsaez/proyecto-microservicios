package cl.duoc.sistema_inscripcion.service;

import cl.duoc.sistema_inscripcion.client.AsignaturaClient;
import cl.duoc.sistema_inscripcion.client.EstudianteClient;
import cl.duoc.sistema_inscripcion.dto.AsignaturaDTO;
import cl.duoc.sistema_inscripcion.dto.EstudianteDTO;
import cl.duoc.sistema_inscripcion.dto.InscripcionCreateDTO;
import cl.duoc.sistema_inscripcion.dto.InscripcionDTO;
import cl.duoc.sistema_inscripcion.exceptions.RecursoNoEncontradoException;
import cl.duoc.sistema_inscripcion.model.SistemaInscripcionesModel;
import cl.duoc.sistema_inscripcion.repository.SistemaInscripcionesRepository;
import feign.FeignException;
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
class SistemaInscripcionesServiceTest {

    @Mock
    private SistemaInscripcionesRepository repository;

    @Mock
    private EstudianteClient estudianteClient;

    @Mock
    private AsignaturaClient asignaturaClient;

    @InjectMocks
    private SistemaInscripcionesService service;

    // ── obtenerTodas ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerTodas - debe retornar lista de DTOs mapeados")
    void debeRetornarListaDeInscripciones() {
        // Given
        List<SistemaInscripcionesModel> simulados = List.of(
            new SistemaInscripcionesModel(1L, "12345678-9", "ASY4131", "2026-1")
        );
        when(repository.findAll()).thenReturn(simulados);

        // When
        List<InscripcionDTO> resultado = service.obtenerTodas();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRutEstudiante());
    }

    // ── guardar ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("guardar - debe inscribir correctamente si los clientes Feign responden OK y no hay duplicados")
    void debeGuardarInscripcionExitosamente() {
        // Given
        InscripcionCreateDTO dto = new InscripcionCreateDTO("12345678-9", "ASY4131", "2026-1");

        // Simulamos respuestas correctas de clientes externos
        when(estudianteClient.obtenerPorRut("12345678-9")).thenReturn(new EstudianteDTO(1L, "Juan Perez", "12345678-9", "juan@duoc.cl"));
        when(asignaturaClient.buscarPorSigla("ASY4131")).thenReturn(new AsignaturaDTO(1L, "Arquitectura", "ASY4131", 4));
        
        // Simulamos que no es duplicado localmente
        when(repository.existsByRutEstudianteAndNombreAsignaturaAndPeriodo("12345678-9", "ASY4131", "2026-1")).thenReturn(false);

        SistemaInscripcionesModel guardado = new SistemaInscripcionesModel(1L, "12345678-9", "ASY4131", "2026-1");
        when(repository.save(any(SistemaInscripcionesModel.class))).thenReturn(guardado);

        // When
        InscripcionDTO resultado = service.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("12345678-9", resultado.getRutEstudiante());
        verify(repository, times(1)).save(any(SistemaInscripcionesModel.class));
    }

    @Test
    @DisplayName("guardar - debe lanzar RecursoNoEncontradoException si Feign retorna 404 en Estudiante")
    void debeLanzarExcepcionSiEstudianteNoExiste() {
        // Given
        InscripcionCreateDTO dto = new InscripcionCreateDTO("12345678-9", "ASY4131", "2026-1");
        
        // Simulamos que Feign lanza un 404 Not Found
        when(estudianteClient.obtenerPorRut("12345678-9")).thenThrow(FeignException.NotFound.class);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> service.guardar(dto));
        verify(repository, never()).save(any());
    }

    // ── eliminar ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar - debe lanzar excepción de recurso no encontrado si el ID no existe en BD")
    void debeLanzarExcepcionAlEliminarInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> service.eliminar(999L));
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("eliminar - debe invocar deleteById si el ID existe")
    void debeEliminarRegistroExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        service.eliminar(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }
}