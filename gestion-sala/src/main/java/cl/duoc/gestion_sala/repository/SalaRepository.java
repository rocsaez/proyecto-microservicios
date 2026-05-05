package cl.duoc.gestion_sala.repository;

import cl.duoc.gestion_sala.model.SalaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<SalaModel, Long> {
}