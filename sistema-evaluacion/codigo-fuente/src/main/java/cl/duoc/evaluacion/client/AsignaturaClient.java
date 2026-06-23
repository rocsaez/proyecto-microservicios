package cl.duoc.evaluacion.client;

import cl.duoc.evaluacion.dto.AsignaturaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "asignatura-client", url = "${asignaturas.service.url}")
public interface AsignaturaClient {

    @GetMapping("/api/asignaturas/sigla/{sigla}")
    AsignaturaDTO buscarPorSigla(@PathVariable("sigla") String sigla);
}