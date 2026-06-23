package cl.duoc.gestion_profesor.controller;

import cl.duoc.gestion_profesor.dto.ProfesorDTO;
import cl.duoc.gestion_profesor.dto.ProfesorCreateDTO;
import cl.duoc.gestion_profesor.service.GestionProfesorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Imports de Swagger OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Profesores", description = "Operaciones CRUD y de control para la gestión de profesores")
@RestController
@RequestMapping("/api/profesores")
public class GestionProfesorController {

    @Autowired
    private GestionProfesorService service;

    @Operation(summary = "Listar todos los profesores", description = "Retorna una lista completa con todos los docentes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ProfesorDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Registrar un nuevo profesor")
    @ApiResponse(responseCode = "201", description = "Profesor creado de manera exitosa")
    @PostMapping
    public ResponseEntity<ProfesorDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos requeridos para el registro del profesor")
            @Valid @RequestBody ProfesorCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @Operation(summary = "Buscar profesor por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profesor encontrado"),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún profesor con el ID indicado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> porId(
            @Parameter(description = "ID único del profesor", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar profesor existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profesor actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "No se puede actualizar; el ID no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProfesorDTO> editar(
            @Parameter(description = "ID del profesor a modificar", required = true, example = "1")
            @PathVariable Long id, 
            @Valid @RequestBody ProfesorCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar un profesor")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Profesor eliminado (No Content)"),
        @ApiResponse(responseCode = "404", description = "El profesor no existe en los registros")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(
            @Parameter(description = "ID del profesor a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        if (service.eliminar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}