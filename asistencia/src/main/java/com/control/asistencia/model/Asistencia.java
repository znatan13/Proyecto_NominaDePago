package com.control.asistencia.model;

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
@Table (name = "asistencia")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asistencia {

    @Id
    //genera atomaticamente un valor al id de asistencia
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer asistenciaId;

    @Positive(message = "El id del empleado debe ser mayor a 0")
    @Column(nullable = false)
    private Integer empleadoId;

    @Positive(message = "El id del turno debe ser mayor a 0")
    @Column(nullable = false)
    private Integer turnoId;

    @NotNull(message = "La fecha del registro de asistencia no puede ser nula")
    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @Column(nullable = true, length = 200) // es opcional
    private String observaciones; 

    @NotBlank(message = "El estado del empleado es obligatorio para su asistencia")
    @Column(nullable = false, length = 100)
    private String estadoAsistencia;

}
