package cl.duoc.beca.controller;

import cl.duoc.beca.dto.BecaDTO;
import cl.duoc.beca.dto.BecaCreateDTO;
import cl.duoc.beca.service.BecaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/becas")
public class BecaController {

    @Autowired
    private BecaService service;

    @GetMapping
    public ResponseEntity<List<BecaDTO>> listar() { 
        return ResponseEntity.ok(service.obtenerTodos()); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<BecaDTO> porId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<BecaDTO> crear(@Valid @RequestBody BecaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BecaDTO> editar(@PathVariable Long id, @Valid @RequestBody BecaCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (service.eliminar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}