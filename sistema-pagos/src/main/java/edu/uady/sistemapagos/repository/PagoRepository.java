package edu.uady.sistemapagos.repository;

import edu.uady.sistemapagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    Optional<Pago> findByAlumnoIdAndMateriaId(Long AlumnoId, Long MateriaId);

}
