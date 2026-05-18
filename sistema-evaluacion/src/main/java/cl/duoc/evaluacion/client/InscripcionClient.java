package cl.duoc.evaluacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "sistema-inscripcion", url = "http://entorno_inscripcion:8085")
public interface InscripcionClient {
    // Buscamos las inscripciones del alumno para ver si tiene la asignatura
    @GetMapping("/api/inscripciones/estudiante/rut/{rut}")
    List<Object> obtenerInscripcionesPorRut(@PathVariable("rut") String rut);
}