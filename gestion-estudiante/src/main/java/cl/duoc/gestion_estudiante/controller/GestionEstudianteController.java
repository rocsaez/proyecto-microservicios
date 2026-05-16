package cl.duoc.gestion_estudiante.controller;

import cl.duoc.gestion_estudiante.model.GestionEstudianteModel;
import cl.duoc.gestion_estudiante.service.GestionEstudianteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class GestionEstudianteController {

    private final GestionEstudianteService service;

    public GestionEstudianteController(GestionEstudianteService service) {
        this.service = service;
    }

    // 1. CREAR: Guardar un estudiante nuevo
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody GestionEstudianteModel estudiante) {
        try {
            GestionEstudianteModel nuevo = service.guardarEstudiante(estudiante);
            return ResponseEntity.status(201).body(nuevo); // 201 significa "Creado"
        } catch (Exception e) {
            // Si falta un dato o el RUT está mal, manda este mensaje
            return ResponseEntity.status(400).body("error de la aplicación: no se pudo crear");
        }
    }

    // 2. LEER TODOS: Listar a todos los alumnos
    @GetMapping
    public List<GestionEstudianteModel> listarTodos() {
        return service.obtenerTodos();
    }

    // 3. LEER POR ID: Buscar uno solo
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<GestionEstudianteModel> estudiante = service.obtenerPorId(id);
        if (estudiante.isPresent()) {
            return ResponseEntity.ok(estudiante.get());
        } else {
            return ResponseEntity.status(404).body("Error de la aplicación: estudiante no encontrado");
        }
    }

    // 4. ACTUALIZAR: El método PUT que faltaba para editar datos
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody GestionEstudianteModel datosNuevos) {
        try {
            GestionEstudianteModel actualizado = service.actualizarEstudiante(id, datosNuevos);
            if (actualizado != null) {
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: no existe para actualizar");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: datos inválidos");
        }
    }

    // 5. ELIMINAR: Borrar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = service.eliminarPorId(id);
            if (eliminado) {
                return ResponseEntity.noContent().build(); // 204: Éxito pero vacío
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: id no existe");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: no se pudo eliminar");
        }
    }
}