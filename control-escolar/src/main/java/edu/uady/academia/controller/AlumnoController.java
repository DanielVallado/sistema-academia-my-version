package edu.uady.academia.controller;

import edu.uady.academia.error.ControlEscolarException;
import edu.uady.academia.model.Alumno;
import edu.uady.academia.service.AlumnoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/alumno")
@Log4j2
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<?> getAllAlumnos() {
        try {
            return ResponseEntity.ok().body(alumnoService.getAllAlumnos());
        } catch (ControlEscolarException ex){
            log.warn("Sin datos");
            log.error(ex);
            return new ResponseEntity<>("Datos no encontrados", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public Alumno createAlumno(@RequestBody Alumno alumno) {
        log.info("Alumno para guardar: " + alumno.toString());
        return alumnoService.createAlumno(alumno);
    }

    @PutMapping
    public Alumno updateAlumno(@RequestBody Alumno alumno) {
        log.info("Alumno actualizado: " + alumno.toString());
        return alumnoService.updateAlumno(alumno);
    }

    @DeleteMapping("/{id}")
    public void deleteAlumno(@PathVariable(value = "id") Long id) {
        log.info("Alumno eliminado: " + id);
        alumnoService.deleteAlumno(id);
    }

}
