package cl.duoc.sistema_asistencia.client;

import cl.duoc.sistema_asistencia.dto.InscripcionDTO; // Importa tu DTO espejo
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "gestion-inscripcion", url = "http://32.198.93.76:8085")
public interface InscripcionClient {

    @GetMapping("/api/inscripciones/estudiante/{rut}")
    List<InscripcionDTO> obtenerInscripcionesPorRut(@PathVariable("rut") String rut);
}
