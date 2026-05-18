package cl.duoc.gestion_sala.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaDTO {
    private Long id;
    private String nombreSala;
    private Integer capacidad;
    private String tipo;
    private String ubicacion;
}