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
public class AsistenciaCompletaDTO { //dto para hacer conexion entre los microservicios
    // datos basicos del empleado
    private String nombre;
    private String apellido;
    private String email;
    private String cargo;
    //datos basicos de turno
    private LocalDate fecha;
    private String horaInicio;
    private String horaFin;
    //datos del registro asistencia e hacemos lista con la bd local
    private List<Asistencia> asistencia;

}
