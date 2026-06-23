package cl.duoc.pagos.service;

import cl.duoc.pagos.client.BecaClient;
import cl.duoc.pagos.client.EstudianteClient;
import cl.duoc.pagos.dto.BecaDTO;
import cl.duoc.pagos.dto.EstudianteDTO;
import cl.duoc.pagos.dto.PagoCreateDTO;
import cl.duoc.pagos.dto.PagoDTO;
import cl.duoc.pagos.exceptions.RecursoNoEncontradoException;
import cl.duoc.pagos.model.PagoModel;
import cl.duoc.pagos.repository.PagoRepository;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private EstudianteClient estudianteClient;

    @Mock
    private BecaClient becaClient;

    @InjectMocks
    private PagoService service;

    @Test
    @DisplayName("guardar - debe aplicar descuento de Beca ACTIVA correctamente")
    void debeAplicarDescuentoDeBecaActiva() {
        // Given
        PagoCreateDTO dto = new PagoCreateDTO(1L, 100000.0, LocalDate.now(), "PAGADO");
        EstudianteDTO estudianteMock = new EstudianteDTO(1L, "Juan Perez", "12345678-9", "juan@duoc.cl");
        BecaDTO becaMock = new BecaDTO(5L, "Beca Excelencia", "12345678-9", 20.0, "ACTIVA"); // 20% descuento
        
        PagoModel modeloGuardado = new PagoModel();
        modeloGuardado.setId(100L);
        modeloGuardado.setIdEstudiante(1L);
        modeloGuardado.setMonto(80000.0); // 100000 - 20% = 80000
        modeloGuardado.setEstado("PAGADO");

        when(estudianteClient.obtenerPorId(1L)).thenReturn(estudianteMock);
        when(becaClient.obtenerBecaPorRut("12345678-9")).thenReturn(becaMock);
        when(repository.save(any(PagoModel.class))).thenReturn(modeloGuardado);

        // When
        PagoDTO resultado = service.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(80000.0, resultado.getMonto()); // Valida cálculo aritmético del descuento
        verify(repository, times(1)).save(any(PagoModel.class));
    }

    @Test
    @DisplayName("guardar - debe lanzar excepcion si Feign reporta que el estudiante no existe")
    void debeLanzarExcepcionSiEstudianteNoExiste() {
        // Given
        PagoCreateDTO dto = new PagoCreateDTO(99L, 50000.0, LocalDate.now(), "PENDIENTE");
        when(estudianteClient.obtenerPorId(99L)).thenThrow(FeignException.NotFound.class);

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () -> service.guardar(dto));
        verify(repository, never()).save(any());
    }
}