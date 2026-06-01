package com.nomina.nominaPago.repository;

import com.nomina.nominaPago.model.Nomina;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NominaRepository extends JpaRepository<Nomina, Integer>{
Optional<Nomina> findByNomEmpleadoId(Integer nomEmpleadoId);
    
}
