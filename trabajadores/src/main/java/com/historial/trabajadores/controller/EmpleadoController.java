package com.historial.trabajadores.controller;


import java.util.List;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.historial.trabajadores.dto.EmpleadoDTO;
import com.historial.trabajadores.model.Empleado;
import com.historial.trabajadores.service.EmpleadoService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        EmpleadoDTO empleadoBuscadoId = service.buscarIdDTO(id);
        return ResponseEntity.ok().body(empleadoBuscadoId);
    }

    @GetMapping("/buscar/rut/{rut}")
    public ResponseEntity<?> buscarPorRut(@PathVariable String rut) {
       EmpleadoDTO empleadoBuscadoRut = service.buscarRutDTO(rut);
       return ResponseEntity.ok().body(empleadoBuscadoRut);
    }

    @GetMapping("/buscar/email/{email}")
    public ResponseEntity<?> buscarPorEmail (@PathVariable String email){
        EmpleadoDTO empleadoBuscadoEmail = service.buscarEmailDTO(email);
        return ResponseEntity.ok().body(empleadoBuscadoEmail);
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearEmpleados(@Valid @RequestBody Empleado empleado) {
        Empleado empleadoNuevo = service.crearEmpleado(empleado);
        return ResponseEntity.status(201).body(empleadoNuevo);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Integer id, @Valid @RequestBody Empleado empleado){
        Empleado empleadoActualizado = service.actualizarPorId(id, empleado);
        return ResponseEntity.ok().body(empleadoActualizado);
    }
    

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Integer id) {
        service.eliminarEmpleado(id);
        return ResponseEntity.ok().body("El empleado ha sido eliminado");
    }

}
