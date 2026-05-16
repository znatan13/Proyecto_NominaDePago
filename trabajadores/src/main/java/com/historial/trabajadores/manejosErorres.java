package com.historial.trabajadores;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.historial.trabajadores.dto.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice // se encargara todos los errores del controlador
public class manejosErorres {

        //manejar errores de validacion.
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorDTO> manejarValidaciones(
            MethodArgumentNotValidException ex,
            HttpServletRequest request){

            Map<String, String> errores = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage())); 
            
            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Errores de validaciones, intente de nuevo",
                errores,
                request.getRequestURI()
            );
            return ResponseEntity.badRequest().body(errorDTO);
        }

        // Manejar errores como datos duplicados etc.
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorDTO> manejarErrorBaseDatos(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

                ErrorDTO errorDTO = new ErrorDTO(
                    LocalDateTime.now(),
                    400, // El codigo de error 
                    "Error de integridad de datos, intente de nuevo",
                    null,
                    request.getRequestURI()
                );
                return ResponseEntity.badRequest().body(errorDTO);

            }

        // manejar errores como empleado no econtrado. etc
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ErrorDTO> manejarErrores(
            RuntimeException ex,
            HttpServletRequest request){

                ErrorDTO errorDTO = new ErrorDTO(
                    LocalDateTime.now(),
                    404, // El codigo de error datos no encontados
                    ex.getMessage(), // el mensaje de error que lanzara
                    null, // no hay errores de validacion, por eso es null
                    request.getRequestURI());
                    return ResponseEntity.status(404).body(errorDTO);
            }
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorDTO> manejarArgumentosInvalidos(
            IllegalArgumentException ex,
            HttpServletRequest request){

                ErrorDTO errorDTO = new ErrorDTO(
                    LocalDateTime.now(),
                    400, // El error 400 cliente envia datos invalidos
                    ex.getMessage(),
                    null,
                    request.getRequestURI());
                return ResponseEntity.status(400).body(errorDTO);
        }
}

