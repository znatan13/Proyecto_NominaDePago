package com.empleados.turnos.dto;

import java.util.List;

import com.empleados.turnos.model.Turnos;

import lombok.Data;

@Data
public class EmpleadoSimple {

    private String nombre;
    private String apellido;
    private String cargo;

    //Creamos una lista , para la bd local y mostrar los turnos para los empleados
    private List<Turnos> turnos;

}
