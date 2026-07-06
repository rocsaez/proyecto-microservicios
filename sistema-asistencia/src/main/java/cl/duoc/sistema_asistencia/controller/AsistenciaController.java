package cl.duoc.sistema_asistencia.controller;

import cl.duoc.sistema_asistencia.dto.AsistenciaCreateDTO;
import cl.duoc.sistema_asistencia.dto.AsistenciaDTO;
import cl.duoc.sistema_asistencia.service.AsistenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Asistencias", description = "Operaciones CRUD del registro de asistencias")
@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class AsistenciaController {

    @Autowired
    private AsistenciaService service;

    @Operation(
        summary = "Listar todas las asistencias",
        description = "Retorna la lista completa de asistencias registradas en el sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<AsistenciaDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar asistencia por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Asistencia encontrada"),
        @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AsistenciaDTO> getById(
            @Parameter(description = "ID único de la asistencia", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear nueva asistencia")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Asistencia creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<AsistenciaDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos de la nueva asistencia"
            )
            @Valid @RequestBody AsistenciaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Actualizar asistencia existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Asistencia actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Asistencia no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AsistenciaDTO> actualizar(
            @Parameter(description = "ID de la asistencia a actualizar", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos de la asistencia"
            )
            @Valid @RequestBody AsistenciaCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar asistencia")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Asistencia eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la asistencia a eliminar", required = true)
            @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}