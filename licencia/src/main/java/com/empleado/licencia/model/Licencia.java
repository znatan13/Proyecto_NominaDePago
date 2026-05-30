package com.empleado.licencia.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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

    @Positive(message= "El id del empleado debe ser mayor a 0")
    @Column(nullable = false)
    private Integer empleadoid;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

   
    @Column(nullable = false, length = 30)
    private String estado;

    @NotBlank(message = "El motivo de la licencia debe ser obligatorio")
    @Column(nullable = false, length = 300)
    private String motivoLicencia;

}
