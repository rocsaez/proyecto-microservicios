package cl.duoc.beca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BecaDTO {
    private Long id;
    private String nombreBeca;
    private String rutEstudiante;
    private Double porcentajeDescuento;
    private String estado;
}