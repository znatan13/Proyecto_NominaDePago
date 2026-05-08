package com.empleados.turnos.model;

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
@Data
@Table(name = "turnos")
@NoArgsConstructor
@AllArgsConstructor

public class Turnos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer turnoId;

    @Positive(message = "El id del empleado debe ser mayor a 0")
    @Column(nullable = false)
    private Integer empleadoId;
    // no es unico porque el mismo empleado puede tener muchos turnos

    @NotBlank(message = "La fecha no puede estar vacia")
    @Column(nullable = false, length = 15)
    private String fecha;

    @NotBlank(message = "La hora de inicio del empleado no puede estar vacia")
    @Column(nullable = false, length = 15)
    private String horaInicio;

    @NotBlank(message = "La hora de fin del empleado no puede estar vacia")
    @Column(nullable = false, length = 15)
    private String horaFin;

    @NotBlank(message = "El estado del empleado no puede estar vacio: elija activo/inactivo")
    @Column(nullable = false, length = 20)
    private String estado;

}
