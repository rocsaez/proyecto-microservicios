package cl.duoc.evaluacion.controller;

import cl.duoc.evaluacion.dto.EvaluacionDTO;
import cl.duoc.evaluacion.dto.EvaluacionCreateDTO;
import cl.duoc.evaluacion.service.EvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Evaluaciones", description = "Operaciones CRUD para la gestión de calificaciones académicas")
@RestController
@RequestMapping("/api/evaluaciones")
@CrossOrigin(origins = "*")
public class EvaluacionController {

    @Autowired
    private EvaluacionService service;

    @Operation(summary = "Listar todas las evaluaciones", description = "Retorna el historial completo de calificaciones registradas en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<EvaluacionDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Buscar evaluación por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evaluación encontrada con éxito"),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada en los registros")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionDTO> buscarPorId(
            @Parameter(description = "ID único de la evaluación", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Crear nueva evaluación")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Evaluación ingresada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o fallas en las restricciones de nota")
    })
    @PostMapping
    public ResponseEntity<EvaluacionDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la nueva calificación")
            @Valid @RequestBody EvaluacionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @Operation(summary = "Actualizar evaluación existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evaluación modificada exitosamente"),
        @ApiResponse(responseCode = "444", description = "ID de evaluación no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de la modificación inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionDTO> editar(
            @Parameter(description = "ID de la evaluación a actualizar", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos de la calificación")
            @Valid @RequestBody EvaluacionCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

   @Operation(summary = "Eliminar evaluación")
@ApiResponses({
    @ApiResponse(responseCode = "204", description = "Evaluación eliminada con éxito"),
    @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
})
@DeleteMapping("/{id}")
public ResponseEntity<Void> borrar(
    @Parameter(description = "ID de la evaluación") @PathVariable Long id) {
    
    // El servicio lanza RecursoNoEncontradoException si no existe,
    // el GlobalExceptionHandler lo atrapa y manda el 404 de forma automática.
    service.eliminar(id); 
    
    return ResponseEntity.noContent().build(); // Retorna 204 No Content
}
}