package com.empleados.turnos.controller;

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

import com.empleados.turnos.model.Turnos;
import com.empleados.turnos.service.TurnosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/turnos")
public class TurnosController {

    @Autowired
    private TurnosService service;

    @GetMapping
    public ResponseEntity<List<Turnos>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/buscar/turnoid/{turnoId}")
    public ResponseEntity<?> buscarIdTurno(@PathVariable Integer turnoId) {
        Turnos buscar = service.buscarPorId(turnoId);
        return ResponseEntity.ok(buscar);
    }

    @GetMapping("/buscar/empleadoid/{empleadoId}")
    public ResponseEntity<?> buscarIdEmpleado(@PathVariable Integer empleadoId) {
        Optional<Turnos> buscar = service.buscarIdEmpleado(empleadoId);
        if (buscar.isEmpty()) {
            return ResponseEntity.status(404).body("EL turno del Empleado no existe");
        }
        return ResponseEntity.ok(buscar.get());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearTurno(@Valid @RequestBody Turnos turnos) {
        Optional<Turnos> nuevo = service.crearTurno(turnos);
        return ResponseEntity.status(201).body(nuevo.get());
    }

    @PutMapping("/actualizar/{turnoId}")
    public ResponseEntity<?> actualizar(@PathVariable Integer turnoId, @Valid @RequestBody Turnos turnoNuevo) {
         Turnos actualizar = service.actualizarId(turnoId, turnoNuevo);
         return ResponseEntity.ok(actualizar);
    }

    @DeleteMapping("/eliminar/{turnoId}")
    public ResponseEntity<?> eliminar(@PathVariable Integer turnoId) {
        boolean eliminar = service.eliminarPorId(turnoId);
        return ResponseEntity.status(200).body(eliminar);
    }
}
