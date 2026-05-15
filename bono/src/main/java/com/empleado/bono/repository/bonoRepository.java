package com.empleado.bono.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empleado.bono.model.Bono;

@Repository
public interface BonoRepository extends JpaRepository<Bono, Integer> {

}
