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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    // 🛠️ Un mini-advice local para indicarle a MockMvc Standalone cómo traducir la excepción a un 404 HTTP
    @RestControllerAdvice
    static class TestExceptionHandler {
        @ExceptionHandler(RecursoNoEncontradoException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public String handleNotFound(RecursoNoEncontradoException ex) {
            return ex.getMessage();
        }
    }

    @BeforeEach
    void setUp() {
        // Configuramos MockMvc registrando el manejador de excepciones
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new TestExceptionHandler())
                .build();
    }

    // ── GET /api/profesores ───────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/profesores - debe retornar 200 con la lista de profesores")
    void debeRetornar200CuandoSePidenProfesores() throws Exception {
        when(service.obtenerTodos()).thenReturn(List.of(
            new ProfesorDTO(1L, "Erick González", "Arquitectura de Software", "erick.gonzalez@duocuc.cl"),
            new ProfesorDTO(2L, "Carlos Soto", "Calidad de Software", "carlos.soto@duocuc.cl")
        ));

        mockMvc.perform(get("/api/profesores"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].nombre").value("Erick González"))
               .andExpect(jsonPath("$[0].asignatura").value("Arquitectura de Software"));
    }

    @Test
    @DisplayName("GET /api/profesores - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        when(service.obtenerTodos()).thenReturn(List.of());

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

        // When & Then - Ahora MockMvc procesará la excepción devolviendo el 404 esperado de forma nativa
        mockMvc.perform(get("/api/profesores/999"))
               .andExpect(status().isNotFound());
    }

    // ── POST /api/profesores ──────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/profesores - debe retornar 201 al crear un profesor válido")
    void debeRetornar201AlCrearProfesor() throws Exception {
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

        mockMvc.perform(post("/api/profesores")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.nombre").value("Erick González"));
    }
}