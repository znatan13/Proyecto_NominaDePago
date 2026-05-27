package com.nomina.nominaPago.repository;

import com.nomina.nominaPago.model.Empleado;

import java.util.List;

import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.stereotype.Repository;

@Repository
public class NominaRepository{
    // arreglar (no me acuerdo los repos aaaa)
    List<Empleado> findbyEmpleadoid(Integer empleadoId);
}
