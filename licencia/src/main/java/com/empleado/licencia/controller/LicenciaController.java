package com.empleado.licencia.controller;

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

import com.empleado.licencia.dto.LicenciaEmpleado;
import com.empleado.licencia.model.Licencia;
import com.empleado.licencia.service.LicenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/licencias")
public class LicenciaController {

    private LicenciaService service;

    public LicenciaController(LicenciaService service){
        this.service = service;
    }
    @Operation(
        summary = "Listar Licencias",
        description = "Obtiene una lista con todas las licencias registrados en el sistema"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos Invalidos"),
        @ApiResponse(responseCode = "500", description = "Error Interno del servidor")
})
    @GetMapping("/listar")
    public ResponseEntity<List<Licencia>> listar() {
        return ResponseEntity.ok().body(service.listarLicencias());
    }

    @Operation(
        summary = "Agregar Licencias",
        description = "Opcion para poder agregar una licencia al sistema"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Licencia Agregada Correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos Invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @PostMapping("/crear")
    public ResponseEntity<?> crearLicencia(@Valid @RequestBody Licencia licenciaNueva) {
        Licencia licenciaCreado = service.guardar(licenciaNueva);
        return ResponseEntity.status(201).body(licenciaCreado);
    }

    @Operation(
        summary = "Actualizar Licencias por ID",
        description = "Opcion para Actualizar una licencia en el sistema por id"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Licencia modificada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "No existe datos para modificar"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @PutMapping("/actualizar/{idLicencia}")
    public ResponseEntity<?> actualizarLicencia(@PathVariable Integer idLicencia, @RequestBody Licencia licenciaNueva) {
        Licencia licenciaActualizar = service.actualizarLicencia(idLicencia, licenciaNueva);
        return ResponseEntity.ok().body(licenciaActualizar);
    }

    @Operation(
        summary = "Buscar licencias por ID",
        description = "Opcion para buscar informacion de una licencia a traves de su ID")

@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Licencia buscada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "No existe datos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @GetMapping("/buscar/licencia/{idLicencia}")
    public ResponseEntity<?> buscarLicencia(@PathVariable Integer idLicencia) {
        Licencia buscar = service.buscarLicencia(idLicencia);
        return ResponseEntity.ok().body(buscar);
    }

    @Operation(
        summary = "Buscar Empleado por su ID",
        description = "Opcion para buscar el ID de empleado"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busqueda exitosa"),
        @ApiResponse(responseCode = "404", description = "No existen datos"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @GetMapping("/buscar/empleado/{empleadoId}")    
    public ResponseEntity<?> buscarEmpleadoId(@PathVariable Integer empleadoId) {
        List<Licencia> buscar = service.buscarIdEmpleado(empleadoId);
        return ResponseEntity.ok().body(buscar);
    }

    @Operation(
        summary = "Buscar la licencia con informacion de un empleado",
        description = "Busca una licencia conbinada con informacion del microservicio de empleados por id"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se a encontrado informacion correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "No existe datos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @GetMapping("/buscar/empleadoLicencia/{empleadoId}")
    public ResponseEntity<?> buscarLicenciaEmpleados(@PathVariable Integer empleadoId) {
        LicenciaEmpleado licenciaEmpleado = service.licenciaEmpleado(empleadoId);
        return ResponseEntity.ok().body(licenciaEmpleado);
    }
    
    @Operation(
        summary = "Eliminar licencia por ID",
        description = "Se elimina una licencia del sistema por id"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se a eliminado la licencia del sistema con exito"),
        @ApiResponse(responseCode = "404", description = "No existen datos"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @DeleteMapping("/eliminar/{idLicencia}")
    public ResponseEntity<?> eliminarLicencia (@PathVariable Integer idLicencia) {
        service.eliminarLicencia(idLicencia);
        return ResponseEntity.ok().body("La licencia a sido eliminado");

    }


}
