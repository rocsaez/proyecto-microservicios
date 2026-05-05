package cl.duoc.gestion_estudiante.repository;

import cl.duoc.gestion_estudiante.model.GestionEstudianteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * @Repository indica a Spring que este componente se encarga de la base de datos.
 * Al heredar de JpaRepository, le decimos qué clase manejar (GestionEstudianteModel) y qué tipo de dato es su ID (Long).
 */
@Repository
public interface GestionEstudianteRepository extends JpaRepository<GestionEstudianteModel, Long> {

    // Método para buscar por RUT
    Optional<GestionEstudianteModel> findByRut(String rut);

    // Método para eliminar por RUT (requiere @Transactional)
    @Transactional
    void deleteByRut(String rut);
}