package com.nomina.nominaPago.model;

import lombok.Data;

@Data
public class Turnos {
    private Integer turnoId;
    private Integer empleadoId;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private String estado;
}
