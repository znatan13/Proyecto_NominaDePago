package com.empleado.bono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.empleado.bono.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ManejosErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejosErroresGlobal (
        MethodArgumentNotValidException ex,
        HttpServletRequest request ){

            Map<String, String> error = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(errores -> error.put(errores.getField()
            , errores.getDefaultMessage()));

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de validaciones, intente de nuevo",
                error,
                request.getRequestURI()
            );
            return ResponseEntity.badRequest().body(errorDTO);
        }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejosErroresBd(
        DataIntegrityViolationException ex,
        HttpServletRequest request){

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de integridad de datos, intente de nuevo",
                null,
                request.getRequestURI()
            );
            return ResponseEntity.badRequest().body(errorDTO);
        }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarExistenciaDatos(
        RuntimeException ex,
        HttpServletRequest request){

        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            404,
            ex.getMessage(),
            null,
            request.getRequestURI()
        );
        return ResponseEntity.status(404).body(errorDTO);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> manejarDatosCorruptos(
        IllegalArgumentException ex,
        HttpServletRequest request){

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                ex.getMessage(),
                null,
                request.getRequestURI()
            );
            return ResponseEntity.status(400).body(errorDTO);
        }
        
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDTO> validarErroresMicroservicios(
        ResponseStatusException ex,
        HttpServletRequest request){
       
            int status = ex.getStatusCode().value();

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                status,
                ex.getReason(),
                null,
                request.getRequestURI()
            );
            return ResponseEntity.status(status).body(errorDTO);
        }
}
