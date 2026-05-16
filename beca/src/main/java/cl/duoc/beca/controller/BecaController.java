package cl.duoc.beca.controller;
import cl.duoc.beca.model.BecaModel;
import cl.duoc.beca.service.BecaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional; // ¡Importante!

@RestController
@RequestMapping("/api/becas")
public class BecaController {
    private final BecaService service;
    public BecaController(BecaService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody BecaModel b) {
        try { return ResponseEntity.status(201).body(service.guardar(b)); }
        catch (Exception e) { return ResponseEntity.status(400).body("error de la aplicación: datos inválidos"); }
    }

    @GetMapping
    public List<BecaModel> listar() { return service.obtenerTodos(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        Optional<BecaModel> b = service.obtenerPorId(id);
        if (b.isPresent()) { return ResponseEntity.ok(b.get()); }
        else { return ResponseEntity.status(404).body("error de la aplicación: beca no existe"); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody BecaModel b) {
        try {
            BecaModel act = service.actualizar(id, b);
            if (act != null) return ResponseEntity.ok(act);
            return ResponseEntity.status(404).body("error de la aplicación: no se encontró");
        } catch (Exception e) { return ResponseEntity.status(400).body("error de la aplicación: fallo al actualizar"); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        if (service.eliminar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.status(404).body("error de la aplicación: id no existe");
    }
}