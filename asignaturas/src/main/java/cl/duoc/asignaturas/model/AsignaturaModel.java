package cl.duoc.asignaturas.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "asignaturas")
public class AsignaturaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String sigla;
    private Integer creditos;
}