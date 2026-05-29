package com.nomina.nominaPago.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomina.nominaPago.dto.NominaSimple;
import com.nomina.nominaPago.service.NominaService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/usuario/nomina")
public class NominaController {
    @Autowired
    private NominaService service;
    //Nomina usuario, usa el id de usuario para buscar su informacion y crear la nomina.
    @GetMapping("/{id}")
    public NominaSimple nominaUsuario(@PathVariable Integer id) {
        return service.nomina(id);
    }
    
}
