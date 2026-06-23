package cl.duoc.evaluacion.controller;

import cl.duoc.evaluacion.dto.EvaluacionDTO;
import cl.duoc.evaluacion.exceptions.GlobalExceptionHandler;
import cl.duoc.evaluacion.exceptions.RecursoNoEncontradoException;
import cl.duoc.evaluacion.service.EvaluacionService;
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
class EvaluacionControllerTest {

    @Mock
    private EvaluacionService service;

    @InjectMocks
    private EvaluacionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ── GET /api/evaluaciones ─────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/evaluaciones - debe retornar 200 con la lista de calificaciones")
    void debeRetornar200CuandoSePidenEvaluaciones() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of(
            new EvaluacionDTO(1L, "19234567-8", "ASY4131", 6.8),
            new EvaluacionDTO(2L, "18456123-K", "ASY4131", 5.5)
        ));

        // When & Then
        mockMvc.perform(get("/api/evaluaciones"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].nombreEstudiante").value("19234567-8"))
               .andExpect(jsonPath("$[0].nota").value(6.8));
    }

    @Test
    @DisplayName("GET /api/evaluaciones - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/evaluaciones"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/evaluaciones/{id} ────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/evaluaciones/{id} - debe retornar 404 cuando la evaluación no existe")
    void debeRetornar404CuandoEvaluacionNoExiste() throws Exception {
        // Given
        when(service.obtenerPorId(999L))
            .thenThrow(new RecursoNoEncontradoException("Evaluación no encontrada con ID: 999"));

        // When & Then
        mockMvc.perform(get("/api/evaluaciones/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Evaluación no encontrada con ID: 999"));
    }

    // ── POST /api/evaluaciones ────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/evaluaciones - debe retornar 201 al registrar una evaluación válida")
    void debeRetornar201AlCrearEvaluacion() throws Exception {
        // Given
        String json = """
            {
                "nombreEstudiante": "19234567-8",
                "asignatura": "ASY4131",
                "nota": 7.0
            }
            """;
        when(service.guardar(any())).thenReturn(
            new EvaluacionDTO(1L, "19234567-8", "ASY4131", 7.0)
        );

        // When & Then
        mockMvc.perform(post("/api/evaluaciones")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.nota").value(7.0));
    }

    @Test
    @DisplayName("POST /api/evaluaciones - debe retornar 400 cuando los datos rompen las validaciones")
    void debeRetornar400CuandoDatosInvalidos() throws Exception {
        // Given — Nombre en blanco y nota fuera de rango (8.0 excede el @Max)
        String json = """
            {
                "nombreEstudiante": "",
                "asignatura": "ASY4131",
                "nota": 8.0
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/evaluaciones")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }
}