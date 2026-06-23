package cl.duoc.sistema_asistencia.client;

import cl.duoc.sistema_asistencia.dto.EstudianteDTO; // Asegúrate de tener este DTO también
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gestion-estudiante", url = "http://32.198.93.76:8081")
public interface EstudianteClient {
    @GetMapping("/api/estudiantes/rut/{rut}")
    EstudianteDTO buscarPorRut(@PathVariable("rut") String rut);
}
