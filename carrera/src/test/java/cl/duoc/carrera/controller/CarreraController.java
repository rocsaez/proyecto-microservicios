package cl.duoc.carrera.controller;

import cl.duoc.carrera.dto.CarreraDTO;
import cl.duoc.carrera.exceptions.GlobalExceptionHandler;
import cl.duoc.carrera.exceptions.RecursoNoEncontradoException;
import cl.duoc.carrera.service.CarreraService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow; // <-- Importación clave solucionada
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CarreraControllerTest {

    @Mock
    private CarreraService service;

    @InjectMocks
    private CarreraController controller;

    private MockMvc mockMvc;

   @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler()) // <-- Esto vincula tu manejador global a MockMvc
                .build();
    }

    // ── GET /api/carreras ──────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/carreras - debe retornar 200 con la lista de carreras")
    void debeRetornar200CuandoSePidenCarreras() throws Exception {
        // Given
        when(service.obtenerTodas()).thenReturn(List.of(
            new CarreraDTO(1L, "Ingeniería en Informática", "Ingeniería", 8),
            new CarreraDTO(2L, "Diseño Gráfico", "Diseño", 8)
        ));

        // When & Then
        mockMvc.perform(get("/api/carreras"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].nombre").value("Ingeniería en Informática"))
               .andExpect(jsonPath("$[1].nombre").value("Diseño Gráfico"));
    }

    @Test
    @DisplayName("GET /api/carreras - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.obtenerTodas()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/carreras"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/carreras/{id} ─────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/carreras/{id} - debe retornar 200 cuando la carrera existe")
    void debeRetornar200CuandoCarreraExiste() throws Exception {
        // Given
        when(service.obtenerPorId(1L)).thenReturn(
            new CarreraDTO(1L, "Ingeniería en Informática", "Ingeniería", 8)
        );

        // When & Then
        mockMvc.perform(get("/api/carreras/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.nombre").value("Ingeniería en Informática"));
    }

    // ── POST /api/carreras ─────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/carreras - debe retornar 201 al crear una carrera válida")
    void debeRetornar201AlCrearCarrera() throws Exception {
        // Given
        String json = """
            {
                "nombre": "Enfermería",
                "facultad": "Salud",
                "semestres": 10
            }
            """;
        when(service.guardar(any())).thenReturn(
            new CarreraDTO(3L, "Enfermería", "Salud", 10)
        );

        // When & Then
        mockMvc.perform(post("/api/carreras")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(3))
               .andExpect(jsonPath("$.nombre").value("Enfermería"));
    }

    @Test
    @DisplayName("POST /api/carreras - debe retornar 400 cuando los datos rompen las validaciones")
    void debeRetornar400CuandoNombreEstaVacio() throws Exception {
        // Given — pasamos el nombre vacío y más de 15 semestres (rompe @NotBlank y @Max)
        String json = """
            {
                "nombre": "",
                "facultad": "Ingeniería",
                "semestres": 20
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/carreras")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }

    // ── PUT /api/carreras/{id} ─────────────────────────────────────────────────

    @Test
    @DisplayName("PUT /api/carreras/{id} - debe retornar 200 al editar con éxito")
    void debeRetornar200AlEditar() throws Exception {
        // Given
        String json = """
            {
                "nombre": "Civil Informática",
                "facultad": "Ingeniería",
                "semestres": 10
            }
            """;
        when(service.actualizar(eq(1L), any())).thenReturn(
            new CarreraDTO(1L, "Civil Informática", "Ingeniería", 10)
        );

        // When & Then
        mockMvc.perform(put("/api/carreras/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value("Civil Informática"));
    }

    // ── DELETE /api/carreras/{id} ──────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/carreras/{id} - debe retornar 204 cuando se borra con éxito")
    void debeRetornar204AlBorrar() throws Exception {
        // Given - Como el servicio es void, no requiere configuración when() para éxito
        
        // When & Then
        mockMvc.perform(delete("/api/carreras/1"))
               .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/carreras/{id} - debe retornar 404 si el recurso no existe")
    void debeRetornar404AlBorrarInexistente() throws Exception {
        // Given - Forzamos el lanzamiento de la excepción para simular que no existe
        doThrow(new RecursoNoEncontradoException("Carrera no encontrada"))
            .when(service).eliminar(999L);

        // When & Then
        mockMvc.perform(delete("/api/carreras/999"))
               .andExpect(status().isNotFound());
    }
}