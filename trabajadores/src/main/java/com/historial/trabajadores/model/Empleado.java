package com.historial.trabajadores.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "empleados")
@NoArgsConstructor
@AllArgsConstructor

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empleadoId;

    @NotBlank(message = "El rut del empleado no puede estar vacio")
    @Column(nullable = false, unique = true)
    private String rut;

    @NotBlank(message = "El nombre del empleado no puede estar vacio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido del empleado no puede estar vacio")
    private String apellido;

    @Email(message = "El correo electronico debe ser uno valido")
    @NotBlank(message = "El correo electronico no puede estar vacio")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "El telefono del empleado no puede estar vacio")
    @Column(nullable = false, unique = true)
    private String numeroTelefono;

    @NotBlank(message = "La direccion del empleado no puede estar vacia")
    @Column(nullable = false)
    private String direccion;

    @NotBlank(message = "Debe registrar casado/soltero")
    @Column(nullable = false)
    private String estadoCivil;

    @NotNull(message = "La fecha de contrato del empleado no puede ser nula")
    @Column(nullable = false)
    private LocalDate fechaContrato;
    @Column(nullable = true)
    private LocalDate fechaDeBaja;

    @NotBlank(message = "El cargo del empleado no puede estar vacio")
    @Column(nullable = false)
    private String cargo;

    @NotBlank(message = "El estado del empleado debe estar activo/inactivo")
    @Column(nullable = false)
    private String estado;

    @Positive(message = "El sueldo base del empleado debe ser un numero positivo")
    @Column(nullable = false)
    private double sueldoBase;

    @NotBlank(message = "El afp del empleado no puede estar Vacio")
    @Column(nullable = false)
    private String afp;

}
