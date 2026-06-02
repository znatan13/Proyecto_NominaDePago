package com.empleados.turnos.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empleados.turnos.model.Turnos;
import java.util.List;


@Repository
public interface TurnoRepository extends JpaRepository<Turnos, Integer> {
    
    List<Turnos> findByEmpleadoId(Integer empleadoId);
}
