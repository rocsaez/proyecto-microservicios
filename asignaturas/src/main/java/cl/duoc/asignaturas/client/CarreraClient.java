package cl.duoc.asignaturas.client;

import cl.duoc.asignaturas.dto.CarreraDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-carrera", url = "http://entorno_carrera:8089")
public interface CarreraClient {

    @GetMapping("/api/carreras/codigo/{codigo}")
    CarreraDTO buscarPorCodigo(@PathVariable("codigo") String codigo);
}
