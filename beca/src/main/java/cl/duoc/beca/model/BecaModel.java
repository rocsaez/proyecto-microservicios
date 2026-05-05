package cl.duoc.beca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "becas")
@Data
public class BecaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreBeca;
    private Double porcentajeDescuento;
    private String rutEstudiante;
    private String estado; // Ejemplo: "Asignada", "En Trámite"
}