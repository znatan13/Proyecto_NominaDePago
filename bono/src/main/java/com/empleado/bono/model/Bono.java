package com.empleado.bono.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bonos")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Bono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bonoid;

    @Positive(message = "El id del empleado debe ser positivo")
    @Column(nullable = false)
    private Integer empleadoId;

    @NotBlank(message = "El tipo de bono es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombreBono;

    @NotNull(message = "Debe estar registrada la fecha de entrega del bono")
    @Column(nullable = false)
    private LocalDate fechaEntrega;

    @Positive(message = "El bono debe ser mayor a 0")
    @Column(nullable = false)
    private Float bonoEmpleado;

    @Positive(message = "El porcentaje de bono que tendra debe ser positivo")
    @Column(nullable = false)
    private Float porcentajeBono;

    @NotBlank(message = "La descripcion del bono debe ser obligatorio")
    @Column(nullable = false, length = 250)
    private String descripcion;

}
