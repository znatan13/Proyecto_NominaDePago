package com.noti.notificacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noti.notificacion.modelo.Notificacion;

@Repository
public interface NotiRepository extends JpaRepository<Notificacion, Integer>{
    Optional<Notificacion> findByEmpleadoId(Integer empleadoId);
}
