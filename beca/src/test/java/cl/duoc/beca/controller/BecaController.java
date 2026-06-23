package cl.duoc.beca.controller;

import cl.duoc.beca.dto.BecaDTO;
import cl.duoc.beca.exceptions.GlobalExceptionHandler;
import cl.duoc.beca.exceptions.RecursoNoEncontradoException;
import cl.duoc.beca.service.BecaService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BecaControllerTest {

    @Mock
    private BecaService service;

    @InjectMocks
    private BecaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ── GET /api/becas ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/becas - debe retornar 200 con la lista de becas")
    void debeRetornar200CuandoSePidenBecas() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of(
            new BecaDTO(1L, "Beca Excelencia", "12345678-9", 50.0, "Activo"),
            new BecaDTO(2L, "Beca Deportiva", "11111111-1", 25.5, "Activo")
        ));

        // When & Then
        mockMvc.perform(get("/api/becas"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].nombreBeca").value("Beca Excelencia"))
               .andExpect(jsonPath("$[0].porcentajeDescuento").value(50.0));
    }

    @Test
    @DisplayName("GET /api/becas - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/becas"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/becas/{id} ────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/becas/{id} - debe retornar 404 cuando la beca no existe")
    void debeRetornar404CuandoBecaNoExiste() throws Exception {
        // Given
        when(service.obtenerPorId(999L))
            .thenThrow(new RecursoNoEncontradoException("Beca no encontrada con ID: 999"));

        // When & Then
        mockMvc.perform(get("/api/becas/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Beca no encontrada con ID: 999"));
    }

    // ── POST /api/becas ────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/becas - debe retornar 201 al asignar una beca válida")
    void debeRetornar201AlCrearBeca() throws Exception {
        // Given
        String json = """
            {
                "nombreBeca": "Beca Vulnerabilidad",
                "rutEstudiante": "12345678-9",
                "porcentajeDescuento": 85.0,
                "estado": "Activo"
            }
            """;
        when(service.guardar(any())).thenReturn(
            new BecaDTO(5L, "Beca Vulnerabilidad", "12345678-9", 85.0, "Activo")
        );

        // When & Then
        mockMvc.perform(post("/api/becas")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(5))
               .andExpect(jsonPath("$.nombreBeca").value("Beca Vulnerabilidad"));
    }

    @Test
    @DisplayName("POST /api/becas - debe retornar 400 cuando los datos del DTO son inválidos")
    void debeRetornar400CuandoCamposEstanVacios() throws Exception {
        // Given — RUT con formato malo y porcentaje inválido
        String json = """
            {
                "nombreBeca": "",
                "rutEstudiante": "RUT_MALO",
                "porcentajeDescuento": 150.0,
                "estado": ""
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/becas")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }
}