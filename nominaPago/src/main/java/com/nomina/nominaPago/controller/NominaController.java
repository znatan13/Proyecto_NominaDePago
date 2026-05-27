package com.nomina.nominaPago.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomina.nominaPago.model.Empleado;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/usuario/nomina")
public class NominaController {

    //Nomina usuario, usa el id de usuario para buscar su informacion y crear la nomina.
    @GetMapping("/{id}")
    public List<Empleado> nominaUsuario(@PathVariable Integer id) {
        //cambiar a lo debido
        return null;
    }
    
}
