package com.nomina.nominaPago.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/usuario/nomina")
public class NominaController {

    @GetMapping("/descarga")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
