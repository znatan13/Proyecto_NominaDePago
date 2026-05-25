package com.empleado.licencia.dto;

import java.util.List;

import com.empleado.licencia.model.Licencia;

import lombok.Data;

@Data
public class LicenciaEmpleado {

    private String nombre;
    private String apellido;
    private String rut;
    private String cargo;

    private List<Licencia> licencia;
}