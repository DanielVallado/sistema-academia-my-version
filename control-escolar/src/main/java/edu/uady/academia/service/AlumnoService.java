package edu.uady.academia.service;

import edu.uady.academia.error.ControlEscolarException;
import edu.uady.academia.model.Alumno;
import edu.uady.academia.repository.AlumnoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Log4j2
public class AlumnoService {

    private AlumnoRepository alumnoRepository;
    private KardexService kardexService;

    @Autowired
    public void setAlumnoRepository(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Autowired
    public void setKardexServiceEnv(KardexService kardexService) {
        this.kardexService = kardexService;
    }

    public List<Alumno> getAllAlumnos() throws ControlEscolarException {
        List<Alumno> alumnos = alumnoRepository.findAll();

        if (alumnos.isEmpty()) {
            throw new ControlEscolarException("No se encontraron datos");
        }

        return  alumnos;
    }

    public Alumno createAlumno(Alumno alumno) {
       log.info("Crear alumno: " + alumno.toString());
        Alumno savedAlumno = alumnoRepository.save(alumno);
        kardexService.createKardexForAlumno(savedAlumno);
        return savedAlumno;
    }

    public Alumno updateAlumno(Alumno alumno) {
        log.info("Actualizar alumno: " + alumno.toString());
        return alumnoRepository.save(alumno);
    }

    public void deleteAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }

}
