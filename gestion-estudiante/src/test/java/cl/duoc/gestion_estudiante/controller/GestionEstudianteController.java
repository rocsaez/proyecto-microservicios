package cl.duoc.gestion_estudiante.controller;

import cl.duoc.gestion_estudiante.dto.EstudianteDTO;
import cl.duoc.gestion_estudiante.dto.EstudianteCreateDTO;
import cl.duoc.gestion_estudiante.service.GestionEstudianteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GestionEstudianteController.class)
class GestionEstudianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GestionEstudianteService service;

    @Autowired
    private ObjectMapper objectMapper;

    private EstudianteDTO estudianteDTO;
    private EstudianteCreateDTO estudianteCreateDTO;

    @BeforeEach
    void setUp() {
        estudianteDTO = new EstudianteDTO(1L, "Juan Perez", "12345678-9", "juan.perez@duocuc.cl");
        estudianteCreateDTO = new EstudianteCreateDTO("Juan Perez", "12345678-9", "juan.perez@duocuc.cl");
    }

    @Test
    @DisplayName("GET /api/estudiantes - debe retornar 200 con la lista de estudiantes")
    void debeRetornar200CuandoSePidenEstudiantes() throws Exception {
        Mockito.when(service.obtenerTodos()).thenReturn(Arrays.asList(estudianteDTO));

        mockMvc.perform(get("/api/estudiantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"))
                .andExpect(jsonPath("$[0].rut").value("12345678-9"));
    }

    @Test
    @DisplayName("POST /api/estudiantes - debe retornar 201 al crear un estudiante válido")
    void debeRetornar201AlCrearEstudiante() throws Exception {
        Mockito.when(service.guardarEstudiante(any(EstudianteCreateDTO.class))).thenReturn(estudianteDTO);

        mockMvc.perform(post("/api/estudiantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(estudianteCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }
    @Test
@DisplayName("Debería retornar 400 Bad Request cuando el DTO enviado es inválido")
void debeRetornarBadRequestCuandoDtoEsInvalido() throws Exception {
    // GIVEN: Un DTO con datos inválidos (nombre vacío y correo sin formato correcto)
    EstudianteCreateDTO dtoInvalido = new EstudianteCreateDTO("", "12345678-9", "correo-incorrecto");

    // WHEN / THEN: Se realiza la petición POST y se espera un estado 400 Bad Request
    mockMvc.perform(post("/api/estudiantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dtoInvalido)))
            .andExpect(status().isBadRequest());
}
    
}