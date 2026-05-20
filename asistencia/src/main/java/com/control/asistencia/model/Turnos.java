package com.control.asistencia.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Turnos {

    private Integer turnoId;
    private LocalDate fecha;
    private String horaInicio;
    private String horaFin;

} //clase solo para extraer los datos necesarios del microservicio turnos
