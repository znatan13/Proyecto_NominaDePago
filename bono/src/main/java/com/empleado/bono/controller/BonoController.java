package com.empleado.bono.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empleado.bono.dto.BonosEmpleado;
import com.empleado.bono.model.Bono;
import com.empleado.bono.service.bonoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/bonos")
public class BonoController {

    private bonoService service;

    public BonoController(bonoService service){
        this.service = service;
    }
    @GetMapping()
    //metodo listar los bonos disponibles
    public ResponseEntity<List<Bono>> listar(){
        return ResponseEntity.ok().body(service.listarBonos());
    }
    //metodo crear un bono
    @PostMapping("/crear")
    public ResponseEntity<?> crearBono (@Valid @RequestBody Bono bonoNuevo) {
        Bono bonoCreado = service.crearBono(bonoNuevo);
        return ResponseEntity.status(201).body(bonoCreado);
    }
    //metodo actualizar un Bono por su id
    @PutMapping("/actualizar/{bonoId}")
    public ResponseEntity<?> actualizarBono(@PathVariable Integer bonoId, @RequestBody Bono bonoNuevo){
        Bono bonoActualizar = service.actualizarBono(bonoId, bonoNuevo);
        return ResponseEntity.ok().body(bonoActualizar);
    }
    //metodo buscar bono por su id
    @GetMapping("/buscar/bono/{bonoId}")
    public ResponseEntity<?> buscarBono (@PathVariable Integer bonoId) {
        Bono buscar = service.buscarBono(bonoId);
        return ResponseEntity.ok().body(buscar);
    }
    //metodo buscar un empleado por id y mostrar bono (solo id)
    @GetMapping("/buscar/empleado/{empleadoId}")    
    public ResponseEntity<?> buscarEmpleadoId(@PathVariable Integer empleadoId){
        List<Bono> buscar = service.buscarIdEmpleado(empleadoId);
        return ResponseEntity.ok().body(buscar);
    }
    //metodo buscar los bonos con empleados asociados a tal bono
    @GetMapping("/empleados/{empleadoId}")
    public ResponseEntity<?> bonosEmpleado (@PathVariable Integer empleadoId) {
        BonosEmpleado bonosEmpleado = service.bonosEmpleado(empleadoId);
        return ResponseEntity.ok().body(bonosEmpleado);
    }
    @DeleteMapping("/eliminar/{bonoId}")
    public ResponseEntity<?> eliminarBono (@PathVariable Integer bonoId){
        service.eliminarBono(bonoId);
        return ResponseEntity.ok().body("El bono ha sido eliminado");
    }
    

    
    
    

}
