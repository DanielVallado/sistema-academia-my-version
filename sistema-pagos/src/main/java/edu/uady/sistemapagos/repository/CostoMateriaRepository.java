package edu.uady.sistemapagos.repository;

import edu.uady.sistemapagos.model.CostoMateria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostoMateriaRepository extends JpaRepository<CostoMateria, Long> {

    Optional<CostoMateria> findCostoMateriaById(Long id);

}
