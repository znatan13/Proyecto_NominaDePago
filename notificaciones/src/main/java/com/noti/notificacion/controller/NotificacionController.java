package com.noti.notificacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noti.notificacion.modelo.Notificacion;
import com.noti.notificacion.service.NotiService;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    @Autowired
    private NotiService service;
    
    @PostMapping
    public Notificacion notificar(@RequestBody Notificacion notificacion){
        return service.noti(notificacion);
    }

    @GetMapping("/empleado/{id}")
    public List<Notificacion> buscar(@PathVariable Integer id){
        return service.buscar(id);
    }
}
