package cl.duoc.asignaturas.client;

import cl.duoc.asignaturas.dto.ProfesorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profesor-client", url = "http://entorno_profesor:8082")
public interface ProfesorClient {

    @GetMapping("/api/profesores/{id}")
    ProfesorDTO obtenerProfesor(@PathVariable("id") Long id);
}