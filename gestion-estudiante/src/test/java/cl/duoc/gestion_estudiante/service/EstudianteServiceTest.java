package cl.duoc.gestion_estudiante.service;

import cl.duoc.gestion_estudiante.model.GestionEstudianteModel;
import cl.duoc.gestion_estudiante.repository.GestionEstudianteRepository;
import cl.duoc.gestion_estudiante.dto.EstudianteDTO;
import cl.duoc.gestion_estudiante.exceptions.RecursoNoEncontradoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    
    @Mock
    private GestionEstudianteRepository repository; 

    // 💉 Inyecta automáticamente el repositorio falso dentro del servicio real
    @InjectMocks
    private GestionEstudianteService service; 

    @Test
    @DisplayName("Debe retornar el estudiante cuando el ID existe")
    void debeRetornarEstudianteCuandoIdExiste() {
        // GIVEN 
        GestionEstudianteModel modeloSimulado = new GestionEstudianteModel();
        modeloSimulado.setId(1L);
        modeloSimulado.setNombre("Juan Perez"); 
        
        when(repository.findById(1L)).thenReturn(Optional.of(modeloSimulado));

        // WHEN 
        EstudianteDTO resultado = service.obtenerPorId(1L); 

        // THEN 
        assertNotNull(resultado, "El DTO de retorno no debería ser nulo");
        
        // 🔎 Verificamos que el repositorio falso haya sido consultado exactamente una vez
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar RecursoNoEncontradoException cuando el estudiante no existe")
    void debeLanzarExcepcionCuandoIdNoExiste() {
        // GIVEN 
        when(repository.findById(2L)).thenReturn(Optional.empty());

        // WHEN & THEN (Act & Assert): Evaluamos que el método lance la excepción esperada (Error 404)
        assertThrows(RecursoNoEncontradoException.class, () -> {
            service.obtenerPorId(2L);
        }, "Se esperaba la excepción RecursoNoEncontradoException pero no ocurrió");
        
    
        verify(repository, times(1)).findById(2L);
    }
}