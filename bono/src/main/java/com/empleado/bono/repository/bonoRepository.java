package com.empleado.bono.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empleado.bono.model.Bono;
import java.util.List;


@Repository
public interface bonoRepository extends JpaRepository<Bono, Integer> {

    List<Bono> findByEmpleadoId(Integer empleadoId);

}
