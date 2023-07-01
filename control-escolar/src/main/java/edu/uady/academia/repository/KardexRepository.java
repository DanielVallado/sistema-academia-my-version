package edu.uady.academia.repository;

import edu.uady.academia.model.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KardexRepository extends JpaRepository<Kardex, Long> {

    List<Kardex> findAllByAlumno_Matricula(String matricula);

}
