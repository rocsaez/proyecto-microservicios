package cl.duoc.asignaturas.controller;

import cl.duoc.asignaturas.dto.AsignaturaDTO;
import cl.duoc.asignaturas.dto.AsignaturaCreateDTO;
import cl.duoc.asignaturas.service.AsignaturaService;
import jakarta.validation.Valid; // IMPORTANTE para que funcionen las validaciones
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService service;

    // 1. Obtener todas (Devuelve lista de DTOs de salida)
    @GetMapping
    public ResponseEntity<List<AsignaturaDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    // 2. Obtener por ID (Devuelve DTO de salida)
    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaDTO> getAsignatura(@PathVariable Long id) {
        // Siguiendo el estándar del profesor: service.findDtoById
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
   @GetMapping("/sigla/{sigla}")
    public ResponseEntity<AsignaturaDTO> obtenerPorSigla(@PathVariable String sigla) {
        return ResponseEntity.ok(service.obtenerPorSigla(sigla));
    }
    // 3. Crear (Recibe CreateDTO y devuelve DTO de salida)
    @PostMapping
    public ResponseEntity<AsignaturaDTO> crearAsignatura(
            @Valid @RequestBody AsignaturaCreateDTO dto) {
        
        AsignaturaDTO creado = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // 4. Editar (Recibe CreateDTO para actualizar)
    @PutMapping("/{id}")
    public ResponseEntity<AsignaturaDTO> editarAsignatura(
            @PathVariable Long id, 
            @Valid @RequestBody AsignaturaCreateDTO dto) {
        
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    // 5. Borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarAsignatura(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}