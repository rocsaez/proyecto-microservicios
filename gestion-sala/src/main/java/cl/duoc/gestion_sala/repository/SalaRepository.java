package cl.duoc.gestion_sala.repository;

import cl.duoc.gestion_sala.model.SalaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Al extender de JpaRepository, obtenemos automáticamente los métodos:
 * save, findAll, findById y deleteById.
 */
@Repository
public interface SalaRepository extends JpaRepository<SalaModel, Long> {
}