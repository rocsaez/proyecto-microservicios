package cl.duoc.asignaturas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asignaturas")
public class AsignaturaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String sigla;
    private Integer creditos;
    
    @Column(name = "codigo_carrera")
    private String codigoCarrera;
    
    @Column(name = "id_profesor")
    private Long idProfesor;
}