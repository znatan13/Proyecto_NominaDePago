package com.empleado.bono.model;

import lombok.Data;

@Data
public class Empleado {
    private Integer empleadoId;
    private String nombre;
    private String apellido;
    private String rut;
    private String cargo;

}
