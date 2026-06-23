package cl.duoc.asignaturas.controller;

import cl.duoc.asignaturas.dto.AsignaturaDTO;
import cl.duoc.asignaturas.exceptions.GlobalExceptionHandler;
import cl.duoc.asignaturas.exceptions.RecursoNoEncontradoException;
import cl.duoc.asignaturas.service.AsignaturaService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class AsignaturaControllerTest {

    @Mock
    private AsignaturaService service;

    @InjectMocks
    private AsignaturaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
       
        org.springframework.validation.beanvalidation.LocalValidatorFactoryBean validator = 
                new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator) //Esto activa los @Valid del DTOs en el test
                .build();
    }

    @Test
    @DisplayName("GET /api/asignaturas - debe retornar status 200 con la lista")
    void debeRetornar200ConListaDeAsignaturas() throws Exception {
        // Given
       when(service.obtenerTodas()).thenReturn(List.of(
    new AsignaturaDTO(1L, "Cálculo", "MAT101", 6, "INF-01", 10L) ,
    new AsignaturaDTO(2L, "Física", "FIS202", 5, "INF-01", 11L)
));

        // When & Then
        mockMvc.perform(get("/api/asignaturas"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].sigla").value("MAT101"))
               .andExpect(jsonPath("$[1].nombre").value("Física"));
    }

    @Test
    @DisplayName("GET /api/asignaturas/sigla/{sigla} - debe retornar 404 si no existe")
    void debeRetornar404CuandoSiglaNoExiste() throws Exception {
        // Given
        when(service.obtenerPorSigla("ERR404"))
                .thenThrow(new RecursoNoEncontradoException("Asignatura con sigla ERR404 no encontrada"));

        // When & Then
        mockMvc.perform(get("/api/asignaturas/sigla/ERR404"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.error").value("Asignatura con sigla ERR404 no encontrada"));
    }

    @Test
    @DisplayName("POST /api/asignaturas - debe retornar 201 al crear una asignatura válida")
    void debeRetornar201AlCrearAsignaturaValida() throws Exception {
        // Given
        String json = """
            {
                "nombre": "Estructuras de Datos",
                "sigla": "EDY2201",
                "creditos": 6,
                "codigoCarrera": "ING-INF",
                "idProfesor": 12
            }
            """;
        when(service.guardar(any())).thenReturn(new AsignaturaDTO(3L, "Estructuras de Datos", "EDY2201", 6, "ING-INF", 12L));

        // When & Then
        mockMvc.perform(post("/api/asignaturas")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(3))
               .andExpect(jsonPath("$.sigla").value("EDY2201"));
    }

    @Test
    @DisplayName("POST /api/asignaturas - debe retornar 400 cuando las validaciones fallan")
    void debeRetornar400CuandoCamposEstanVacios() throws Exception {
        // Given (JSON inválido: sigla vacía, créditos fuera de rango)
        String jsonBodyInvalido = """
            {
                "nombre": "A",
                "sigla": "",
                "creditos": 99,
                "codigoCarrera": "",
                "idProfesor": null
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/asignaturas")
               .contentType(MediaType.APPLICATION_JSON)
               .content(jsonBodyInvalido))
               .andExpect(status().isBadRequest());
    }
}