package cl.duoc.sistema_inscripcion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cl.duoc.sistema_inscripcion.dto.AsignaturaDTO;

@FeignClient(name = "asignatura-client", url = "${asignaturas.service.url}")
public interface AsignaturaClient {

    // Este método debe llamar a la misma ruta que tiene el Controller de Asignaturas
    @GetMapping("/api/asignaturas/sigla/{sigla}")
    AsignaturaDTO buscarPorSigla(@PathVariable("sigla") String sigla);
}