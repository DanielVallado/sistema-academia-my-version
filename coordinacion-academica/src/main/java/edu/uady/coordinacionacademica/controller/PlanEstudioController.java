package edu.uady.coordinacionacademica.controller;

import edu.uady.coordinacionacademica.service.PlanEstudioService;
import edu.uady.coordinacionacademica.model.PlanEstudio;
import edu.uady.coordinacionacademica.error.COAException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/plan-estudios")
@Log4j2
public class PlanEstudioController {

    @Autowired
    private PlanEstudioService planEstudiosService;

    @GetMapping
    public ResponseEntity<?> findAllPlanEstudios() {
        try {
            return ResponseEntity.ok().body(planEstudiosService.getAllPlanEstudios());
        }catch (COAException ex){
            log.warn("Sin datos");
            log.error(ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping( ("/{licenciatura-id}"))
    public ResponseEntity<?> findByLicenciaturaId(@PathVariable("licenciatura-id") long licenciaturaId) {
        try {
            return ResponseEntity.ok().body(planEstudiosService.getLicenciaturaMaterias(licenciaturaId));
        }catch (COAException ex){
            log.warn("Sin datos");
            log.error(ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPlanEstudios(@RequestBody PlanEstudio planEstudio) {
        try {
            return new ResponseEntity<>(planEstudiosService.createPlanEstudio(planEstudio), HttpStatus.CREATED);
        }catch (COAException ex){
            log.warn("Sin datos");
            log.error(ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public ResponseEntity<?> updatePlanEstudios(@RequestBody PlanEstudio planEstudio) {
        try {
            return new ResponseEntity<>(planEstudiosService.updatePlanEstudio(planEstudio), HttpStatus.CREATED);
        }catch (COAException ex){
            log.warn("Sin datos");
            log.error(ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePlanEstudios(@PathVariable (value = "id") Long id) {
        planEstudiosService.deletePlanEstudio(id);
    }

}