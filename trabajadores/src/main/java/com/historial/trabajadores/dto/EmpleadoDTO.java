package com.historial.trabajadores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmpleadoDTO {
    
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private String cargo;
    private double sueldoBase;
    private String afp;

}
