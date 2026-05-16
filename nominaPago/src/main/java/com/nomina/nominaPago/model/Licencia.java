package com.nomina.nominaPago.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Licencia {
    private int id;
    private int empleadoid;
    private int nombreEmpleado;
    private String tipoLicencia;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private String estado;
}
