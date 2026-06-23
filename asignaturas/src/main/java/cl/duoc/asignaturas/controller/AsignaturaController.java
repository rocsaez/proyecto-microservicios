package cl.duoc.asignaturas.controller;

import cl.duoc.asignaturas.dto.AsignaturaDTO;
import cl.duoc.asignaturas.dto.AsignaturaCreateDTO;
import cl.duoc.asignaturas.service.AsignaturaService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
@Tag(name = "Asignaturas", description = "Operaciones CRUD y de control para la gestión de asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService service;

    @GetMapping
    @Operation(summary = "Listar todas las asignaturas", description = "Obtiene una lista de todas las asignaturas registradas")
    public ResponseEntity<List<AsignaturaDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar asignatura por ID", description = "Obtiene los detalles de una asignatura mediante su ID único")
    public ResponseEntity<AsignaturaDTO> getAsignatura(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/sigla/{sigla}")
    @Operation(summary = "Buscar asignatura por sigla", description = "Obtiene los detalles de una asignatura usando su sigla única")
    public ResponseEntity<AsignaturaDTO> obtenerPorSigla(@PathVariable String sigla) {
        return ResponseEntity.ok(service.obtenerPorSigla(sigla));
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva asignatura", description = "Crea una nueva asignatura en el sistema")
    public ResponseEntity<AsignaturaDTO> crearAsignatura(@Valid @RequestBody AsignaturaCreateDTO dto) {
        AsignaturaDTO creado = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar asignatura existente", description = "Modifica los datos de una asignatura existente por su ID")
    public ResponseEntity<AsignaturaDTO> editarAsignatura(@PathVariable Long id, @Valid @RequestBody AsignaturaCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

   @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una asignatura", description = "Elimina permanentemente una asignatura del sistema")
    public ResponseEntity<Void> borrarAsignatura(@PathVariable Long id) {
        service.eliminar(id); // El servicio lanza RecursoNoEncontradoException si no existe
        return ResponseEntity.noContent().build();
    }
}