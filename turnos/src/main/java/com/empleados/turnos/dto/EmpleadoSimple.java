package com.empleados.turnos.dto;

import java.util.List;

import com.empleados.turnos.model.Turnos;

import lombok.Data;

@Data
public class EmpleadoSimple {
    private String rut;
    private String nombre;
    private String apellido;
    private String cargo;

    private List<Turnos> turnos;

}
