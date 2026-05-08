package com.empleados.turnos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empleados.turnos.model.Turnos;

@Repository
public interface TurnoRepository extends JpaRepository<Turnos, Integer> {
    
    Optional<Turnos> findByEmpleadoId(Integer empleadoId);
}
