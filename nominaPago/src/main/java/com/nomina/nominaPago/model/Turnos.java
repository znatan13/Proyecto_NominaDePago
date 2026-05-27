package com.nomina.nominaPago.model;

import java.time.LocalDate;


import lombok.Data;

@Data
public class Turnos {
    private Integer turnoId;
    private Integer empleadoId;
    private LocalDate fecha;
    private String horaInicio;
    private String horaFin;
    private String estado;
}
