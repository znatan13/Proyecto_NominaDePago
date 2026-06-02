package com.auth.autenticacion;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth.autenticacion.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice

public class ManejoErrores {

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
                "Error de validacion",
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
                "Email ya existe o nombre de usuario, vuelva intentarlo",
                null,
                request.getRequestURI());
        return ResponseEntity.badRequest().body(errorDTO);

    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarErrores(
            RuntimeException ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                ex.getMessage(), 
                null,
                request.getRequestURI());
        return ResponseEntity.status(404).body(errorDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> manejarErroresDatosErroneos(
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
}
