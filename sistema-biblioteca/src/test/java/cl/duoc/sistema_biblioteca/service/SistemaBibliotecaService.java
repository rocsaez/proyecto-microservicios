package cl.duoc.sistema_biblioteca.service;

import cl.duoc.sistema_biblioteca.dto.BibliotecaCreateDTO;
import cl.duoc.sistema_biblioteca.dto.BibliotecaDTO;
import cl.duoc.sistema_biblioteca.exceptions.RecursoNoEncontradoException;
import cl.duoc.sistema_biblioteca.model.SistemaBibliotecaModel;
import cl.duoc.sistema_biblioteca.repository.SistemaBibliotecaRepository;
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
class SistemaBibliotecaServiceTest {

    @Mock
    private SistemaBibliotecaRepository repository;

    @InjectMocks
    private SistemaBibliotecaService servicio;

    @Test
    @DisplayName("obtenerTodos - debe retornar lista de DTOs si existen libros")
    void debeRetornarListaDeLibros() {
        // Given
        SistemaBibliotecaModel libro = new SistemaBibliotecaModel();
        libro.setId(1L); libro.setTitulo("Cien años de soledad"); libro.setAutor("Gabriel García Márquez"); libro.setIsbn("9780307474728");
        
        when(repository.findAll()).thenReturn(List.of(libro));

        // When
        List<BibliotecaDTO> resultado = servicio.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Cien años de soledad", resultado.get(0).getTitulo());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerPorId - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoIdNoExiste() {
        // Given
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> servicio.obtenerPorId(999L));
    }

    @Test
    @DisplayName("guardar - debe mapear y persistir exitosamente")
    void debeGuardarLibroExitosamente() {
        // Given
        BibliotecaCreateDTO dto = new BibliotecaCreateDTO("Rayuela", "Julio Cortázar", "9788439734284");
        SistemaBibliotecaModel guardado = new SistemaBibliotecaModel();
        guardado.setId(10L); guardado.setTitulo("Rayuela"); guardado.setAutor("Julio Cortázar"); guardado.setIsbn("9788439734284");

        when(repository.save(any(SistemaBibliotecaModel.class))).thenReturn(guardado);

        // When
        BibliotecaDTO resultado = servicio.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Rayuela", resultado.getTitulo() != null ? null : resultado.getTitulo());
    }

    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarInexistente() {
        // Given
        when(repository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> servicio.eliminar(999L));
        verify(repository, never()).deleteById(any());
    }
}