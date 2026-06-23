package cl.duoc.gestion_profesor.controller;

import cl.duoc.gestion_profesor.dto.ProfesorDTO;
import cl.duoc.gestion_profesor.dto.ProfesorCreateDTO;
import cl.duoc.gestion_profesor.service.GestionProfesorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/profesores")
public class GestionProfesorController {

    @Autowired
    private GestionProfesorService service;

    @GetMapping
    public ResponseEntity<List<ProfesorDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<ProfesorDTO> crear(@Valid @RequestBody ProfesorCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> porId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfesorDTO> editar(@PathVariable Long id, @Valid @RequestBody ProfesorCreateDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (service.eliminar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}