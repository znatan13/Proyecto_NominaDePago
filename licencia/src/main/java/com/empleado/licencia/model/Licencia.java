package com.empleado.licencia.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "licencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Licencia {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idLicencia;

    @Positive(message= "El id del empleado debe ser major a 0")
    @Column(nullable = false)
    private Integer empleadoid;

    @NotNull(message= "La fecha de creacion no debe que estar nula")
    private LocalDate fechaCreacion;

    @NotNull(message= "La fecha de vencimiento no de que estar nula")
    private LocalDate fechaVencimiento;

    @NotNull(message= "El estado no debe que estar nulo")
    private String estado;

}
