package com.noti.notificacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noti.notificacion.modelo.Notificacion;
import com.noti.notificacion.service.NotiService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    @Autowired
    private NotiService service;
    
    @PostMapping
    public ResponseEntity<?> crearNotificacion(@RequestBody Notificacion notificacion){
        Notificacion notificacionCreada = service.crearNotificacion(notificacion);
        return ResponseEntity.ok().body(notificacionCreada);
    }

    @PutMapping("/actualizar/{idNoti}")
    public ResponseEntity<?> actualizarNotificacion(@PathVariable Integer idNoti, @Valid @RequestBody Notificacion notificacion){
        Notificacion notificacionActualizada = service.actualizarNotificacion(idNoti, notificacion);
        return ResponseEntity.ok().body(notificacionActualizada);
    }

    @GetMapping("/empleado/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id){
        Notificacion notificacion = service.buscarnNotificacion(id);
        return ResponseEntity.ok().body(notificacion);
    }

    @GetMapping("/general")
        public ResponseEntity<List<Notificacion>> mostrarGeneral(){
            return ResponseEntity.ok().body(service.verNotificaciones());
        }
}
