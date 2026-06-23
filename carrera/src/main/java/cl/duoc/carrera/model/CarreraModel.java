package cl.duoc.carrera.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carreras")
@Data                       // Genera automáticamente los Getters, Setters, toString, etc.
@NoArgsConstructor          // Genera el constructor vacío obligatorio para JPA
@AllArgsConstructor         // Genera el constructor con todos los parámetros que piden tus pruebas
public class CarreraModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String facultad;
    private int semestres;
}