package cl.duoc.carrera.controller;

import cl.duoc.carrera.dto.CarreraDTO;
import cl.duoc.carrera.dto.CarreraCreateDTO;
import cl.duoc.carrera.service.CarreraService;
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
@RequestMapping("/api/carreras")
@CrossOrigin(origins = "*")
@Tag(name = "Carreras", description = "Operaciones CRUD y de control para la gestión de carreras")
public class CarreraController {

    @Autowired
    private CarreraService service;

    @GetMapping
    @Operation(summary = "Listar todas las carreras", description = "Retorna la lista completa de todas las carreras de la universidad")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<CarreraDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar carrera por ID", description = "Obtiene los detalles de una carrera específica usando su ID único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Carrera encontrada con éxito"),
        @ApiResponse(responseCode = "404", description = "Carrera no encontrada en el sistema")
    })
    public ResponseEntity<CarreraDTO> buscar(
            @Parameter(description = "ID único de la carrera a buscar", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar nueva carrera", description = "Crea una nueva carrera en el sistema validando sus datos obligatorios")
    @ApiResponse(responseCode = "201", description = "Carrera creada exitosamente")
    public ResponseEntity<CarreraDTO> crear(
            @Valid @RequestBody CarreraCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar carrera existente", description = "Modifica los datos de una carrera existente buscando por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Carrera actualizada con éxito"),
        @ApiResponse(responseCode = "404", description = "Carrera no encontrada")
    })
    public ResponseEntity<CarreraDTO> editar(
            @Parameter(description = "ID de la carrera a modificar", required = true)
            @PathVariable Long id, 
            @Valid @RequestBody CarreraCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

   @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar carrera", description = "Elimina físicamente una carrera del sistema mediante su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Carrera eliminada exitosamente (No Content)"),
        @ApiResponse(responseCode = "404", description = "Carrera no encontrada")
    })
    public ResponseEntity<Void> borrar(
            @Parameter(description = "ID de la carrera a eliminar", required = true)
            @PathVariable Long id) {
        service.eliminar(id); // El servicio se encarga de validar o fallar
        return ResponseEntity.noContent().build(); // Retorna siempre 204 si todo sale bien
    }
}