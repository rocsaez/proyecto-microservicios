package cl.duoc.pagos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "pagos")
public class PagoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idEstudiante;
    private Double monto;
    private LocalDate fechaPago;
    private String estado; // Ej: "PAGADO", "PENDIENTE", "ATRASADO"
}