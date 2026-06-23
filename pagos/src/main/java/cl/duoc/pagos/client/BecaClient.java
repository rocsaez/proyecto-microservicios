package cl.duoc.pagos.client;

import cl.duoc.pagos.dto.BecaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "beca-client", url = "${becas.service.url}")
public interface BecaClient {

    @GetMapping("/api/becas/estudiante/{rut}")
    BecaDTO obtenerBecaPorRut(@PathVariable("rut") String rut);
}