package cl.duoc.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {
    private Long id;
    private Long idEstudiante;
    private Double monto;
    private LocalDate fechaPago;
    private String estado;
}