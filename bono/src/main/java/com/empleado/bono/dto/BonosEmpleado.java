package com.empleado.bono.dto;


import java.util.List;

import com.empleado.bono.model.Bono;

import lombok.Data;


@Data
public class BonosEmpleado {

    private String nombre;
    private String apellido;
    private String rut;
    private String cargo;

    private List<Bono> bono;

}
