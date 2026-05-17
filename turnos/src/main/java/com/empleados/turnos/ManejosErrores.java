package com.empleados.turnos;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.empleados.turnos.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ManejosErrores {

    // Cuando falla el @Valid en un request
    @ExceptionHandler(MethodArgumentNotValidException.class)

    // Se ejecuta automaticamente cuando ocurre una exception de la validacacion
    public ResponseEntity<ErrorDTO> manejarErroresValidacion(

            MethodArgumentNotValidException ex, // Detalles de los errore de validicion
            HttpServletRequest request) { // informacion del request URL

        // Mapa donde se almacenaran los errores
        Map<String, String> errores = new HashMap<>();

        // Se recorre todos los errores de validacion detectado por el spring
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de datos, verifique bien",
                errores, // Detalle de error del campo
                request.getRequestURI()); // Url donde ocurrio el error
        return ResponseEntity.badRequest().body(errorDTO);
    }

    //En este microservicio no existen duplicados en la base de datos.
    //De todas formas se hace la excepcion para buenas practicas.
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

            //Creamo el JSON con el error de get y delete
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
                    400, // error del cliente 
                    ex.getMessage(),
                    null,
                    request.getRequestURI());
                return ResponseEntity.status(400).body(errorDTO);
        }
}
