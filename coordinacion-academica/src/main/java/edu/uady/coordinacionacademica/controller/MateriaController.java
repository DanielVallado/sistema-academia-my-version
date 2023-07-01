package edu.uady.coordinacionacademica.controller;

import edu.uady.coordinacionacademica.error.COAException;
import edu.uady.coordinacionacademica.model.Materia;
import edu.uady.coordinacionacademica.service.MateriaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/materia")
@Log4j2
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @GetMapping
    public ResponseEntity<?> getAllMateria() {
        try {
            return ResponseEntity.ok().body(materiaService.getAllMaterias());
        } catch (COAException ex){
            log.warn("Sin datos");
            log.error(ex);
            return new ResponseEntity<>("Datos no encontrados", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public Materia createMateria(@RequestBody Materia materia){
        log.info("Kardex a guardar: " + materia.toString());
        return materiaService.createMateria(materia);
    }

    @PutMapping("/{clave-materia}")
    public ResponseEntity<?> updateMateria(@RequestBody Materia materia, @PathVariable("clave-materia") String claveMateria) {
        try {
            return ResponseEntity.ok().body(materiaService.updateMateria(materia, claveMateria));
        } catch (COAException ex) {
            log.warn("La clave materia no existe");
            log.error(ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMateria(@PathVariable("id") Long id){
        materiaService.deleteMateria(id);
    }
    
}
