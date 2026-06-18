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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service){
        this.service = service;
    }

    @Operation(
        summary =     "Listar empleados",
        description = "Lista a todos los empleados del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista Obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        return ResponseEntity.ok(service.listar());
    }

        @Operation(
        summary =     "Buscar empleado por ID",
        description = "Se obtiene informacion de empleados a travez de su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busqueda Obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no existe"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })

    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        EmpleadoDTO empleadoBuscadoId = service.buscarIdDTO(id);
        return ResponseEntity.ok().body(empleadoBuscadoId);
    }

        @Operation(
        summary =     "Buscar empleado por Rut",
        description = "Se obtiene informacion de empleados a travez de su Rut unico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busqueda Obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no existe"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })

    @GetMapping("/buscar/rut/{rut}")
    public ResponseEntity<?> buscarPorRut(@PathVariable String rut) {
       EmpleadoDTO empleadoBuscadoRut = service.buscarRutDTO(rut);
       return ResponseEntity.ok().body(empleadoBuscadoRut);
    }

        @Operation(
        summary =     "Buscar empleado por correo electronico",
        description = "Se obtiene informacion de empleados a travez de su correo electronico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busqueda Obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no existe"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })

    @GetMapping("/buscar/email/{email}")
    public ResponseEntity<?> buscarPorEmail (@PathVariable String email){
        EmpleadoDTO empleadoBuscadoEmail = service.buscarEmailDTO(email);
        return ResponseEntity.ok().body(empleadoBuscadoEmail);
    }

        @Operation(
        summary =     "Crear un nuevo empleado",
        description = "Registra un empleado nuevo en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Creado con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearEmpleados(@Valid @RequestBody Empleado empleado) {
        Empleado empleadoNuevo = service.crearEmpleado(empleado);
        return ResponseEntity.status(201).body(empleadoNuevo);
    }


        @Operation(
        summary =     "Actualizar empleado por ID",
        description = "Se actualizar informacion de empleado a travez de su id "
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "ID no existe"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Integer id, @Valid @RequestBody Empleado empleado){
        Empleado empleadoActualizado = service.actualizarPorId(id, empleado);
        return ResponseEntity.ok().body(empleadoActualizado);
    }
    
        @Operation(
        summary =     "Eliminar empleado por ID",
        description = "Se elimina informacion de empleado desde su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "ID no existe"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Integer id) {
        service.eliminarEmpleado(id);
        return ResponseEntity.ok().body("El empleado ha sido eliminado");
    }

}
