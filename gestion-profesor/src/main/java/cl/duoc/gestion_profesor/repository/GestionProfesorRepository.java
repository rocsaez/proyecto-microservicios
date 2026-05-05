package cl.duoc.gestion_profesor.repository;

import cl.duoc.gestion_profesor.model.GestionProfesorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface GestionProfesorRepository extends JpaRepository<GestionProfesorModel, Long> {
    
    // Método para buscar por RUT
    Optional<GestionProfesorModel> findByRut(String rut);

    // Método para eliminar por RUT (requiere @Transactional)
    @Transactional
    void deleteByRut(String rut);
}