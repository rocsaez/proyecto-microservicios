package cl.duoc.gestion_sala.controller;

import cl.duoc.gestion_sala.dto.SalaDTO;
import cl.duoc.gestion_sala.dto.SalaCreateDTO;
import cl.duoc.gestion_sala.service.SalaService;
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

@Tag(name = "Salas", description = "Operaciones CRUD para la gestión de salas")
@RestController
@RequestMapping("/api/salas")
@CrossOrigin(origins = "*")
public class SalaController {

    @Autowired
    private SalaService service;

    @Operation(summary = "Listar todas las salas", description = "Retorna la lista completa de salas registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<SalaDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @Operation(summary = "Buscar sala por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sala encontrada"),
        @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> buscarPorId(
            @Parameter(description = "ID único de la sala", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Crear nueva sala")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Sala creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<SalaDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la nueva sala")
            @Valid @RequestBody SalaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @Operation(summary = "Actualizar sala existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sala actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sala no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> actualizar(
            @Parameter(description = "ID de la sala a actualizar", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos de la sala")
            @Valid @RequestBody SalaCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar sala")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Sala eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la sala a eliminar", required = true)
            @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}