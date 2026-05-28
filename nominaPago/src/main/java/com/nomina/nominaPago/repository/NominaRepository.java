package com.nomina.nominaPago.repository;

import com.nomina.nominaPago.model.Nomina;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NominaRepository extends JpaRepository<Nomina, Integer>{
    //List<Empleado> findbyEmpleadoid(Integer empleadoId);
}
