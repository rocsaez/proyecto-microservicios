package cl.duoc.gestion_estudiante.repository;

import cl.duoc.gestion_estudiante.model.GestionEstudianteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GestionEstudianteRepository extends JpaRepository<GestionEstudianteModel, Long> {
    // Aquí no hay que escribir código, JpaRepository ya trae los métodos de guardado y búsqueda.
}