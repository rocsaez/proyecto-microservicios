package cl.duoc.evaluacion.controller;

import cl.duoc.evaluacion.model.EvaluacionModel;
import cl.duoc.evaluacion.service.EvaluacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService service;

    public EvaluacionController(EvaluacionService service) {
        this.service = service;
    }

    // POST: Crear evaluación
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody EvaluacionModel evaluacion) {
        try {
            EvaluacionModel nueva = service.guardar(evaluacion);
            return ResponseEntity.status(201).body(nueva);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: no se pudo registrar la evaluación");
        }
    }

    // GET: Listar todas
    @GetMapping
    public List<EvaluacionModel> listar() {
        return service.obtenerTodas();
    }

    // GET BY ID: Buscar una específica
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<EvaluacionModel> eval = service.obtenerPorId(id);
        
        if (eval.isPresent()) {
            return ResponseEntity.ok(eval.get());
        } else {
            return ResponseEntity.status(404).body("error de la aplicación: evaluación no encontrada");
        }
    }

    // PUT: Actualizar nota o datos
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody EvaluacionModel datos) {
        try {
            EvaluacionModel act = service.actualizar(id, datos);
            if (act != null) {
                return ResponseEntity.ok(act);
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: el id de la evaluación no existe");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: datos de evaluación inválidos");
        }
    }

    // DELETE: Eliminar registro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            if (service.eliminar(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: no se encontró la evaluación para borrar");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: fallo al intentar eliminar");
        }
    }
}