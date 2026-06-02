package com.noti.notificacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.noti.notificacion.model.Notificacion;
import com.noti.notificacion.repository.NotiRepository;

@Service
public class NotiService {
    
private final NotiRepository repository;
public NotiService(NotiRepository repository){
    this.repository = repository;
}

public Notificacion crearNotificacion(Notificacion notificacion){
    return repository.save(notificacion);
}

public List<Notificacion> verNotificaciones(){ 
    return repository.findAll();
    }

public Notificacion actualizarNotificacion(Integer notificacionId, Notificacion actualizado){
        if(notificacionId == null){
        throw new IllegalArgumentException("La notificacion no existe");
    } 
    Optional<Notificacion> notificacionBuscar = repository.findById(notificacionId);
            if(notificacionBuscar.isEmpty()){
        throw new IllegalArgumentException("No hay Notificaciones con el Id: "+notificacionId);
    } 
    Notificacion notificacion = notificacionBuscar.get();
    
    notificacion.setTitulo(actualizado.getTitulo());
    notificacion.setMensaje(actualizado.getMensaje());

    return repository.save(notificacion);
}

public void eliminarNotificacion(Integer notificacionId){
    if(notificacionId == null){
        throw new IllegalArgumentException("La notificacion no existe");
    } 
    if(repository.existsById(notificacionId)){
        repository.deleteById(notificacionId);
    }
    else{
        throw new RuntimeException("La notificacion de Id: "+notificacionId+". No existe.");
    }
}

    
}
