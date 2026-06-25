package com.empleado.licencia.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Notificacion {

    private Integer notificacionId;
    private Integer empleadoId;
    private String  titulo;
    private String  mensaje;
    private LocalDate fecha;

}
