package cl.duoc.sistema_asistencia.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.sistema_asistencia.dto.SalaDTO;

import java.util.List;

@FeignClient(name = "sala-client", url = "http://entorno_salas:8087")
public interface SalaClient {
    @GetMapping("/api/salas/{id}")
    SalaDTO obtenerSalaPorId(@PathVariable("id") Long id);
}
