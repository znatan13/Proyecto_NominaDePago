package com.empleado.bono.model;

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
    private Integer id;

    @Positive(message = "El id del empleado debe ser mayor a 0")
    @Column(nullable = false)
    private Integer empleadoid;

    @NotBlank(message = "El nombre del empleado es obligatorio")
    private String nombreEmpleado;

    @NotBlank(message = "El tipo de bono es obligatorio")
    private String tipoBono;

    @NotNull(message = "El bono debe ser mayor a 0")
    private int bonoEmpleado;






}
