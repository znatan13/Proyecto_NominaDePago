package com.control.asistencia.dto;

import java.time.LocalDate;
import java.util.List;

import com.control.asistencia.model.Asistencia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsistenciaCompletaDTO { 
    private String nombre;
    private String apellido;
    private String email;
    private String cargo;
    private LocalDate fecha;
    private String horaInicio;
    private String horaFin;
    private List<Asistencia> asistencia;

}
