package com.nomina.nominaPago.model;

import lombok.Data;

@Data
public class Usuario {
    private int id;
    private String nombreUsuario;
    private String password;
    private String email;
    private String rol;
}
