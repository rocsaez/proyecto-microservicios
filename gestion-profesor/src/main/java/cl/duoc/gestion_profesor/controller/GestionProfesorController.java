package cl.duoc.gestion_profesor.controller;

import cl.duoc.gestion_profesor.model.GestionProfesorModel;
import cl.duoc.gestion_profesor.service.GestionProfesorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/profesores")
public class GestionProfesorController {

    private final GestionProfesorService service;

    public GestionProfesorController(GestionProfesorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GestionProfesorModel> crear(@RequestBody GestionProfesorModel profesor) {
        GestionProfesorModel nuevo = service.guardarProfesor(profesor);
        return (nuevo != null) ? ResponseEntity.ok(nuevo) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public List<GestionProfesorModel> listarTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GestionProfesorModel> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<GestionProfesorModel> obtenerPorRut(@PathVariable String rut) {
        return service.obtenerPorRut(rut).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GestionProfesorModel> editar(@PathVariable Long id, @RequestBody GestionProfesorModel detalles) {
        GestionProfesorModel act = service.actualizarProfesor(id, detalles);
        return (act != null) ? ResponseEntity.ok(act) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        return service.eliminarPorId(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/rut/{rut}")
    public ResponseEntity<Void> eliminarPorRut(@PathVariable String rut) {
        return service.eliminarPorRut(rut) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}