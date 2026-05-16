package com.historial.trabajadores.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    /*
     * No implementamos los metodos buscar por email ni por numero de telefono, ya
     * que funcionan internamente (al igual que buscar por rut)
     * en el service para validar al crear al empleado., ademas evitamos sobrecargar
     * el controller
     */
    @PostMapping("/crear")
    public ResponseEntity<?> crearEmpleados(@Valid @RequestBody Empleado empleado, BindingResult resultado) {
        Empleado empleadoNuevo = service.crearEmpleado(empleado);
        return ResponseEntity.status(201).body(empleadoNuevo);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEmpleado(@Valid @PathVariable Integer id, @RequestBody Empleado empleado,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> error = new HashMap<>();
            result.getFieldErrors().forEach(errores -> error.put(errores.getField(), errores.getDefaultMessage()));
            return ResponseEntity.badRequest().body(error);
        }
        try {
            Optional<Empleado> existe = service.actualizarPorId(id, empleado);
            if (existe.isEmpty()) {
                return ResponseEntity.status(404).body("Datos erroneos intente denuevo");
            }
            return ResponseEntity.status(200).body("Empleado actualizado con exito");
        } catch (Exception error) {
            return ResponseEntity.status(500).body("Error del sistema");

        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Integer id) {
        try {

            boolean eliminar = service.eliminarEmpleado(id);
            if (eliminar) {
                return ResponseEntity.status(200).body("El id ha sido eliminado!!");
            }
            return ResponseEntity.status(404).body("EL id que quiere eliminar no existe");

        } catch (Exception error) {
            return ResponseEntity.status(500).body("Error de la base de datos");

        }
    }

}
