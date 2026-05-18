package cl.duoc.pagos.client;

import cl.duoc.pagos.dto.EstudianteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gestion-estudiante", url = "http://entorno_programacion1:8081")
public interface EstudianteClient {

    @GetMapping("/api/estudiantes/rut/{rut}")
    EstudianteDTO buscarPorRut(@PathVariable("rut") String rut);

    @GetMapping("/api/estudiantes/{id}")
    EstudianteDTO obtenerPorId(@PathVariable("id") Long id);
}