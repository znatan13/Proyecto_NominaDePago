package com.empleados.turnos;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.empleados.turnos.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ManejosErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ErrorDTO> manejarErroresValidacion(

            MethodArgumentNotValidException ex, 
            HttpServletRequest request) {
        
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de datos, verifique bien",
                errores,
                request.getRequestURI()); 
        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErroresBaseDatos(

            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de integridad de datos, intentelo nuevamente",
                null,
                request.getRequestURI());
        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejosErrores(
         RuntimeException ex,
         HttpServletRequest request){

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                ex.getMessage(),
                null,
                request.getRequestURI());
            return ResponseEntity.status(404).body(errorDTO);
         }
     @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorDTO> manejarArgumentosInvalidos(
            IllegalArgumentException ex,
            HttpServletRequest request){

                ErrorDTO errorDTO = new ErrorDTO(
                    LocalDateTime.now(),
                    400,
                    ex.getMessage(),
                    null,
                    request.getRequestURI());
                return ResponseEntity.status(400).body(errorDTO);
        }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDTO> manejosErorroresMicroservicios(
        ResponseStatusException exception,
        HttpServletRequest request){

            int status = exception.getStatusCode().value();

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                status,
                exception.getReason(),
                null,
                request.getRequestURI()
            );
        return ResponseEntity.status(status).body(errorDTO);
    }
}
