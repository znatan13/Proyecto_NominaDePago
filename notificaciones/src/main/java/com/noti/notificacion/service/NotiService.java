package com.noti.notificacion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.noti.notificacion.modelo.Notificacion;
import com.noti.notificacion.repository.NotiRepository;

@Service
public class NotiService {
    
private final NotiRepository repository;
public NotiService(NotiRepository repository){
    this.repository = repository;
}

public Notificacion noti(Notificacion notificacion){
    return repository.save(notificacion);
}

public List<Notificacion> buscar(Integer empleadoId){
     return repository.findByEmpleadoId(empleadoId);   
    }
    
}
