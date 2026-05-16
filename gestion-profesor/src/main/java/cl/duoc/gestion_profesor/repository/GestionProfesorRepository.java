package cl.duoc.gestion_profesor.repository;

import cl.duoc.gestion_profesor.model.GestionProfesorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GestionProfesorRepository extends JpaRepository<GestionProfesorModel, Long> {
}