package cl.duoc.gestion_estudiante.controller;

import cl.duoc.gestion_estudiante.dto.EstudianteDTO;
import cl.duoc.gestion_estudiante.repository.GestionEstudianteRepository;
import cl.duoc.gestion_estudiante.dto.EstudianteCreateDTO;
import cl.duoc.gestion_estudiante.service.GestionEstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class GestionEstudianteController {
    @Autowired
    private GestionEstudianteRepository repository;

    @Autowired
    private GestionEstudianteService service;

    // 1. CREAR: Ahora recibe EstudianteCreateDTO
    @PostMapping
    public ResponseEntity<EstudianteDTO> crear(@Valid @RequestBody EstudianteCreateDTO dto) {
        // Llamamos al service que ahora espera el DTO
        EstudianteDTO nuevo = service.guardarEstudiante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // 2. LEER TODOS: Ahora devuelve una lista de DTOs
    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> listarTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // 3. LEER POR ID: Devuelve EstudianteDTO
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // 4. ACTUALIZAR: Recibe el DTO de entrada
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EstudianteCreateDTO dto) {
        return ResponseEntity.ok(service.actualizarEstudiante(id, dto));
    }

    // 5. ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminarPorId(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    //conexiones con otros microservicios
    @GetMapping("/rut/{rut}")
public ResponseEntity<?> buscarPorRut(@PathVariable String rut) {
    return repository.findByRut(rut)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
}






}