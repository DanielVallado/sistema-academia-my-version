package edu.uady.coordinacionacademica.service;

import edu.uady.coordinacionacademica.dto.LicenciaturaMateriaDTO;
import edu.uady.coordinacionacademica.dto.MateriaDTO;
import edu.uady.coordinacionacademica.error.COAException;
import edu.uady.coordinacionacademica.model.PlanEstudio;
import edu.uady.coordinacionacademica.repository.PlanEstudioRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class PlanEstudioService {

    @Autowired
    private PlanEstudioRepository planEstudioRepository;

    public PlanEstudio createPlanEstudio(PlanEstudio planEstudio) throws Exception {
        log.info("Crear Plan de estudios: " + planEstudio.toString());
        Optional<PlanEstudio> existePlanEstudios = planEstudioRepository
                .findByLicenciatura_RevoeAndAndMateriaClaveMateria(planEstudio.getLicenciatura().getRevoe(),
                        planEstudio.getMateria().getClaveMateria());

        if (existePlanEstudios.isEmpty()) {
            log.info("Insertar plan de estudios: " + planEstudio);
            return planEstudioRepository.save(planEstudio);
        }

        throw new COAException("Ya existe una materia asignada a esta licenciatura");
    }

    public PlanEstudio updatePlanEstudio(PlanEstudio planEstudio) throws Exception {
        if (planEstudio.getId() == null) {
            throw new COAException("No se puede actualizar este plan de estudios");
        }

        Optional<PlanEstudio> existePlanEstudios = planEstudioRepository
                .findByLicenciatura_RevoeAndAndMateriaClaveMateria(planEstudio.getLicenciatura().getRevoe(),
                        planEstudio.getMateria().getClaveMateria());

        if (existePlanEstudios.isPresent()) {
            log.info("Actualizar PlanEstudio: "+planEstudio.toString());
            return planEstudioRepository.save(planEstudio);
        }

        throw new COAException("No existe una materia asignada a esta licenciatura");
    }

    public List<PlanEstudio> getAllPlanEstudios() throws Exception {
        List<PlanEstudio> planEstudios = planEstudioRepository.findAll();

        if (planEstudios.isEmpty()) {
            throw new COAException("No se encontraron datos");
        }

        return  planEstudios;
    }

    public LicenciaturaMateriaDTO getLicenciaturaMaterias(Long licenciaturaId) throws Exception {
        List<PlanEstudio> planEstudios = planEstudioRepository.findPlanEstudioByLicenciatura_Id(licenciaturaId);

        if (planEstudios.isEmpty()) {
            throw new COAException("Datos no encontrados");
        }

        LicenciaturaMateriaDTO dto = new LicenciaturaMateriaDTO();
        dto.setLicenciatura(planEstudios.get(0).getLicenciatura().getNombre());

        List<MateriaDTO> materiasDto = new ArrayList<>();

        planEstudios.forEach(pe ->{
            MateriaDTO materia = new MateriaDTO();
            materia.setClaveMateria(pe.getMateria().getClaveMateria());
            materia.setMateria(pe.getMateria().getNombreMateria());
            materia.setSemestre(pe.getSemestre());
            materia.setCreditos(pe.getCreditos());
            materiasDto.add(materia);
        });

        dto.setMaterias(materiasDto);

        return dto;
    }

    public void deletePlanEstudio(Long id){
        planEstudioRepository.deleteById(id);
    }

}
