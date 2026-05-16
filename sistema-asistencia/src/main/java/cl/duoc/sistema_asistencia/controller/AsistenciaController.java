package cl.duoc.sistema_asistencia.controller;

import cl.duoc.sistema_asistencia.model.AsistenciaModel;
import cl.duoc.sistema_asistencia.service.AsistenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    private final AsistenciaService service;

    public AsistenciaController(AsistenciaService service) {
        this.service = service;
    }

    // POST: Registrar asistencia
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody AsistenciaModel asistencia) {
        try {
            AsistenciaModel nueva = service.guardar(asistencia);
            return ResponseEntity.status(201).body(nueva);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: no se pudo registrar la asistencia");
        }
    }

    // GET: Listar todas
    @GetMapping
    public List<AsistenciaModel> listar() {
        return service.obtenerTodas();
    }

    // GET BY ID: Buscar una asistencia específica
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<AsistenciaModel> asistencia = service.obtenerPorId(id);
        
        if (asistencia.isPresent()) {
            return ResponseEntity.ok(asistencia.get());
        } else {
            return ResponseEntity.status(404).body("error de la aplicación: registro de asistencia no encontrado");
        }
    }

    // PUT: Editar registro
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody AsistenciaModel datos) {
        try {
            AsistenciaModel act = service.actualizar(id, datos);
            if (act != null) {
                return ResponseEntity.ok(act);
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: id de asistencia no existe");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: fallo al actualizar el registro");
        }
    }

    // DELETE: Eliminar registro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            if (service.eliminar(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: registro no encontrado para eliminar");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: fallo en la operación");
        }
    }
}