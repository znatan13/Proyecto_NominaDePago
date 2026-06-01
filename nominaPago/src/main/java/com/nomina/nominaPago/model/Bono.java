package com.nomina.nominaPago.model;

import lombok.Data;

@Data
public class Bono {
    private int bonoid;
    
    private String nombreBono;
    private String tipoBono;
    private Float bonoEmpleado;
    private String descripcion;
}
