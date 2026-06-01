package com.empleado.licencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empleado.licencia.model.Licencia;


@Repository
public interface LicenciarRespository extends JpaRepository<Licencia, Integer> {

    List <Licencia> findByEmpleadoid(Integer empleadoid);
    boolean existsByempleadoidAndEstado(Integer empleadoId, String estado);

}
