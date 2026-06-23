package cl.duoc.asignaturas.service;

import cl.duoc.asignaturas.client.CarreraClient;
import cl.duoc.asignaturas.client.ProfesorClient;
import cl.duoc.asignaturas.dto.AsignaturaCreateDTO;
import cl.duoc.asignaturas.dto.AsignaturaDTO;
import cl.duoc.asignaturas.exceptions.RecursoNoEncontradoException;
import cl.duoc.asignaturas.model.AsignaturaModel;
import cl.duoc.asignaturas.repository.AsignaturaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsignaturaServiceTest {

    @Mock
    private AsignaturaRepository repository;

    @Mock
    private CarreraClient carreraClient;

    @Mock
    private ProfesorClient profesorClient;

    @InjectMocks
    private AsignaturaService service;

    @Test
    @DisplayName("guardar - debe registrar una asignatura exitosamente y retornar su DTO")
    void debeGuardarAsignaturaExitosamente() {
        // Given
        AsignaturaCreateDTO dtoInput = new AsignaturaCreateDTO("Desarrollo Web", "MDY3102", 6, "ING-INF", 10L);
        
        AsignaturaModel modelGuardado = new AsignaturaModel();
        modelGuardado.setId(100L);
        modelGuardado.setNombre("Desarrollo Web");
        modelGuardado.setSigla("MDY3102");
        modelGuardado.setCreditos(6);

        // Simulamos el comportamiento del JpaRepository.save()
        when(repository.save(any(AsignaturaModel.class))).thenReturn(modelGuardado);

        // When
        AsignaturaDTO resultado = service.guardar(dtoInput);

        // Then
        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        assertEquals("Desarrollo Web", resultado.getNombre());
        assertEquals("MDY3102", resultado.getSigla());
        
        // Verificamos que efectivamente se llamó al save una vez
        verify(repository, times(1)).save(any(AsignaturaModel.class));
    }

    @Test
    @DisplayName("obtenerPorId - debe retornar el DTO correspondiente cuando el ID existe")
    void debeObtenerPorIdExistente() {
        // Given
        AsignaturaModel model = new AsignaturaModel();
        model.setId(1L);
        model.setNombre("Arquitectura");
        model.setSigla("ARQ4402");
        model.setCreditos(4);

        when(repository.findById(1L)).thenReturn(Optional.of(model));

        // When
        AsignaturaDTO resultado = service.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals("Arquitectura", resultado.getNombre());
        assertEquals("ARQ4402", resultado.getSigla());
    }

    @Test
    @DisplayName("obtenerPorId - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoIdNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> {
            service.obtenerPorId(99L);
        });
    }

    // ── METODO ELIMINAR CORREGIDO (YA NO BUSCA UN BOOLEAN) ───────────────────
    @Test
    @DisplayName("eliminar - debe invocar deleteById exitosamente si la asignatura existe")
    void debeEliminarAsignaturaExistente() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        // When
        service.eliminar(1L); // Se invoca directo sin asignarlo a variables

        // Then
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar - debe lanzar RecursoNoEncontradoException si el ID no existe")
    void debeLanzarExcepcionAlEliminarInexistente() {
        // Given
        when(repository.existsById(99L)).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> {
            service.eliminar(99L);
        });

        // Verificamos que NUNCA intentó borrar en el repositorio si no existía
        verify(repository, never()).deleteById(anyLong());
    }
}