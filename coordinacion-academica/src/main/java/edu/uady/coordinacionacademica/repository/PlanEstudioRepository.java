package edu.uady.coordinacionacademica.repository;

import edu.uady.coordinacionacademica.model.PlanEstudio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanEstudioRepository extends JpaRepository<PlanEstudio, Long> {

    Optional<PlanEstudio> findByLicenciatura_RevoeAndAndMateriaClaveMateria(String revoe, String materia);

    List<PlanEstudio> findPlanEstudioByLicenciatura_Id(Long licenciaturaId);

}
