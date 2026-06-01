package com.nomina.nominaPago.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Nomina")
@AllArgsConstructor
@NoArgsConstructor
public class Nomina {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nominaId;

    @Column(nullable = false)
    private Integer nomEmpleadoId;

    private Integer bonoId;

    @Column(nullable = false)
    private Double sueldoLiquido;
}
