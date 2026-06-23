package cl.duoc.gestion_sala.controller;

import cl.duoc.gestion_sala.dto.SalaDTO;
import cl.duoc.gestion_sala.exceptions.GlobalExceptionHandler;
import cl.duoc.gestion_sala.exceptions.RecursoNoEncontradoException;
import cl.duoc.gestion_sala.service.SalaService;
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
class SalaControllerTest {

    @Mock
    private SalaService service;

    @InjectMocks
    private SalaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ── GET /api/salas ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/salas - debe retornar 200 con la lista de salas")
    void debeRetornar200CuandoSePidenSalas() throws Exception {
        // Given
        when(service.obtenerTodas()).thenReturn(List.of(
            new SalaDTO(1L, "Laboratorio 402", 30, "Laboratorio", "Piso 4, Edificio A"),
            new SalaDTO(2L, "Auditorio Central", 150, "Auditorio", "Piso 1, Edificio Central")
        ));

        // When & Then
        mockMvc.perform(get("/api/salas"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].nombreSala").value("Laboratorio 402"))
               .andExpect(jsonPath("$[0].capacidad").value(30));
    }

    @Test
    @DisplayName("GET /api/salas - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.obtenerTodas()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/salas"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/salas/{id} ────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/salas/{id} - debe retornar 404 cuando la sala no existe")
    void debeRetornar404CuandoSalaNoExiste() throws Exception {
        // Given
        when(service.obtenerPorId(999L))
            .thenThrow(new RecursoNoEncontradoException("Sala no encontrada con ID: 999"));

        // When & Then
        mockMvc.perform(get("/api/salas/999"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Sala no encontrada con ID: 999"));
    }

    // ── POST /api/salas ────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/salas - debe retornar 201 al crear una sala válida")
    void debeRetornar201AlCrearSala() throws Exception {
        // Given
        String json = """
            {
                "nombreSala": "Aula Magna",
                "capacidad": 200,
                "tipo": "Auditorio",
                "ubicacion": "Piso 1, Edificio Central"
            }
            """;
        when(service.guardar(any())).thenReturn(
            new SalaDTO(7L, "Aula Magna", 200, "Auditorio", "Piso 1, Edificio Central")
        );

        // When & Then
        mockMvc.perform(post("/api/salas")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(7))
               .andExpect(jsonPath("$.nombreSala").value("Aula Magna"));
    }

    @Test
    @DisplayName("POST /api/salas - debe retornar 400 cuando el nombre está en blanco")
    void debeRetornar400CuandoNombreEstaVacio() throws Exception {
        // Given — nombre vacío y capacidad fuera de límites mínimos
        String json = """
            {
                "nombreSala": "",
                "capacidad": -5,
                "tipo": "Aula",
                "ubicacion": "Piso 2"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/salas")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isBadRequest());
    }
}