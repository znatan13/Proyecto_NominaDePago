package com.empleados.turnos.controller;

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

import com.empleados.turnos.dto.EmpleadoSimple;
import com.empleados.turnos.model.Turnos;
import com.empleados.turnos.service.TurnosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/turnos")
public class TurnosController {

    
    private TurnosService service;

    public TurnosController(TurnosService service){
        this.service = service;
    }

    @Operation(
        summary = "Lista a todos los turnos",
        description = "Se lista todos los turnos registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se obtiene la lista correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })
    @GetMapping
    public ResponseEntity<List<Turnos>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(
        summary = "Buscar turno por su ID unico",
        description = "Se obtiene informacion de turno a traves de su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se obtiene informacion de turno correctamente"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")

    })

    @GetMapping("/buscar/turnoid/{turnoId}")
    public ResponseEntity<?> buscarIdTurno(@PathVariable Integer turnoId) {
        Turnos buscar = service.buscarPorId(turnoId);
        return ResponseEntity.ok(buscar);
    }

    
    @Operation(
        summary =     "Buscar Id empleado por su identificador unico",
        description = "Se obtiene informacion de empleado registrado a un turno"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se obtiene informacion de turno correctamente"),
        @ApiResponse(responseCode = "404", description = "empleado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")

    })

    @GetMapping("/buscar/empleadoid/{empleadoId}")
    public ResponseEntity<?> buscarIdEmpleado(@PathVariable Integer empleadoId) {
        List<Turnos> buscar = service.buscarIdEmpleado(empleadoId);
        return ResponseEntity.ok().body(buscar);
    }

    
    @Operation(
        summary = "Obtener informacion completa de empleado y sus turnos",
        description = "Consulta el microservicio Empleados y combina la informacion del empleado con sus turnos registrados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se obtiene el registro informacion de turno correctamente"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")

    })

    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<?> empleadoId(@PathVariable Integer empleadoId) {
        EmpleadoSimple empleado = service.obtenerTurnosConEmpleados(empleadoId);
        return ResponseEntity.ok().body(empleado);
    }
    
    
    @Operation(
        summary = "Registra un turno al sistema",
        description = "Se Añade un turno nuevo al sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Se añade turno existosamente al sistema"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")

    })

    @PostMapping("/crear")
    public ResponseEntity<?> crearTurno(@Valid @RequestBody Turnos turnos) {
        Turnos crear = service.crearTurno(turnos);
        return ResponseEntity.status(201).body(crear);
    }

    @Operation(
        summary = "Actualizar turno por su ID unico",
        description = "Se Actualiza informacion de un turno a traves de su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turno Actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")

    })

    @PutMapping("/actualizar/{turnoId}")
    public ResponseEntity<?> actualizar(@PathVariable Integer turnoId, @Valid @RequestBody Turnos turnoNuevo) {
         Turnos actualizar = service.actualizarId(turnoId, turnoNuevo);
         return ResponseEntity.ok(actualizar);
    }

    
    @Operation(
        summary = "Eliminar turno por su ID unico",
        description = "Elimina un turno del sistema a traves de su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turno eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")

    })
    @DeleteMapping("/eliminar/{turnoId}")
    public ResponseEntity<?> eliminar(@PathVariable Integer turnoId) {
       service.eliminarPorId(turnoId);
       return ResponseEntity.ok().body("El turno con id " + turnoId + " Fue eliminado");
    }
}
