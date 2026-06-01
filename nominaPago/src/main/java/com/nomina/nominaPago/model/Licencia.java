package com.nomina.nominaPago.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Licencia {
    private int empleadoId;
    private int nombreEmpleado;
    
    private String tipoLicencia;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
}
