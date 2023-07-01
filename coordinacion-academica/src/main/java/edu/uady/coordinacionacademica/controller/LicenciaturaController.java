package edu.uady.coordinacionacademica.controller;

import edu.uady.coordinacionacademica.error.COAException;
import edu.uady.coordinacionacademica.model.Licenciatura;
import edu.uady.coordinacionacademica.service.LicenciaturaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/licenciatura")
@Log4j2
public class LicenciaturaController {

    @Autowired
    private LicenciaturaService licenciaturaService;

    @GetMapping
    public ResponseEntity<?> getAllLicenciatura() {
        try {
            return ResponseEntity.ok().body(licenciaturaService.getAllLicenciaturas());
        }catch (COAException ex){
            log.warn("Sin datos");
            log.error(ex);
            return new ResponseEntity<>("Datos no encontrados", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public Licenciatura createLicenciatura(@RequestBody Licenciatura licenciatura){
        return licenciaturaService.createLicenciatura(licenciatura);
    }

    @PutMapping("/{revoe}")
    public ResponseEntity<?> updateLicenciatura(@RequestBody Licenciatura licenciatura, @PathVariable(value = "revoe")String revoe) {
        try {
            return  ResponseEntity.ok().body(licenciaturaService.updateLicenciatura(licenciatura, revoe));
        }catch (COAException ex){
            log.warn("Sin datos");
            log.error(ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteKardex(@PathVariable("id") Long id){
        licenciaturaService.deleteLicenciatura(id);
    }
    
}
