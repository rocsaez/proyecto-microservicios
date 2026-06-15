package cl.duoc.gestion_estudiante.controller;

import cl.duoc.gestion_estudiante.dto.EstudianteDTO;
import cl.duoc.gestion_estudiante.dto.EstudianteCreateDTO;
import cl.duoc.gestion_estudiante.service.GestionEstudianteService;

// Imports de Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Estudiantes", description = "Operaciones para la gestión, registro y búsqueda de alumnos")
@RestController
@RequestMapping("/api/estudiantes")
public class GestionEstudianteController {

    private final GestionEstudianteService service;

    public GestionEstudianteController(GestionEstudianteService service) {
        this.service = service;
    }

    // 1. CREAR
    @Operation(summary = "Registrar un nuevo estudiante", description = "Crea un registro de estudiante en la base de datos a partir de los datos entregados.")
    @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente")
    @PostMapping
    public ResponseEntity<EstudianteDTO> crear(@Valid @RequestBody EstudianteCreateDTO dto) {
        EstudianteDTO nuevo = service.guardarEstudiante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // 2. LEER TODOS
    @Operation(summary = "Listar todos los estudiantes", description = "Retorna una lista con la totalidad de los estudiantes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> listarTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // 3. LEER POR ID
    @Operation(summary = "Buscar estudiante por ID", description = "Retorna los datos de un estudiante específico usando su identificador numérico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún estudiante con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtenerPorId(
            @Parameter(description = "ID único del estudiante a consultar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // 4. ACTUALIZAR
    @Operation(summary = "Actualizar un estudiante existente", description = "Modifica los datos de un estudiante en base a su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "No se pudo actualizar porque el ID no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> actualizar(
            @Parameter(description = "ID del estudiante a modificar", required = true)
            @PathVariable Long id, 
            @Valid @RequestBody EstudianteCreateDTO dto) {
        return ResponseEntity.ok(service.actualizarEstudiante(id, dto));
    }

    // 5. ELIMINAR
    @Operation(summary = "Eliminar estudiante por ID", description = "Remueve permanentemente a un estudiante del sistema por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Estudiante eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID del estudiante no fue encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del estudiante a eliminar", required = true)
            @PathVariable Long id) {
        if (service.eliminarPorId(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // BÚSQUEDA FEIGN POR RUT
    @Operation(summary = "Buscar estudiante por RUT", description = "Endpoint de comunicación interna que permite a otros microservicios buscar un alumno por su RUT.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estudiante localizado"),
        @ApiResponse(responseCode = "404", description = "RUT no registrado en el sistema")
    })
    @GetMapping("/rut/{rut}")
    public ResponseEntity<EstudianteDTO> buscarPorRut(
            @Parameter(description = "RUT del estudiante (con guión y dígito verificador)", required = true, example = "12345678-9")
            @PathVariable String rut) {
        return ResponseEntity.ok(service.obtenerPorRut(rut));
    }
}