package com.noti.notificacion.modelo;

import java.time.LocalDate;

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
@Table(name = "Notificacion")
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificacionId;
    @Column(nullable = false)
    private Integer empleadoId;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String mensaje;
    @Column(nullable = false)
    private LocalDate fecha;

}
