package cl.duoc.sistema_inscripcion.controller;

import cl.duoc.sistema_inscripcion.model.SistemaInscripcionesModel;
import cl.duoc.sistema_inscripcion.service.SistemaInscripcionesService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class SistemaInscripcionesController {

    private final SistemaInscripcionesService service;

    public SistemaInscripcionesController(SistemaInscripcionesService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public List<SistemaInscripcionesModel> listar() {
        return service.listarTodas();
    }

    @PostMapping("/crear")
    public SistemaInscripcionesModel crear(@RequestBody SistemaInscripcionesModel inscripcion) {
        return service.guardar(inscripcion);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/actualizar")
    public SistemaInscripcionesModel actualizar(@RequestBody SistemaInscripcionesModel inscripcion) {
        return service.actualizar(inscripcion);
    }

    @GetMapping("/obtener/{id}")
    public SistemaInscripcionesModel obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }
}