package cl.duoc.gestion_sala.controller;

import cl.duoc.gestion_sala.model.SalaModel;
import cl.duoc.gestion_sala.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {
    @Autowired
    private SalaService salaService;

    @GetMapping
    public List<SalaModel> listar() {
        return salaService.obtenerTodas();
    }

    @PostMapping
    public SalaModel crear(@RequestBody SalaModel sala) {
        return salaService.guardarSala(sala);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        salaService.eliminarSala(id);
    }
}