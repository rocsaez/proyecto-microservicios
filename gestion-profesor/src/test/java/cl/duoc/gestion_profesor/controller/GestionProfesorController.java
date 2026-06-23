package cl.duoc.gestion_profesor.controller;

import cl.duoc.gestion_profesor.dto.ProfesorDTO;
import cl.duoc.gestion_profesor.exceptions.RecursoNoEncontradoException;
import cl.duoc.gestion_profesor.service.GestionProfesorService;
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
class GestionProfesorControllerTest {

    @Mock
    private GestionProfesorService service;

    @InjectMocks
    private GestionProfesorController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    // ── GET /api/profesores ───────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/profesores - debe retornar 200 con la lista de profesores")
    void debeRetornar200CuandoSePidenProfesores() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of(
            new ProfesorDTO(1L, "Erick González", "Arquitectura de Software", "erick.gonzalez@duocuc.cl"),
            new ProfesorDTO(2L, "Carlos Soto", "Calidad de Software", "carlos.soto@duocuc.cl")
        ));

        // When & Then
        mockMvc.perform(get("/api/profesores"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].nombre").value("Erick González"))
               .andExpect(jsonPath("$[0].asignatura").value("Arquitectura de Software"));
    }

    @Test
    @DisplayName("GET /api/profesores - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/profesores"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/profesores/{id} ──────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/profesores/{id} - debe retornar 404 cuando el profesor no existe")
    void debeRetornar404CuandoProfesorNoExiste() throws Exception {
        // Given
        when(service.obtenerPorId(999L))
            .thenThrow(new RecursoNoEncontradoException("Profesor no encontrado con ID: 999"));

        // When & Then
        mockMvc.perform(get("/api/profesores/999"))
               .andExpect(status().isNotFound());
    }

    // ── POST /api/profesores ──────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/profesores - debe retornar 201 al crear un profesor válido")
    void debeRetornar201AlCrearProfesor() throws Exception {
        // Given
        String json = """
            {
                "nombre": "Erick González",
                "asignatura": "Arquitectura de Software",
                "correo": "erick.gonzalez@duocuc.cl"
            }
            """;
        when(service.guardar(any())).thenReturn(
            new ProfesorDTO(1L, "Erick González", "Arquitectura de Software", "erick.gonzalez@duocuc.cl")
        );

        // When & Then
        mockMvc.perform(post("/api/profesores")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.nombre").value("Erick González"));
    }
}