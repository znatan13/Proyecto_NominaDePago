package com.noti.notificacion.controller;

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

import com.noti.notificacion.model.Notificacion;
import com.noti.notificacion.service.NotiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    @Autowired
    private NotiService service;
    
    @Operation(
        summary = "Creacion de notificacion",
        description = "Crea una notificacion que se guarda en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificacion creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")

    })
    @PostMapping("/crear")
    public ResponseEntity<?> crearNotificacion(@RequestBody Notificacion notificacion){
        Notificacion notificacionCreada = service.crearNotificacion(notificacion);
        return ResponseEntity.ok().body(notificacionCreada);
    }
    @Operation(
        summary = "Actualizacion de notificaciones",
        description = "Modifica la notificacion en base a su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificacion modificada con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")

    })
    @PutMapping("/actualizar/{idNoti}")
    public ResponseEntity<?> actualizarNotificacion(@PathVariable Integer idNoti, @Valid @RequestBody Notificacion notificacion){
        Notificacion notificacionActualizada = service.actualizarNotificacion(idNoti, notificacion);
        return ResponseEntity.ok().body(notificacionActualizada);
    }
    @Operation(
        summary = "Listado general de notificaciones",
        description = "Obtiene de forma ordenada las notificaciones creadas hasta la fecha"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listas obtenidas sin problema"),
        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")

    })
    @GetMapping("/general")
    public ResponseEntity<List<Notificacion>> mostrarGeneral(){
        return ResponseEntity.ok().body(service.verNotificaciones());
    }
    @Operation(
        summary = "Listado general de notificaciones",
        description = "Obtiene de forma ordenada las notificaciones creadas hasta la fecha"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listas obtenidas sin problema"),
        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
    })
    @DeleteMapping("/eliminar/{notificacionId}")
    public ResponseEntity<?> eliminar(@PathVariable Integer notificacionId){
        service.eliminarNotificacion(notificacionId);
        return ResponseEntity.ok().body("se elimino con exito");
    }

}
