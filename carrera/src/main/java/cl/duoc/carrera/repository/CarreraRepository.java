package cl.duoc.carrera.repository;

import cl.duoc.carrera.model.CarreraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreraRepository extends JpaRepository<CarreraModel, Long> {
}