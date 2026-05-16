package cl.duoc.asignaturas.controller;

import cl.duoc.asignaturas.model.AsignaturaModel;
import cl.duoc.asignaturas.service.AsignaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    private final AsignaturaService service;

    public AsignaturaController(AsignaturaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody AsignaturaModel asignatura) {
        try {
            return ResponseEntity.status(201).body(service.guardar(asignatura));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: no se pudo crear la asignatura");
        }
    }

    @GetMapping
    public List<AsignaturaModel> listar() {
        return service.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<AsignaturaModel> asig = service.obtenerPorId(id);
        if (asig.isPresent()) {
            return ResponseEntity.ok(asig.get());
        } else {
            return ResponseEntity.status(404).body("error de la aplicación: asignatura no existe");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody AsignaturaModel datos) {
        try {
            AsignaturaModel act = service.actualizar(id, datos);
            if (act != null) return ResponseEntity.ok(act);
            return ResponseEntity.status(404).body("error de la aplicación: id no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: fallo al actualizar asignatura");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        if (service.eliminar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.status(404).body("error de la aplicación: id no válido para borrar");
    }
}