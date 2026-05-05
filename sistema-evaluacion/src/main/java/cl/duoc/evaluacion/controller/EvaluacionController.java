package cl.duoc.evaluacion.controller;

import cl.duoc.evaluacion.model.EvaluacionModel;
import cl.duoc.evaluacion.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService service;

    @GetMapping("/listar")
    public List<EvaluacionModel> listar() {
        return service.listarTodas();
    }

    @PostMapping("/crear")
    public EvaluacionModel crear(@RequestBody EvaluacionModel evaluacion) {
        return service.guardar(evaluacion);
    }

    @DeleteMapping("/eliminar/{id}")
public void eliminar(@PathVariable Long id) {
    service.eliminar(id);
}

    @PutMapping("/actualizar")
    public EvaluacionModel actualizar(@RequestBody EvaluacionModel evaluacion) {
        return service.actualizar(evaluacion);
    }

    @GetMapping("/obtener/{id}")
    public EvaluacionModel obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }
}