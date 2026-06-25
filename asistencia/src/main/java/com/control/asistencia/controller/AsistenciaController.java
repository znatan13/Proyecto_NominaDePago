package com.control.asistencia.controller;

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

import com.control.asistencia.dto.AsistenciaCompletaDTO;
import com.control.asistencia.model.Asistencia;
import com.control.asistencia.service.AsistenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/asistencia/asistencias")
public class AsistenciaController {

    private final AsistenciaService service;

    public AsistenciaController(AsistenciaService service){
        this.service = service;
    }

    @Operation(
        summary = "Lista registros de asistencia",
        description = "Lista toda la informacion de todos los registros de asistencia en el sistema"
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Lista de Informacion obtenida con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Asistencia>> listar() {
        return ResponseEntity.ok(service.listarAsistencias());
    }

        @Operation(
        summary = "Buscar asistencia por su ID",
        description = "Busca informacion de las asistencias por su ID"
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Informacion obtenida con exito"),
        @ApiResponse(responseCode = "404", description = "ID no existe"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })
    @GetMapping("/buscar/asistencia/{asistenciaId}")
    public ResponseEntity<?> buscarAsistencia (@PathVariable Integer asistenciaId) {
        Asistencia buscar = service.buscarAsistencia(asistenciaId);
        return ResponseEntity.ok().body(buscar);
    }


        @Operation(
        summary = "Registrar asistencia en el sistema",
        description = "Registra nuevas asistencia en el sistema"
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "201", description = "Asistencia registrada con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearRegistro (@Valid @RequestBody Asistencia asistencia){
        Asistencia asistenciaNueva = service.crearRegistro(asistencia);
        return ResponseEntity.status(201).body(asistenciaNueva);
    }

        @Operation(
        summary = "Actualizar asistencia por su ID",
        description = "Actualiza informacion de asistencia por su ID"
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Asistencia actualizada con exito"),
        @ApiResponse(responseCode = "404", description = "ID no existe"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })
    @PutMapping("/actualizar/{asistenciaId}")
    public ResponseEntity<?> actualizar(@PathVariable Integer asistenciaId, @Valid @RequestBody Asistencia asistencia ) {
        Asistencia asistenciaActualizada = service.actualizarAsistencia(asistenciaId, asistencia); 
        return ResponseEntity.ok(asistenciaActualizada);
    }

        @Operation(
        summary = "Eliminar asistencia por su ID",
        description = "Elimina toda la informacion de asistencia por su ID"
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Asistencia eliminada con exito"),
        @ApiResponse(responseCode = "404", description = "ID no existe"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar (@PathVariable Integer id){
        service.eliminarRegistro(id);
        return ResponseEntity.ok().body("El registro : " + id + " ha sido eliminado");
    }

        @Operation(
        summary = "Busca registro completo de asistencia",
        description = "Conbina informacion de los microservicios empleados y turnos para generar registro completo de asistencia."
    )
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Registro de asistencia buscado con exito"),
        @ApiResponse(responseCode = "404", description = "Los IDs no existe"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })


    @GetMapping("/registro/empleado/{empleadoId}/turno/{turnoId}")
    public ResponseEntity<?> registroCompleto(@PathVariable Integer empleadoId, @PathVariable Integer turnoId) {
        AsistenciaCompletaDTO registroCompleto = service.obtenerAsistenciaCompleta(empleadoId, turnoId);
        return ResponseEntity.ok().body(registroCompleto);
    }

}
