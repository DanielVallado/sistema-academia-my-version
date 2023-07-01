package edu.uady.coordinacionacademica.service;

import edu.uady.coordinacionacademica.error.COAException;
import edu.uady.coordinacionacademica.model.Materia;
import edu.uady.coordinacionacademica.repository.MateriaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    public Materia createMateria(Materia materia){
        log.info("Creación Materia: "+materia.toString());
        return materiaRepository.save(materia);
    }

    public Materia updateMateria(Materia materia, String claveMateria) throws COAException {
        if (materia.getId() == null) {
            throw new COAException("No se puede actualizar esta materia");
        }

        Optional<Materia> optionalMateria = materiaRepository.findByClaveMateria(claveMateria);

        if (optionalMateria.isPresent()) {
            log.info("Actualización Materia: "+ materia.toString());
            return materiaRepository.save(materia);
        }

        throw new COAException("No existe una materia con esa clave");
    }

    public List<Materia> getAllMaterias() throws Exception{
        List<Materia> materias = materiaRepository.findAll();
        if(materias.isEmpty()){
            throw new COAException("No se encontraron datos");
        }
        return  materias;
    }

    public void deleteMateria(Long id){
        materiaRepository.deleteById(id);
    }

}
