package com.nomina.nominaPago.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomina.nominaPago.dto.NominaSimple;
import com.nomina.nominaPago.model.Nomina;
import com.nomina.nominaPago.service.NominaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/nomina")
public class NominaController {
    @Autowired
    private NominaService service;
    //Nomina usuario, usa el id de usuario para buscar su informacion y crear la nomina.
    @GetMapping("/{empleadoId}")
    public ResponseEntity<?> nominaUsuario(@PathVariable Integer empleadoId) {
        NominaSimple nomina = service.nominaDTO(empleadoId);
        return ResponseEntity.ok().body(nomina);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearNomina (@Valid @RequestBody Nomina nomina){
        Nomina nominaCreada = service.crearNomina(nomina);
        return ResponseEntity.status(201).body(nominaCreada);
    }
    
}
