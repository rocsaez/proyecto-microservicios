package cl.duoc.sistema_biblioteca.controller;

import cl.duoc.sistema_biblioteca.dto.BibliotecaDTO;
import cl.duoc.sistema_biblioteca.dto.BibliotecaCreateDTO;
import cl.duoc.sistema_biblioteca.service.SistemaBibliotecaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/biblioteca")
public class SistemaBibliotecaController {

    @Autowired
    private SistemaBibliotecaService service;

    @GetMapping
    public ResponseEntity<List<BibliotecaDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<BibliotecaDTO> crear(@Valid @RequestBody BibliotecaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BibliotecaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody BibliotecaCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}