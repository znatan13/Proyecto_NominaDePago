package com.historial.trabajadores.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private EmpleadoService service;

    @GetMapping()
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<EmpleadoDTO> empleado = service.buscarIdDTO(id);
        try {
            if (empleado.isEmpty()) {
                return ResponseEntity.status(404).body("El id no existe");
            }
            return ResponseEntity.ok(empleado.get()); // mostrara los datos del empleado buscado (DTO)

        } catch (Exception error) {
            return ResponseEntity.status(500).body("error de la base de datos");

        }
    }

    @GetMapping("/buscar/rut/{rut}")
    public ResponseEntity<?> buscarPorRut(@PathVariable String rut) {
        Optional<EmpleadoDTO> empleado = service.buscarRutDTO(rut);
        try {
            if (empleado.isEmpty()) {
                return ResponseEntity.status(404).body("EL rut que busca no existe");
            }
            return ResponseEntity.ok(empleado.get());
        } catch (Exception error) {
            return ResponseEntity.status(500).body("Error del sistema");

        }
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
