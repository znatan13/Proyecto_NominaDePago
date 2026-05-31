package com.historial.trabajadores.model;

import lombok.Data;

@Data
public class Notificacion {
    private Integer notificacionId;
    
    private Integer empleadoId;

    private String titulo;

    private String mensaje;

    private String fecha;
}