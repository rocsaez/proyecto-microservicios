package cl.duoc.sistema_inscripcion.controller;

import cl.duoc.sistema_inscripcion.dto.InscripcionDTO;
import cl.duoc.sistema_inscripcion.dto.InscripcionCreateDTO;
import cl.duoc.sistema_inscripcion.exceptions.GlobalExceptionHandler;
import cl.duoc.sistema_inscripcion.exceptions.RecursoNoEncontradoException;
import cl.duoc.sistema_inscripcion.service.SistemaInscripcionesService;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SistemaInscripcionesControllerTest {

    @Mock
    private SistemaInscripcionesService service;

    @InjectMocks
    private SistemaInscripcionesController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ── GET /api/inscripciones ───────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/inscripciones - debe retornar 200 con la lista de inscripciones")
    void debeRetornar200CuandoSePidenInscripciones() throws Exception {
        // Given
        when(service.obtenerTodas()).thenReturn(List.of(
            new InscripcionDTO(1L, "12345678-9", "ASY4131", "2026-1"),
            new InscripcionDTO(2L, "9876543-2", "MDY3131", "2026-1")
        ));

        // When & Then
        mockMvc.perform(get("/api/inscripciones"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].rutEstudiante").value("12345678-9"))
               .andExpect(jsonPath("$[0].nombreAsignatura").value("ASY4131"));
    }

    @Test
    @DisplayName("GET /api/inscripciones - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.obtenerTodas()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/inscripciones"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/inscripciones/{id} ──────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/inscripciones/{id} - debe retornar 404 cuando la inscripción no existe")
    void debeRetornar404CuandoInscripcionNoExiste() throws Exception {
        // Given
        when(service.obtenerPorId(999L))
            .thenThrow(new RecursoNoEncontradoException("Inscripción no encontrada con ID: 999"));

        // When & Then
        mockMvc.perform(get("/api/inscripciones/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Inscripción no encontrada con ID: 999"));
    }

    // ── POST /api/inscripciones ──────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/inscripciones - debe retornar 201 al crear una inscripción válida")
    void debeRetornar201AlCrearInscripcion() throws Exception {
        // Given
        String json = """
            {
                "rutEstudiante": "12345678-9",
                "nombreAsignatura": "ASY4131",
                "periodo": "2026-1"
            }
            """;
        when(service.guardar(any())).thenReturn(
            new InscripcionDTO(1L, "12345678-9", "ASY4131", "2026-1")
        );

        // When & Then
        mockMvc.perform(post("/api/inscripciones")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.rutEstudiante").value("12345678-9"));
    }

    @Test
    @DisplayName("POST /api/inscripciones - debe retornar 400 cuando el formato del RUT es inválido")
    void debeRetornar400CuandoRutEsInvalido() throws Exception {
        // Given — RUT mal formateado (con puntos o largo incorrecto)
        String json = """
            {
                "rutEstudiante": "12.345.678-9",
                "nombreAsignatura": "ASY4131",
                "periodo": "2026-1"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/inscripciones")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }

    // ── DELETE /api/inscripciones/{id} ───────────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/inscripciones/{id} - debe retornar 404 si el servicio lanza excepción")
    void debeRetornar404AlEliminarInexistente() throws Exception {
        // Given
        doThrow(new RecursoNoEncontradoException("Inscripción no encontrada con ID: 999"))
            .when(service).eliminar(999L);

        // When & Then
        mockMvc.perform(delete("/api/inscripciones/999"))
               .andExpect(status().isNotFound());
    }
}