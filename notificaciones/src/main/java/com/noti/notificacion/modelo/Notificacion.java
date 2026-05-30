package com.noti.notificacion.modelo;

import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public class Notificacion {

    @Id
    @NotNull
    private Integer notificacionId;

    private Integer empleadoId;

    private String titulo;

    private String mensaje;

    private LocalDate fecha;

}
