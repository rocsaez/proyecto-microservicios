package cl.duoc.gestion_profesor.service;

import cl.duoc.gestion_profesor.dto.ProfesorCreateDTO;
import cl.duoc.gestion_profesor.dto.ProfesorDTO;
import cl.duoc.gestion_profesor.exceptions.RecursoNoEncontradoException;
import cl.duoc.gestion_profesor.model.GestionProfesorModel;
import cl.duoc.gestion_profesor.repository.GestionProfesorRepository;
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
class GestionProfesorServiceTest {

    @Mock
    private GestionProfesorRepository repository;

    @InjectMocks
    private GestionProfesorService service;

    @Test
    @DisplayName("obtenerTodos - debe retornar lista de profesores")
    void debeRetornarListaDeProfesores() {
        // Given
        GestionProfesorModel p = new GestionProfesorModel();
        p.setId(1L); p.setNombre("Carlos"); p.setAsignatura("Calidad"); p.setCorreo("c@d.cl");
        when(repository.findAll()).thenReturn(List.of(p));

        // When
        List<ProfesorDTO> resultado = service.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
    }

  @Test
@DisplayName("obtenerPorId - debe lanzar RecursoNoEncontradoException cuando no existe")
void debeLanzarExcepcionCuandoIdNoExiste() {
    // Given
    when(repository.findById(999L)).thenReturn(Optional.empty());

    // When & Then (En las excepciones, la acción y la aserción ocurren juntas)
    assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(999L));
}

    @Test
    @DisplayName("guardar - debe persistir un nuevo profesor")
    void debeGuardarProfesorCorrectamente() {
        // Given
        ProfesorCreateDTO dto = new ProfesorCreateDTO("Erick González", "Arquitectura", "e@d.cl");
        GestionProfesorModel guardado = new GestionProfesorModel();
        guardado.setId(1L); guardado.setNombre("Erick González");
        
        when(repository.save(any(GestionProfesorModel.class))).thenReturn(guardado);

        // When
        ProfesorDTO resultado = service.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }
}