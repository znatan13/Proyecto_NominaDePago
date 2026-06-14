package com.empleado.bono.controller;

import java.util.List;

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

import com.empleado.bono.dto.BonosEmpleado;
import com.empleado.bono.model.Bono;
import com.empleado.bono.service.bonoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bonos")
public class BonoController {

    @Autowired
    private bonoService service;

    public BonoController(bonoService service){
        this.service = service;
    }

    @Operation(
        summary = "Listar Bonos",
        description = "Obtiene una lista de los empleados con sus respectivos bonos"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista Obtenida Correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos Invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @GetMapping()
    public ResponseEntity<List<Bono>> listar(){
        return ResponseEntity.ok().body(service.listarBonos());
    }

    @Operation(
        summary = "Agregar Bonos",
        description = "Opcion para poder agregar un bono al empleado"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bono Agregado Correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos Invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @PostMapping("/crear")
    public ResponseEntity<?> crearBono (@Valid @RequestBody Bono bonoNuevo) {
        Bono bonoCreado = service.crearBono(bonoNuevo);
        return ResponseEntity.status(201).body(bonoCreado);
    }

    @Operation(
        summary = "Actualizar Bonos por ID",
        description = "Opcion para Actualizar un bono al empleado por id"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modificacion de Empleado Correctamente"),
        @ApiResponse(responseCode = "400", description = "No existe el ID para modificarlo"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @PutMapping("/actualizar/{bonoId}")
    public ResponseEntity<?> actualizarBono(@PathVariable Integer bonoId, @RequestBody Bono bonoNuevo){
        Bono bonoActualizar = service.actualizarBono(bonoId, bonoNuevo);
        return ResponseEntity.ok().body(bonoActualizar);
    }

    @Operation(
        summary = "Buscar bonos por ID",
        description = "Opcion para buscar el bono de un empleado por ID"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se a encontrado el ID del emplado"),
        @ApiResponse(responseCode = "400", description = "ID del empleado no existe "),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @GetMapping("/buscar/bono/{bonoId}")
    public ResponseEntity<?> buscarBono (@PathVariable Integer bonoId) {
        Bono buscar = service.buscarBono(bonoId);
        return ResponseEntity.ok().body(buscar);
    }

    @Operation(
        summary = "Buscar Empleado por su ID",
        description = "Opcion para buscar el ID de empleado"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se a encontrado el ID del empleado"),
        @ApiResponse(responseCode = "400", description = "ID del empleado no encontrado o no existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @GetMapping("/buscar/empleado/{empleadoId}")    
    public ResponseEntity<?> buscarEmpleadoId(@PathVariable Integer empleadoId){
        List<Bono> buscar = service.buscarIdEmpleado(empleadoId);
        return ResponseEntity.ok().body(buscar);
    }

    
    @GetMapping("/empleados/{empleadoId}")
    public ResponseEntity<?> bonosEmpleado (@PathVariable Integer empleadoId) {
        BonosEmpleado bonosEmpleado = service.bonosEmpleado(empleadoId);
        return ResponseEntity.ok().body(bonosEmpleado);
    }

    @Operation(
        summary = "Buscar el Bono unico por el ID de empleado",
        description = "Opcion de buscar su bono unico con el id del empleado"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se a encontrado el ID del empleado para el Bono unico"),
        @ApiResponse(responseCode = "400", description = "ID de empleado no encontrado o no existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @GetMapping("/buscar/bonoUnico/{empleadoId}")
    public ResponseEntity<?> bonoUnico (@PathVariable Integer empleadoId){
        Bono bonounico = service.bonoUnico(empleadoId);
        return ResponseEntity.ok().body(bonounico);
    }

    @Operation(
        summary = "Eliminar Bonos por ID",
        description = "Opcion para eliminar el bono a un empleado por id"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se a eliminado el bono del empleado"),
        @ApiResponse(responseCode = "400", description = "ID de empleado no encontrado o no existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @DeleteMapping("/eliminar/{bonoId}")
    public ResponseEntity<?> eliminarBono (@PathVariable Integer bonoId){
        service.eliminarBono(bonoId);
        return ResponseEntity.ok().body("El bono ha sido eliminado");
    }

}
