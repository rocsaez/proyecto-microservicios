package cl.duoc.beca.client;

import cl.duoc.beca.dto.EstudianteDTO; 
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "gestion-estudiante", url = "http://entorno_programacion1:8081")
public interface EstudianteClient {

    @GetMapping("/api/estudiantes/rut/{rut}")
    EstudianteDTO buscarPorRut(@PathVariable("rut") String rut);
}