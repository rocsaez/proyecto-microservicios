package cl.duoc.sistema_biblioteca.controller;

import cl.duoc.sistema_biblioteca.dto.BibliotecaDTO;
import cl.duoc.sistema_biblioteca.exceptions.GlobalExceptionHandler;
import cl.duoc.sistema_biblioteca.exceptions.RecursoNoEncontradoException;
import cl.duoc.sistema_biblioteca.service.SistemaBibliotecaService;
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
class SistemaBibliotecaControllerTest {

    @Mock
    private SistemaBibliotecaService service;

    @InjectMocks
    private SistemaBibliotecaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ── GET /api/biblioteca ─────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/biblioteca - debe retornar 200 con la lista de libros")
    void debeRetornar200CuandoSePidenLibros() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of(
            new BibliotecaDTO(1L, "El Psicoanalista", "John Katzenbach", "9786073133319"),
            new BibliotecaDTO(2L, "Ficciones", "Jorge Luis Borges", "9789875666481")
        ));

        // When & Then
        mockMvc.perform(get("/api/biblioteca"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].titulo").value("El Psicoanalista"))
               .andExpect(jsonPath("$[0].isbn").value("9786073133319"));
    }

    @Test
    @DisplayName("GET /api/biblioteca - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.obtenerTodos()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/biblioteca"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/biblioteca/{id} ────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/biblioteca/{id} - debe retornar 404 cuando el libro no existe")
    void debeRetornar404CuandoLibroNoExiste() throws Exception {
        // Given
        when(service.obtenerPorId(999L))
            .thenThrow(new RecursoNoEncontradoException("Libro no encontrado con ID: 999"));

        // When & Then
        mockMvc.perform(get("/api/biblioteca/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Libro no encontrado con ID: 999"));
    }

    // ── POST /api/biblioteca ────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/biblioteca - debe retornar 201 al crear un libro válido")
    void debeRetornar201AlCrearLibro() throws Exception {
        // Given
        String json = """
            {
                "titulo": "El Aleph",
                "autor": "Jorge Luis Borges",
                "isbn": "9789500403344"
            }
            """;
        when(service.guardar(any())).thenReturn(
            new BibliotecaDTO(5L, "El Aleph", "Jorge Luis Borges", "9789500403344")
        );

        // When & Then
        mockMvc.perform(post("/api/biblioteca")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(5))
               .andExpect(jsonPath("$.titulo").value("El Aleph"));
    }

    @Test
    @DisplayName("POST /api/biblioteca - debe retornar 400 cuando el título está en blanco o ISBN es malo")
    void debeRetornar400CuandoDatosInvalidos() throws Exception {
        // Given — Título vacío e ISBN con formato erróneo
        String json = """
            {
                "titulo": "",
                "autor": "Autor Cualquiera",
                "isbn": "123-malo"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/biblioteca")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }
}