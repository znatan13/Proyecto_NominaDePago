package com.nomina.nominaPago.model;

import lombok.Data;

@Data
public class Empleado {
    private Integer empleadoId;

    private String rut;
    private String nombre;
    private String apellido;

    private String email;
    private String numeroTelefono;
    private String direccion;
    private String estadoCivil;
    
    private String fechaIngreso;
    private String fechaSalida;
    private String cargo;
    private String estado;
    private double sueldoBase;
    private String AFP;
}
