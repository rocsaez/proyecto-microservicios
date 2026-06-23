package cl.duoc.pagos.controller;

import cl.duoc.pagos.dto.PagoDTO;
import cl.duoc.pagos.exceptions.GlobalExceptionHandler;
import cl.duoc.pagos.exceptions.RecursoNoEncontradoException;
import cl.duoc.pagos.service.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PagoControllerTest {

    @Mock
    private PagoService service;

    @InjectMocks
    private PagoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler()) // Asegúrate de tener este handler en tu paquete exceptions
                .build();
    }

    @Test
    @DisplayName("GET /api/pagos - debe retornar 200 con la lista de pagos")
    void debeRetornar200CuandoSePidenPagos() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of(
            new PagoDTO(1L, 100L, 50000.0, LocalDate.now(), "PAGADO"),
            new PagoDTO(2L, 101L, 35000.0, LocalDate.now(), "PENDIENTE")
        ));

        // When & Then
        mockMvc.perform(get("/api/pagos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].monto").value(50000.0))
               .andExpect(jsonPath("$[1].estado").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/pagos/{id} - debe retornar 404 cuando el pago no existe")
    void debeRetornar404CuandoPagoNoExiste() throws Exception {
        // Given
        when(service.obtenerPorId(999L))
            .thenThrow(new RecursoNoEncontradoException("Pago no encontrado con ID: 999"));

        // When & Then
        mockMvc.perform(get("/api/pagos/999"))
               .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/pagos - debe retornar 201 al registrar un pago válido")
    void debeRetornar201AlCrearPago() throws Exception {
        // Given
        String json = """
            {
                "idEstudiante": 1,
                "monto": 60000.0,
                "fechaPago": "2026-06-21",
                "estado": "PAGADO"
            }
            """;
        when(service.guardar(any())).thenReturn(
            new PagoDTO(1L, 1L, 60000.0, LocalDate.of(2026, 6, 21), "PAGADO")
        );

        // When & Then
        mockMvc.perform(post("/api/pagos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.monto").value(60000.0));
    }

    @Test
    @DisplayName("POST /api/pagos - debe retornar 400 cuando el monto es menor o igual a cero")
    void debeRetornar400CuandoMontoEsInvalido() throws Exception {
        // Given - Monto inválido en 0
        String json = """
            {
                "idEstudiante": 1,
                "monto": 0.0,
                "fechaPago": "2026-06-21",
                "estado": "PAGADO"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/pagos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }
}