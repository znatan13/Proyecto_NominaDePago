package com.auth.autenticacion.dto;

import lombok.Data;

@Data
// Este dto servira para que el usuario ingrese datos al login
// es decir solo ingresara nombreusuario y password.
public class LoginDTO {

    private String nombreUsuario;
    private String password;

}
