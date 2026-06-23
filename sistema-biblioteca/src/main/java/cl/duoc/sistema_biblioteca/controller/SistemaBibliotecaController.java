package cl.duoc.sistema_biblioteca.controller;

import cl.duoc.sistema_biblioteca.dto.BibliotecaDTO;
import cl.duoc.sistema_biblioteca.dto.BibliotecaCreateDTO;
import cl.duoc.sistema_biblioteca.service.SistemaBibliotecaService;
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

@Tag(name = "Biblioteca", description = "Operaciones CRUD del catálogo de libros")
@RestController
@RequestMapping("/api/biblioteca")
public class SistemaBibliotecaController {

    @Autowired
    private SistemaBibliotecaService service;

    @Operation(
        summary = "Listar todos los libros",
        description = "Retorna la lista completa de libros registrados en la biblioteca."
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<BibliotecaDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Buscar libro por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Libro encontrado"),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaDTO> buscarPorId(
            @Parameter(description = "ID único del libro", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Crear nuevo libro")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Libro guardado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<BibliotecaDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del nuevo libro")
            @Valid @RequestBody BibliotecaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @Operation(summary = "Actualizar libro existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Libro actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BibliotecaDTO> actualizar(
            @Parameter(description = "ID del libro a actualizar", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del libro")
            @Valid @RequestBody BibliotecaCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar un libro")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Libro eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del libro a eliminar", required = true)
            @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}