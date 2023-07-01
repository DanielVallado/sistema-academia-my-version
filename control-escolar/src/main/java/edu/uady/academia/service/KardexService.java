package edu.uady.academia.service;

import edu.uady.academia.dto.KardexAlumno;
import edu.uady.academia.dto.MateriasKardex;
import edu.uady.academia.dto.client.LicenciaturaMateriaDTO;
import edu.uady.academia.dto.client.MateriaDTO;
import edu.uady.academia.error.ControlEscolarException;
import edu.uady.academia.model.Alumno;
import edu.uady.academia.model.Kardex;
import edu.uady.academia.repository.KardexRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class KardexService {

    @Autowired
    private KardexRepository kardexRepository;

    public Kardex createKardex(Kardex kardex){
        log.info("crea Kardex: "+kardex.toString());
        return kardexRepository.save(kardex);
    }

    public void createKardexForAlumno(Alumno alumno) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        log.info(alumno.getLicenciaturaId());
        ResponseEntity<LicenciaturaMateriaDTO> response = restTemplate.exchange("http://localhost:8090/sistema-academia/plan-estudios/"
                + alumno.getLicenciaturaId(), HttpMethod.GET, entity, LicenciaturaMateriaDTO.class);

        LicenciaturaMateriaDTO responseMaterias = response.getBody();
        log.info("Consumo endpoint desde Control Escolar");

        // Crear los kardex
        assert responseMaterias != null;
        List<MateriaDTO> materias = responseMaterias.getMaterias();

        for (MateriaDTO materia : materias) {
            Kardex kardex = new Kardex();
            kardex.setFolioKardex(generateFolioKardex());
            kardex.setMateria(Long.valueOf(materia.getClaveMateria()));
            kardex.setCalificacion(0.0);
            kardex.setAlumno(alumno);
            kardexRepository.save(kardex);
        }
    }

    public String generateFolioKardex() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    public Kardex updateKardex(Kardex kardex){
        log.info("actualiza Kardex: "+kardex.toString());
        return kardexRepository.save(kardex);
    }

    public List<Kardex> getAllKardexs() throws Exception{
        List<Kardex> kardexes = kardexRepository.findAll();
        if(kardexes.isEmpty()){
            throw new ControlEscolarException("no se encontraron datos");
        }
        return  kardexes;
    }

    public KardexAlumno findByKardexByAlumno(String matricula) throws Exception {
        List<Kardex> kardex = kardexRepository.findAllByAlumno_Matricula(matricula);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<LicenciaturaMateriaDTO> response = restTemplate.exchange("http://localhost:8090/sistema-academia/plan-estudios/"
                        +kardex.get(0).getAlumno().getLicenciaturaId(),
                HttpMethod.GET, entity, LicenciaturaMateriaDTO.class);
        LicenciaturaMateriaDTO ResponseDto = response.getBody();

        log.info("Consumo endpoint desde Control Escolar");
        log.info(ResponseDto);

        KardexAlumno kardexAlumno = new KardexAlumno();
        kardexAlumno.setNombreCompleto(kardex.get(0).getAlumno().getNombre()+" "+kardex.get(0).getAlumno().getApellidos());
        kardexAlumno.setFolio(kardex.get(0).getFolioKardex());
        kardexAlumno.setLicenciatrua(ResponseDto.getLicenciatura());

        List<MateriasKardex> materiasKardexes = new ArrayList<>();
        ResponseDto.getMaterias().forEach(dto ->{
            MateriasKardex materiasKardex = new MateriasKardex();
            materiasKardex.setMateria(dto.getMateria());
            materiasKardex.setSemestre(dto.getSemestre());
            materiasKardex.setClaveMateria(dto.getClaveMateria());
            materiasKardexes.add(materiasKardex);
        });

        kardexAlumno.setMateriasKardex(materiasKardexes);
        return kardexAlumno;
    }

    public void deleteKardex(Long id){
        kardexRepository.deleteById(id);
    }

}
