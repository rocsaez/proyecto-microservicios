package cl.duoc.gestion_sala.controller;

import cl.duoc.gestion_sala.model.SalaModel;
import cl.duoc.gestion_sala.service.SalaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaService service;

    public SalaController(SalaService service) {
        this.service = service;
    }

    // POST: Crear sala con try-catch
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody SalaModel sala) {
        try {
            SalaModel nueva = service.guardar(sala);
            return ResponseEntity.status(201).body(nueva);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: no se pudo crear la sala");
        }
    }

    // GET: Listar todas
    @GetMapping
    public List<SalaModel> listar() {
        return service.obtenerTodas();
    }

    // GET BY ID: Usando if-else para evitar errores de tipo con Optional
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<SalaModel> sala = service.obtenerPorId(id);
        
        if (sala.isPresent()) {
            return ResponseEntity.ok(sala.get());
        } else {
            return ResponseEntity.status(404).body("error de la aplicación: sala no encontrada");
        }
    }

    // PUT: Actualizar sala existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody SalaModel datos) {
        try {
            SalaModel act = service.actualizar(id, datos);
            if (act != null) {
                return ResponseEntity.ok(act);
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: id de sala no existe");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: fallo al actualizar datos");
        }
    }

    // DELETE: Eliminar sala
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            if (service.eliminar(id)) {
                return ResponseEntity.noContent().build(); // Éxito (204)
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: no se puede eliminar porque no existe");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: fallo en la operación de eliminación");
        }
    }
}