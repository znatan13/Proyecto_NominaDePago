package com.nomina.nominaPago.dto;

import lombok.Data;

@Data
public class NominaSimple {
    private String nombre;
    private String apellido;
    private String cargo;
    private String horario;
    private String rut;
    private String AFP;
    private double sueldo_Base;
    private double descuento_de_AFP;
    private String Estado_Licencia;
    private String nombre_Bono;
    private double cantidad_bono;
    private String descripcion_Bono;
    private String correo;
    private double sueldo_Total;

}