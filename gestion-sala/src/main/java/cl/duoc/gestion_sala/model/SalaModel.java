package cl.duoc.gestion_sala.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "salas")
@Data
public class SalaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreSala; // Ejemplo: Laboratorio 402
    private Integer capacidad;
    private String tipo;      // Ejemplo: Aula, Laboratorio, Auditorio
    private String ubicacion; // Ejemplo: Piso 4, Edificio A
}