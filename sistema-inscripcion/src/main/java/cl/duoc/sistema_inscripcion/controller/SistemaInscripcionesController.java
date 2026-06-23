package cl.duoc.sistema_inscripcion.controller;

import cl.duoc.sistema_inscripcion.dto.InscripcionDTO;
import cl.duoc.sistema_inscripcion.dto.InscripcionCreateDTO;
import cl.duoc.sistema_inscripcion.service.SistemaInscripcionesService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@Tag(name = "Inscripciones", description = "Operaciones CRUD para el control de inscripciones de asignaturas")
public class SistemaInscripcionesController {

    @Autowired
    private SistemaInscripcionesService service;

    @GetMapping
    @Operation(summary = "Listar todas las inscripciones", description = "Retorna el historial completo de inscripciones del sistema")
    @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    public ResponseEntity<List<InscripcionDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar inscripción por ID", description = "Obtiene los detalles de una inscripción mediante su ID único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscripción encontrada de manera exitosa"),
        @ApiResponse(responseCode = "404", description = "El ID de la inscripción no existe en el sistema")
    })
    public ResponseEntity<InscripcionDTO> buscarPorId(
            @Parameter(description = "ID único de la inscripción a consultar", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar nueva inscripción", description = "Crea una inscripción de asignatura validando el RUT y cruzando datos con otros microservicios")
    @ApiResponse(responseCode = "201", description = "Inscripción procesada y guardada correctamente")
    public ResponseEntity<InscripcionDTO> crear(
            @Valid @RequestBody InscripcionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inscripción existente", description = "Modifica los datos de un registro de inscripción basándose en su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscripción actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Inscripción no encontrada para actualizar")
    })
    public ResponseEntity<InscripcionDTO> actualizar(
            @Parameter(description = "ID del registro a modificar", required = true)
            @PathVariable Long id, 
            @Valid @RequestBody InscripcionCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inscripción", description = "Elimina físicamente una inscripción del sistema usando su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Registro eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID de la inscripción no fue encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del registro a eliminar", required = true)
            @PathVariable Long id) {
        
        service.eliminar(id); // Si no existe, lanza la excepción atrapada por el Handler
        return ResponseEntity.noContent().build(); // Retorna 204 siempre que sea exitoso
    }
}