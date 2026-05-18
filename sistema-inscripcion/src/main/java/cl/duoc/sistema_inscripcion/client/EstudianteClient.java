package cl.duoc.sistema_inscripcion.client;

import cl.duoc.sistema_inscripcion.dto.EstudianteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// El "name" es un apodo interno. La "url" se sacará de tu application.properties
@FeignClient(name = "gestion-estudiante", url = "http://entorno_programacion1:8081")
public interface EstudianteClient {

    // Este método debe ser idéntico al que creamos en el Controller de Estudiantes
    @GetMapping("/api/estudiantes/rut/{rut}")
    EstudianteDTO obtenerPorRut(@PathVariable("rut") String rut);
}