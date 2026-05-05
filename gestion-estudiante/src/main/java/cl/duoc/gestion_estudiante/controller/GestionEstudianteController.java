package cl.duoc.gestion_estudiante.controller;

import cl.duoc.gestion_estudiante.model.GestionEstudianteModel;
import cl.duoc.gestion_estudiante.service.GestionEstudianteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * @RestController indica que esta clase responderá peticiones web y devolverá datos (como JSON).
 * @RequestMapping("/api/estudiantes") define la URL base.
 */
@RestController
@RequestMapping("/api/estudiantes")
public class GestionEstudianteController {

    private final GestionEstudianteService service;

    public GestionEstudianteController(GestionEstudianteService service) {
        this.service = service;
    }

    /**
     * CREATE - POST: Se usa para enviar y crear nuevos datos.
     */
    @PostMapping
    public ResponseEntity<GestionEstudianteModel> crear(@RequestBody GestionEstudianteModel estudiante) {
        GestionEstudianteModel nuevo = service.guardarEstudiante(estudiante);
        return (nuevo != null) ? ResponseEntity.ok(nuevo) : ResponseEntity.badRequest().build();
    }

    /**
     * READ ALL - GET: Solicita la lista de todos los estudiantes.
     */
    @GetMapping
    public List<GestionEstudianteModel> listarTodos() {
        return service.obtenerTodos();
    }

    /**
     * READ BY ID - GET: Solicita información de un elemento específico por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GestionEstudianteModel> obtenerPorId(@PathVariable Long id) {
        Optional<GestionEstudianteModel> estudiante = service.obtenerPorId(id);
        return estudiante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * READ BY RUT - GET: Busca un estudiante específico usando su RUT en la URL.
     */
    @GetMapping("/rut/{rut}")
    public ResponseEntity<GestionEstudianteModel> obtenerPorRut(@PathVariable String rut) {
        Optional<GestionEstudianteModel> estudiante = service.obtenerPorRut(rut);
        return estudiante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE - DELETE (Por ID): Elimina usando el ID numérico.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminarPorId(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * DELETE - DELETE (Por RUT): Elimina usando el RUT proporcionado en la URL.
     */
    @DeleteMapping("/rut/{rut}")
    public ResponseEntity<Void> eliminarPorRut(@PathVariable String rut) {
        if (service.eliminarPorRut(rut)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}