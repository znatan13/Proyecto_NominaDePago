package com.empleado.bono.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empleado.bono.model.Bono;
import com.empleado.bono.service.BonoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bonos")
public class BonoController {

    @Autowired
    private BonoService bonoService;

    @GetMapping("/listar")
    public List<Bono> listar() {
        return bonoService.listar();
    }

    @PostMapping("/guardar")
    public Bono guardar(@Valid @RequestBody Bono bono) {
        return bonoService.guardar(bono);
    }

    @GetMapping("/buscar/{id}")
    public Bono buscar(@PathVariable Integer id) {
        return bonoService.buscar(id);
    }

    @PutMapping("/actualizar/{id}")
    public Bono actualizar(@PathVariable Integer id, @Valid @RequestBody Bono bono) {
        return bonoService.actualizar(id, bono);
    }

}
