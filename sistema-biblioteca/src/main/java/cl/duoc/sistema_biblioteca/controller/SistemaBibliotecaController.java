package cl.duoc.sistema_biblioteca.controller;

import cl.duoc.sistema_biblioteca.model.SistemaBibliotecaModel;
import cl.duoc.sistema_biblioteca.service.SistemaBibliotecaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/biblioteca")
public class SistemaBibliotecaController {

    private final SistemaBibliotecaService service;

    public SistemaBibliotecaController(SistemaBibliotecaService service) {
        this.service = service;
    }

    // POST: Crear libro
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody SistemaBibliotecaModel libro) {
        try {
            SistemaBibliotecaModel nuevo = service.guardar(libro);
            return ResponseEntity.status(201).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: no se pudo registrar el libro");
        }
    }

    // GET: Listar catálogo
    @GetMapping
    public List<SistemaBibliotecaModel> listar() {
        return service.obtenerTodos();
    }

    // GET BY ID: Buscar libro
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<SistemaBibliotecaModel> libro = service.obtenerPorId(id);
        
        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get());
        } else {
            return ResponseEntity.status(404).body("error de la aplicación: el libro no existe en el sistema");
        }
    }

    // PUT: Editar libro
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody SistemaBibliotecaModel datos) {
        try {
            SistemaBibliotecaModel actualizado = service.actualizar(id, datos);
            if (actualizado != null) {
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: id de libro no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: fallo al actualizar los datos del libro");
        }
    }

    // DELETE: Borrar libro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            if (service.eliminar(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(404).body("error de la aplicación: no se encontró el libro para eliminar");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("error de la aplicación: hubo un error al intentar borrar");
        }
    }
}