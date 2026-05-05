package cl.duoc.sistema_asistencia.controller;

import cl.duoc.sistema_asistencia.model.AsistenciaModel;
import cl.duoc.sistema_asistencia.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {
    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping
    public List<AsistenciaModel> listar() {
        return asistenciaService.listarTodas();
    }

    @PostMapping
    public AsistenciaModel marcar(@RequestBody AsistenciaModel asistencia) {
        return asistenciaService.registrarAsistencia(asistencia);
    }

    @DeleteMapping("/{id}")
public void eliminar(@PathVariable Long id) {
    asistenciaService.eliminarAsistencia(id);
}
}