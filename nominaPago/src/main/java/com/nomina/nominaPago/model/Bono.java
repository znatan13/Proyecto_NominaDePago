package com.nomina.nominaPago.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Bono {
    private int bonoid;
    private String nombreBono;
    private String tipoBono;
    private Float bonoEmpleado;
    private Float porcentajeBono;
    private LocalDate fechaEntrega;
    private String descripcion;
}
