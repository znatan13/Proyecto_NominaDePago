package com.empleados.turnos.model;

import lombok.Data;

@Data
// solo mostramos datos relevantes no es necesario mostrar todos los datos del empleado.
public class Empleado {

    private Integer empleadoId;
    private String rut;
    private String nombre;
    private String apellido;
    private String cargo;

}
// no es una entidad de base de datos, solo es un modelo para mostrar datos de los empleados