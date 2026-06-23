package cl.duoc.sistema_asistencia.controller;

import cl.duoc.sistema_asistencia.dto.AsistenciaDTO;
import cl.duoc.sistema_asistencia.exceptions.GlobalExceptionHandler;
import cl.duoc.sistema_asistencia.exceptions.RecursoNoEncontradoException;
import cl.duoc.sistema_asistencia.service.AsistenciaService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AsistenciaControllerTest {

    @Mock
    private AsistenciaService service;

    @InjectMocks
    private AsistenciaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ── GET /api/asistencias ─────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/asistencias - debe retornar 200 con la lista de asistencias")
    void debeRetornar200CuandoSePidenAsistencias() throws Exception {
        // Given
        LocalDateTime ahora = LocalDateTime.now();
        when(service.findAll()).thenReturn(List.of(
            new AsistenciaDTO(1L, "12345678-9", "INF4141", ahora),
            new AsistenciaDTO(2L, "98765432-1", "MAT2020", ahora)
        ));

        // When & Then
        mockMvc.perform(get("/api/asistencias"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].rutEstudiante").value("12345678-9"))
               .andExpect(jsonPath("$[0].codigoClase").value("INF4141"));
    }

    @Test
    @DisplayName("GET /api/asistencias - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.findAll()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/asistencias"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/asistencias/{id} ────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/asistencias/{id} - debe retornar 404 cuando la asistencia no existe")
    void debeRetornar404CuandoAsistenciaNoExiste() throws Exception {
        // Given
        when(service.findById(999L))
            .thenThrow(new RecursoNoEncontradoException("Asistencia no encontrada: 999"));

        // When & Then
        mockMvc.perform(get("/api/asistencias/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Asistencia no encontrada: 999"));
    }

    // ── POST /api/asistencias ────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/asistencias - debe retornar 201 al crear una asistencia válida")
    void debeRetornar201AlCrearAsistencia() throws Exception {
        // Given
        String json = """
            {
                "rutEstudiante": "12345678-9",
                "codigoClase": "INF4141"
            }
            """;
        when(service.crear(any())).thenReturn(
            new AsistenciaDTO(7L, "12345678-9", "INF4141", LocalDateTime.now())
        );

        // When & Then
        mockMvc.perform(post("/api/asistencias")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(7))
               .andExpect(jsonPath("$.rutEstudiante").value("12345678-9"));
    }

    @Test
    @DisplayName("POST /api/asistencias - debe retornar 400 cuando el rut o código están en blanco")
    void debeRetornar400CuandoCamposEstanVacios() throws Exception {
        // Given — campos obligatorios vacíos
        String json = """
            {
                "rutEstudiante": "",
                "codigoClase": ""
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/asistencias")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }
}