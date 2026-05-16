package cl.duoc.pagos.controller;

import cl.duoc.pagos.model.PagoModel;
import cl.duoc.pagos.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PagoModel pago) {
        try {
            return ResponseEntity.status(201).body(service.guardar(pago));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: no se pudo registrar el pago");
        }
    }

    @GetMapping
    public List<PagoModel> listar() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<PagoModel> pago = service.obtenerPorId(id);
        if (pago.isPresent()) {
            return ResponseEntity.ok(pago.get());
        } else {
            return ResponseEntity.status(404).body("error de la aplicación: pago no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody PagoModel datos) {
        try {
            PagoModel act = service.actualizar(id, datos);
            if (act != null) return ResponseEntity.ok(act);
            return ResponseEntity.status(404).body("error de la aplicación: id de pago inexistente");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: datos de pago inválidos");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        if (service.eliminar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.status(404).body("error de la aplicación: no se encontró el pago para eliminar");
    }
}