package cl.duoc.carrera.controller;

import cl.duoc.carrera.model.CarreraModel;
import cl.duoc.carrera.service.CarreraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carreras")
public class CarreraController {

    private final CarreraService service;

    public CarreraController(CarreraService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CarreraModel carrera) {
        try {
            return ResponseEntity.status(201).body(service.guardar(carrera));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: no se pudo registrar la carrera");
        }
    }

    @GetMapping
    public List<CarreraModel> listar() {
        return service.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<CarreraModel> carrera = service.obtenerPorId(id);
        if (carrera.isPresent()) {
            return ResponseEntity.ok(carrera.get());
        } else {
            return ResponseEntity.status(404).body("error de la aplicación: carrera no encontrada");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody CarreraModel datos) {
        try {
            CarreraModel act = service.actualizar(id, datos);
            if (act != null) return ResponseEntity.ok(act);
            return ResponseEntity.status(404).body("error de la aplicación: id de carrera inexistente");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: datos de carrera inválidos");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        if (service.eliminar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.status(404).body("error de la aplicación: no se encontró el registro para eliminar");
    }
}