package cl.duoc.gestion_estudiante.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Table(name = "estudiantes") 
public class GestionEstudianteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, unique = true, length = 12) // Integridad: RUT único y obligatorio
    private String rut;
    
    @Column(nullable = false, unique = true, length = 150) // Integridad: Correo único y obligatorio
    private String correo;
}