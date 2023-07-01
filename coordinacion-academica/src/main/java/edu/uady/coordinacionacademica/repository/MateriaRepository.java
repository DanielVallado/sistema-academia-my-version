package edu.uady.coordinacionacademica.repository;

import edu.uady.coordinacionacademica.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MateriaRepository extends JpaRepository<Materia, Long> {

    Optional<Materia> findByClaveMateria(String claveMateria);

}
