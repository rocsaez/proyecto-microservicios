package cl.duoc.pagos.controller;

import cl.duoc.pagos.dto.PagoDTO;
import cl.duoc.pagos.dto.PagoCreateDTO;
import cl.duoc.pagos.service.PagoService;
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

@Tag(name = "Pagos", description = "Operaciones de procesamiento y gestión de pagos estudiantiles")
@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService service;

    @Operation(summary = "Listar todos los pagos", description = "Retorna el historial completo de pagos.")
    @ApiResponse(responseCode = "200", description = "Listado de transacciones obtenido")
    @GetMapping
    public ResponseEntity<List<PagoDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Buscar pago por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago localizado"),
        @ApiResponse(responseCode = "404", description = "El ID del pago no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> buscar(
            @Parameter(description = "ID único del pago", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Registrar una nueva transacción de pago")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pago procesado y guardado"),
        @ApiResponse(responseCode = "400", description = "Entrada inválida o estudiante inexistente")
    })
    @PostMapping
    public ResponseEntity<PagoDTO> crear(@Valid @RequestBody PagoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @Operation(summary = "Actualizar pago existente")
    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> editar(
            @Parameter(description = "ID del pago a modificar", required = true)
            @PathVariable Long id, 
            @Valid @RequestBody PagoCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar un registro de pago")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}