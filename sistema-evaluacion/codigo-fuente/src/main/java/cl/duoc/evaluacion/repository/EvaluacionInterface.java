package cl.duoc.evaluacion.repository;

import cl.duoc.evaluacion.model.EvaluacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionInterface extends JpaRepository<EvaluacionModel, Long> {
}