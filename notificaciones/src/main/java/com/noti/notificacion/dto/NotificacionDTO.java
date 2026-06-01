package com.noti.notificacion.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class NotificacionDTO {
    private Integer empleadoId;
    private String titulo;
    private String mensaje;
    private LocalDate fecha;
}
