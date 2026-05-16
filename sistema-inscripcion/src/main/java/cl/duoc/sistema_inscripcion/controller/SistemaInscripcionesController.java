package cl.duoc.sistema_inscripcion.controller;

import cl.duoc.sistema_inscripcion.model.SistemaInscripcionesModel;
import cl.duoc.sistema_inscripcion.service.SistemaInscripcionesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscripciones")
public class SistemaInscripcionesController {

    private final SistemaInscripcionesService service;

    public SistemaInscripcionesController(SistemaInscripcionesService service) {
        this.service = service;
    }

    // POST: Crear inscripción
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody SistemaInscripcionesModel inscripcion) {
        try {
            SistemaInscripcionesModel nueva = service.guardar(inscripcion);
            return ResponseEntity.status(201).body(nueva);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: no se pudo procesar la inscripción");
        }
    }

    // GET: Listar todas
    @GetMapping
    public List<SistemaInscripcionesModel> listar() {
        return service.obtenerTodas();
    }

    // GET BY ID: Buscar una específica
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<SistemaInscripcionesModel> ins = service.obtenerPorId(id);
        
        if (ins.isPresent()) {
            return ResponseEntity.ok(ins.get());
        } else {
            return ResponseEntity.status(404).body("error de la aplicación: inscripción no encontrada");
        }
    }

    // PUT: Actualizar datos de inscripción
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody SistemaInscripcionesModel datos) {
        try {
            SistemaInscripcionesModel act = service.actualizar(id, datos);
            if (act != null) {
                return ResponseEntity.ok(act);
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: id de inscripción inexistente");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: fallo al actualizar la inscripción");
        }
    }

    // DELETE: Eliminar inscripción
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            if (service.eliminar(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: no se encontró el registro para eliminar");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: error en la operación de borrado");
        }
    }
}