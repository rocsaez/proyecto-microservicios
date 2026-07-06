package cl.duoc.beca.controller;

import cl.duoc.beca.dto.BecaDTO;
import cl.duoc.beca.dto.BecaCreateDTO;
import cl.duoc.beca.service.BecaService;
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

@Tag(name = "Becas", description = "Operaciones CRUD para la gestión de becas de estudiantes")
@RestController
@RequestMapping("/api/becas")
@CrossOrigin(origins = "*")
public class BecaController {

    @Autowired
    private BecaService service;

    // ── GET /api/becas ─────────────────────────────────────────────────────────
    @Operation(summary = "Listar todas las becas", description = "Retorna la lista completa de becas otorgadas.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<BecaDTO>> listar() { 
        return ResponseEntity.ok(service.obtenerTodos()); 
    }

    // ── GET /api/becas/{id} ────────────────────────────────────────────────────
    @Operation(summary = "Buscar beca por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Beca encontrada"),
        @ApiResponse(responseCode = "404", description = "Beca no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BecaDTO> porId(
            @Parameter(description = "ID único de la beca", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // ── POST /api/becas ────────────────────────────────────────────────────────
    @Operation(summary = "Crear/Asignar una nueva beca")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Beca creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<BecaDTO> crear(@Valid @RequestBody BecaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    // ── PUT /api/becas/{id} ─────────────────────────────────────────────────────
    @Operation(summary = "Actualizar beca existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Beca actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Beca no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BecaDTO> editar(
            @Parameter(description = "ID de la beca a actualizar", required = true)
            @PathVariable Long id,
            @Valid @RequestBody BecaCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    // ── DELETE /api/becas/{id} ──────────────────────────────────────────────────
    @Operation(summary = "Eliminar una beca")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Beca eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Beca no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(
            @Parameter(description = "ID de la beca a eliminar", required = true)
            @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}