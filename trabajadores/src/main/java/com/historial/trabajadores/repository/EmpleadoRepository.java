package com.historial.trabajadores.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.historial.trabajadores.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    Optional<Empleado> findByRut(String rut);
    Optional<Empleado> findByEmailIgnoreCase(String email);
    Optional<Empleado> findByNumeroTelefono(String numeroTelefono);

}
