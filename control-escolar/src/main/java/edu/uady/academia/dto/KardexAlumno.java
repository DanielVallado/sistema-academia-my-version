package edu.uady.academia.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class KardexAlumno {

    private String folio;
    private String nombreCompleto;
    private String licenciatrua;
    private List<MateriasKardex> materiasKardex;

}
