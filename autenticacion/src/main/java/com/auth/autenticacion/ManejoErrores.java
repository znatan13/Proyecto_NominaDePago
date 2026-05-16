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
// Para capturar erroress globales de este backend ademas evitar try catch
// controller, para no sobrecargar el controlador

public class ManejoErrores {

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

        // Este metodo se usara en post y put mostrara que fue el error y donde
        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400, 
                "Error de validacion",
                errores, // Detalle de error del campo
                request.getRequestURI()); // Url donde ocurrio el error
        return ResponseEntity.badRequest().body(errorDTO);
    }

    // Manejara los errores del base de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> manejarErroresBaseDatos(

            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400, // error de validacion de datos
                "Email ya existe o nombre de usuario, vuelva intentarlo",
                null,
                request.getRequestURI());
        return ResponseEntity.badRequest().body(errorDTO);
        /*
         * confirmamos el metodo para ejecutarse cuando detecte un error
         */
    }

    /*
     * Opte por usar Throw runtimeException para manjear error de id no existentes.
     * y dando mensaje respuesta JSON al cliente
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarErrores(
            RuntimeException ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                ex.getMessage(), //Se mostraran los mensajes respondiente al caso
                null,
                request.getRequestURI());
        // 404 -> recurso no encotrado
        return ResponseEntity.status(404).body(errorDTO);
    }

    //Manejara los errores de datos erroneos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> manejarErroresDatosErroneos(
            IllegalArgumentException ex,
            HttpServletRequest request){

            ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400, // 400 -> Error ejemplo id nulo o email nulo
                ex.getMessage(),
                null,
                request.getRequestURI());
            return ResponseEntity.status(400).body(errorDTO);
        }
}
