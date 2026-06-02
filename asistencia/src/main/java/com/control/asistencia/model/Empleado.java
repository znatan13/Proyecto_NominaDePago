package com.control.asistencia.model;

import lombok.Data;

@Data
public class Empleado {

    private Integer empleadoId;
    private String  nombre;
    private String  apellido;
    private String  email;
    private String  cargo;

} 
