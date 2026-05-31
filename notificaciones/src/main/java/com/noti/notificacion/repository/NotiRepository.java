package com.noti.notificacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noti.notificacion.modelo.Notificacion;

@Repository
public interface NotiRepository extends JpaRepository<Notificacion, Integer>{
    List<Notificacion> findByEmpleadoId(Integer empleadoId);
}
