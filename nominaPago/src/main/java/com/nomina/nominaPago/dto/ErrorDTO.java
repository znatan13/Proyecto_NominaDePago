package com.nomina.nominaPago.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDTO {
    private LocalDateTime timestamp;
    private Integer status;
    private String mensaje;
    private String path;
    private Map<String, String> errores;

    public ErrorDTO(LocalDateTime timestamp, Integer status, String mensaje, String path,Map<String,String> errores){
        this.timestamp = timestamp;
        this.status = status;
        this.mensaje = mensaje;
        this.path = path;
        this.errores = errores;
    }
}
