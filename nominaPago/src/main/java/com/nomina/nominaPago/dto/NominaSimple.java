package com.nomina.nominaPago.dto;

import com.nomina.nominaPago.model.Licencia;
import com.nomina.nominaPago.model.Nomina;

import lombok.Data;

@Data
public class NominaSimple {
    private String correo;
    private String nombre;
    private String apellido;
    private String rut;

    private String cargo;
    private String horario;

    private String AFP;
    private double sueldo_Base;

    private Licencia licencias;
    private Nomina nomina;

    private String nombre_Bono;
    private double cantidad_bono;
    private String descripcion_Bono;

    
    private double sueldo_Total;

}