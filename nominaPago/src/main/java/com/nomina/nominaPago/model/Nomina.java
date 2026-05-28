package com.nomina.nominaPago.model;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Nomina {
    @Id
    private Integer nomEmpleadoId;
    private Integer bonoId;
    private Integer turnoId;
    private String nombre;
    private String apellido;
    private String email;

}
