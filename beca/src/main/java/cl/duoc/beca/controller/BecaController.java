package cl.duoc.beca.controller;

import cl.duoc.beca.model.BecaModel;
import cl.duoc.beca.service.BecaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/becas")
public class BecaController {

    private final BecaService service;

    public BecaController(BecaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public List<BecaModel> listar() {
        return service.listarTodas();
    }

    @PostMapping("/crear")
    public BecaModel crear(@RequestBody BecaModel beca) {
        return service.guardar(beca);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping("/obtener/{id}")
    public BecaModel obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }
}