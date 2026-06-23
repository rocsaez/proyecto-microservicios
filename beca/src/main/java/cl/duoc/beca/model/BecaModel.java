package cl.duoc.beca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "becas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BecaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreBeca;
    private Double porcentajeDescuento;
    private String rutEstudiante;
    private String estado; 
}