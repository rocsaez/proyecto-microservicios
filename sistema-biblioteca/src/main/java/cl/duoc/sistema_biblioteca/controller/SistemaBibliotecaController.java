package cl.duoc.sistema_biblioteca.controller;

import cl.duoc.sistema_biblioteca.model.SistemaBibliotecaModel;
import cl.duoc.sistema_biblioteca.service.SistemaBibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/biblioteca")
public class SistemaBibliotecaController {

    @Autowired
    private SistemaBibliotecaService service;

    @GetMapping("/listar")
    public List<SistemaBibliotecaModel> listar() {
        return service.listarTodos();
    }

    @PostMapping("/crear")
    public SistemaBibliotecaModel crear(@RequestBody SistemaBibliotecaModel libro) {
        return service.guardar(libro);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}