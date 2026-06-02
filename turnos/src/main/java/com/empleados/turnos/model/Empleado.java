package com.empleados.turnos.model;

import lombok.Data;

@Data
public class Empleado {

    private Integer empleadoId;
    private String rut;
    private String nombre;
    private String apellido;
    private String cargo;

}