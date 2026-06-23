package cl.duoc.sistema_inscripcion.client;

import cl.duoc.sistema_inscripcion.dto.EstudianteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "gestion-estudiante", 
    url = "${estudiantes.service.url}",
    fallback = EstudianteClientFallback.class // <-- Agregamos el manejador de caídas
)
public interface EstudianteClient {

    @GetMapping("/api/estudiantes/rut/{rut}")
    EstudianteDTO obtenerPorRut(@PathVariable("rut") String rut);
}

// Clase espejo para manejar la falla si el servicio remoto se cae
@Component
class EstudianteClientFallback implements EstudianteClient {
    @Override
    public EstudianteDTO obtenerPorRut(String rut) {
        // En lugar de explotar con un error 500, puedes retornar null 
        // o lanzar una excepción personalizada controlada.
        return null; 
    }
}