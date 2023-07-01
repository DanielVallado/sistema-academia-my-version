package edu.uady.coordinacionacademica.service;

import edu.uady.coordinacionacademica.error.COAException;
import edu.uady.coordinacionacademica.model.Licenciatura;
import edu.uady.coordinacionacademica.repository.LicenciaturaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class LicenciaturaService {

    @Autowired
    private LicenciaturaRepository licenciaturaRepository;

    public Licenciatura createLicenciatura(Licenciatura licenciatura) {
        log.info("Creación licenciatura: " + licenciatura.toString());
        return licenciaturaRepository.save(licenciatura);
    }

    public Licenciatura updateLicenciatura(Licenciatura licenciatura, String revoe) throws Exception {
        if (licenciatura.getId() == null) {
            throw new COAException("No se puede actualizar esta licenciatura");
        }

        Optional<Licenciatura> optionalLicenciatura = licenciaturaRepository.findByRevoe(revoe);

        if (optionalLicenciatura.isPresent()) {
            log.info("Actualización licenciatura: " + licenciatura.toString());
            return licenciaturaRepository.save(licenciatura);
        }

        throw new COAException("No existe la licenciatura con el revoe");
    }

    public List<Licenciatura> getAllLicenciaturas() throws Exception {
        List<Licenciatura> licenciaturas = licenciaturaRepository.findAll();
        if (licenciaturas.isEmpty()) {
            throw new COAException("No se encontraron datos");
        }
        return licenciaturas;
    }

    public void deleteLicenciatura(Long id) {
        licenciaturaRepository.deleteById(id);
    }
    
}
