package com.empleado.licencia.model;

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
@Table(name = "licencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Licencia {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Positive(message= "El id del empleado debe ser major a 0")
    @Column(nullable = false)
    private Integer empleadoid;

    @NotBlank(message= "El nombre del empleado es obligatorio")
    private String nombreEmpleado;

    @NotBlank(message= "El tipo de licencia es obligatorio")
    private String tipoLicencia;

    @NotNull(message= "La fecha de inicio es obligatorio")
    private LocalDate fechaInicio;

    @NotNull(message= "La fecha de termino es obligatoria")
    private LocalDate fechaTermino;

    @NotBlank(message= "El estado es obligatorio")
    private String estado;

}
