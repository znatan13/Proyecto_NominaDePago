package com.nomina.nominaPago.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomina.nominaPago.dto.NominaSimple;
import com.nomina.nominaPago.model.Nomina;
import com.nomina.nominaPago.service.NominaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/nomina")
public class NominaController {
    
    private NominaService service;

    public NominaController(NominaService service){
        this.service = service;
    }

    @Operation(
        summary = "Listado general de nominas",
        description = "Obtiene de forma ordenada las nominas creadas hasta la fecha"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listas obtenidas sin problema"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")

    })
    @GetMapping("/listar")
    public ResponseEntity<List<Nomina>> listar() {
        return ResponseEntity.ok().body(service.listar());
    }
    
        @Operation(
        summary = "Creacion de Nomina en usuario.",
        description = "Creacion de Nomina dentro del sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Creacion de Nomina existosa."),
        @ApiResponse(responseCode = "400", description = "Datos invalidos."),
        @ApiResponse(responseCode = "500", description = "Error del sistema.")

    })
    @PostMapping("/crear")
    public ResponseEntity<?> crearNomina (@Valid @RequestBody Nomina nomina){
        Nomina nominaCreada = service.crearNomina(nomina);
        return ResponseEntity.status(201).body(nominaCreada);
    }
        @Operation(
        summary = "Busqueda de Nomina segun empleado.",
        description = "Se busca una nomina en base al ID del empleado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listas obtenidas sin problema."),
        @ApiResponse(responseCode = "404", description = "No hay existencias del empleado."),
        @ApiResponse(responseCode = "400", description = "Datos invalidos."),
        @ApiResponse(responseCode = "500", description = "Error del sistema.")

    })
    @GetMapping("/{empleadoId}")
    public ResponseEntity<?> nominaUsuario(@PathVariable Integer empleadoId) {
        NominaSimple nomina = service.nominaDTO(empleadoId);
        return ResponseEntity.ok().body(nomina);
    }
        @Operation(
        summary = "Busqueda de Nomina segun ID.",
        description = "Se busca una nomina registrada en el sistema por ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listas obtenidas sin problema."),
        @ApiResponse(responseCode = "404", description = "No se encuentra el ID."),
        @ApiResponse(responseCode = "400", description = "Datos invalidos."),
        @ApiResponse(responseCode = "500", description = "Error del sistema.")

    })
    @GetMapping("/buscar/nomina/{nominaId}")
    public ResponseEntity<?> buscarNominaId (@PathVariable Integer nominaId) {
        Nomina nominaBuscada = service.buscarNomina(nominaId);
        return ResponseEntity.ok().body(nominaBuscada);
    }
        @Operation(
        summary = "Busqueda de Nomina segun ID.",
        description = "Se busca una nomina registrada en el sistema por ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listas obtenidas sin problema."),
        @ApiResponse(responseCode = "404", description = "No se encuentra el ID."),
        @ApiResponse(responseCode = "400", description = "Datos invalidos."),
        @ApiResponse(responseCode = "500", description = "Error del sistema.")
    })
    @GetMapping("/buscar/empleado/{nomEmpleadoId}")
    public ResponseEntity<?> buscarEmpleadoId (@PathVariable Integer nomEmpleadoId) {
        Nomina buscarEmpleado = service.buscarEmpleado(nomEmpleadoId);
        return ResponseEntity.ok().body(buscarEmpleado);
    }
        @Operation(
        summary = "Modificacion de Nomina.",
        description = "Se modifica la Nomina en base al ID de la misma en el sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nomina modificada exitosamente."),
        @ApiResponse(responseCode = "404", description = "No se encuentra el ID."),
        @ApiResponse(responseCode = "400", description = "Datos invalidos."),
        @ApiResponse(responseCode = "500", description = "Error del sistema.")
    })
    @PutMapping("/actualizar/{nominaId}")
    public ResponseEntity<?> actualizarNomina(@PathVariable Integer nominaId,@Valid @RequestBody Nomina nominaActualizada){
        Nomina actualizarNomina = service.actualizarNomina(nominaId, nominaActualizada);
        return ResponseEntity.ok().body(actualizarNomina);
    }
            @Operation(
        summary = "Eliminacion de Nomina.",
        description = "Se elimina una nomina en base al ID dentro del sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se elimino exitosamente."),
        @ApiResponse(responseCode = "404", description = "No se encuentra el ID."),
        @ApiResponse(responseCode = "400", description = "Datos invalidos."),
        @ApiResponse(responseCode = "500", description = "Error del sistema.")
    })
    @DeleteMapping("/eliminar/{nominaId}")
    public ResponseEntity<?> eliminarNomina (@PathVariable Integer nominaId){
        service.elimimarNomina(nominaId);
        return ResponseEntity.ok().body("Nomina eliminada");
    }
    
    


    
}
