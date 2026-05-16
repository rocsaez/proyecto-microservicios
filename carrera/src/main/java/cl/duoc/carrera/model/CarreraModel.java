package cl.duoc.carrera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "carreras")
public class CarreraModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String facultad;
    private Integer semestres;
}