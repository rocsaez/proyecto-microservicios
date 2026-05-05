package cl.duoc.beca.repository;

import cl.duoc.beca.model.BecaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BecaRepository extends JpaRepository<BecaModel, Long> {
}