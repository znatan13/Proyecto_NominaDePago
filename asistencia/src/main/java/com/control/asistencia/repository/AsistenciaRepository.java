package com.control.asistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.control.asistencia.model.Asistencia;
import java.util.List;


@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {
    List<Asistencia> findByEmpleadoIdAndTurnoId(Integer empleadoId, Integer turnoId);

}
