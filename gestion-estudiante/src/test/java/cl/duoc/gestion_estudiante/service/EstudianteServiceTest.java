package cl.duoc.gestion_estudiante.service;

import cl.duoc.gestion_estudiante.dto.EstudianteCreateDTO;
import cl.duoc.gestion_estudiante.dto.EstudianteDTO;
import cl.duoc.gestion_estudiante.exceptions.RecursoNoEncontradoException;
import cl.duoc.gestion_estudiante.model.GestionEstudianteModel;
import cl.duoc.gestion_estudiante.repository.GestionEstudianteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Evita que Mockito falle por stubs no usados en flujos alternativos
class GestionEstudianteServiceTest {

    @Mock
    private GestionEstudianteRepository repository;

    @InjectMocks
    private GestionEstudianteService service;

    private GestionEstudianteModel estudianteModel;
    private EstudianteCreateDTO estudianteCreateDTO;

    @BeforeEach
    void setUp() {
        estudianteModel = new GestionEstudianteModel();
        estudianteModel.setId(1L);
        estudianteModel.setNombre("Juan Pérez");
        estudianteModel.setRut("12345678-9");
        estudianteModel.setCorreo("juan.perez@duocuc.cl");

        estudianteCreateDTO = new EstudianteCreateDTO("Juan Pérez", "12345678-9", "juan.perez@duocuc.cl");
    }

    @Test
    @DisplayName("guardarEstudiante - Debe registrar y retornar el estudiante DTO")
    void debeGuardarEstudianteExitosamente() {
        // Given
        when(repository.findByRut("12345678-9")).thenReturn(Optional.empty());
        when(repository.save(any(GestionEstudianteModel.class))).thenReturn(estudianteModel);

        // When
        EstudianteDTO resultado = service.guardarEstudiante(estudianteCreateDTO);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombre());
        verify(repository, times(1)).save(any(GestionEstudianteModel.class));
    }

    @Test
    @DisplayName("guardarEstudiante - Debe lanzar IllegalArgumentException si el RUT ya existe")
    void debeLanzarExcepcionCuandoRutYaExiste() {
        // Given
        when(repository.findByRut("12345678-9")).thenReturn(Optional.of(estudianteModel));

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            service.guardarEstudiante(estudianteCreateDTO);
        });
        verify(repository, never()).save(any(GestionEstudianteModel.class));
    }

    @Test
    @DisplayName("obtenerTodos - Debe retornar la lista completa de estudiantes")
    void debeRetornarTodosLosEstudiantes() {
        // Given
        when(repository.findAll()).thenReturn(List.of(estudianteModel));

        // When
        List<EstudianteDTO> resultado = service.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRut());
    }

    @Test
    @DisplayName("obtenerPorId - Debe lanzar RecursoNoEncontradoException si el ID no existe")
    void debeLanzarExcepcionCuandoIdNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Then
        assertThrows(RecursoNoEncontradoException.class, () -> {
            service.obtenerPorId(99L);
        });
    }

    @Test
    @DisplayName("eliminarPorId - Debe eliminar correctamente si el ID existe")
    void debeEliminarSiIdExiste() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        // When
        assertDoesNotThrow(() -> service.eliminarPorId(1L));

        // Then
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarPorId - Debe lanzar excepción si el ID a eliminar no existe")
    void debeLanzarExcepcionAlEliminarIdInexistente() {
        // Given
        when(repository.existsById(99L)).thenReturn(false);

        // Then
        assertThrows(RecursoNoEncontradoException.class, () -> {
            service.eliminarPorId(99L);
        });
        verify(repository, never()).deleteById(anyLong());
    }
}