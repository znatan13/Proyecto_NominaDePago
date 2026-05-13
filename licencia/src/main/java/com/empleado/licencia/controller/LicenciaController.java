package com.empleado.licencia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.empleado.licencia.service.LicenciaService;

import com.empleado.licencia.model.Licencia;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/licencias")
public class LicenciaController {

    @Autowired
    private LicenciaService licenciaService;

    @GetMapping("/listar")
    public List<Licencia> listar() {
        return licenciaService.listar();
    }

    @PostMapping("/guardar")
    public Licencia guardar(@Valid @RequestBody Licencia licencia) {
        return licenciaService.guardar(licencia);
    }

    @GetMapping("/buscar/{id}")
    public Licencia buscar(@PathVariable Integer id) {
        return licenciaService.buscar(id);
    }

    @PutMapping("/actualizar/{id}")
    public Licencia actualizar(@PathVariable Integer id, @Valid @RequestBody Licencia licencia) {
        return licenciaService.actualizar(id, licencia);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {

        licenciaService.eliminar(id);

        return "Licencia eliminada";
    }

}
