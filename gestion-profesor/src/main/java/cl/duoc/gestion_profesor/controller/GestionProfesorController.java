package cl.duoc.gestion_profesor.controller;
import cl.duoc.gestion_profesor.model.GestionProfesorModel;
import cl.duoc.gestion_profesor.service.GestionProfesorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;



@RestController
@RequestMapping("/api/profesores")
public class GestionProfesorController {
    private final GestionProfesorService service;
    public GestionProfesorController(GestionProfesorService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody GestionProfesorModel p) {
        try { return ResponseEntity.status(201).body(service.guardar(p)); }
        catch (Exception e) { return ResponseEntity.status(400).body("error de la aplicación: datos inválidos"); }
    }

   @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        // 1. Buscamos al profesor en el servicio
        Optional<GestionProfesorModel> profesor = service.obtenerPorId(id);

        // 2. Si existe, lo devolvemos con un OK (200)
        if (profesor.isPresent()) {
            return ResponseEntity.ok(profesor.get());
        } 
        // 3. Si no existe, devolvemos el error que tú quieres (404)
        else {
            return ResponseEntity.status(404).body("error de la aplicación: profesor no existe");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody GestionProfesorModel p) {
        try {
            GestionProfesorModel act = service.actualizar(id, p);
            return act != null ? ResponseEntity.ok(act) : ResponseEntity.status(404).body("error de la aplicación: no encontrado");
        } catch (Exception e) { return ResponseEntity.status(400).body("error de la aplicación: fallo al actualizar"); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        return service.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.status(404).body("error de la aplicación: id inexistente");
    }
}