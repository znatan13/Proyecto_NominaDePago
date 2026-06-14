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
    @GetMapping()
    public ResponseEntity<List<Licencia>> listar() {
        return ResponseEntity.ok().body(service.listarLicencias());
    }

    @Operation(
        summary = "Agregar Licencias",
        description = "Opcion para poder agregar una licencia al empleado"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Licencia Agregado Correctamente"),
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
        description = "Opcion para Actualizar una licencia al empleado por id"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modificacion de Empleado Correctamente"),
        @ApiResponse(responseCode = "400", description = "No existe el ID para modificarlo"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @PutMapping("/actualizar/{idLicencia}")
    public ResponseEntity<?> actualizarLicencia(@PathVariable Integer idLicencia, @RequestBody Licencia licenciaNueva) {
        Licencia licenciaActualizar = service.actualizarLicencia(idLicencia, licenciaNueva);
        return ResponseEntity.ok().body(licenciaActualizar);
    }

    @Operation(
        summary = "Buscar licencias por ID",
        description = "Opcion para buscar la licencia de un empleado por ID"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se a encontrado el ID del emplado"),
        @ApiResponse(responseCode = "400", description = "ID del empleado no existe "),
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
        @ApiResponse(responseCode = "200", description = "Se a encontrado el ID del empleado"),
        @ApiResponse(responseCode = "400", description = "ID del empleado no encontrado o no existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @GetMapping("/buscar/empleado/{empleadoId}")    
    public ResponseEntity<?> buscarEmpleadoId(@PathVariable Integer empleadoId) {
        List<Licencia> buscar = service.buscarIdEmpleado(empleadoId);
        return ResponseEntity.ok().body(buscar);
    }

    @Operation(
        summary = "Buscar la licencia por el ID de empleado",
        description = "Opcion de buscar su licencia con el id del empleado"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se a encontrado el ID del empleado con su licencia"),
        @ApiResponse(responseCode = "400", description = "ID de empleado no encontrado o no existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @GetMapping("/buscar/empleadoLicencia/{empleadoId}")
    public ResponseEntity<?> buscarLicenciaEmpleados(@PathVariable Integer empleadoId) {
        LicenciaEmpleado licenciaEmpleado = service.licenciaEmpleado(empleadoId);
        return ResponseEntity.ok().body(licenciaEmpleado);
    }
    
    @Operation(
        summary = "Eliminar Licencia por ID",
        description = "Opcion para eliminar la licencia a un empleado por id"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se a eliminado la licencia del empleado"),
        @ApiResponse(responseCode = "400", description = "ID de empleado no encontrado o no existe"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    @DeleteMapping("/eliminar/{idLicencia}")
    public ResponseEntity<?> eliminarLicencia (@PathVariable Integer idLicencia) {
        service.eliminarLicencia(idLicencia);
        return ResponseEntity.ok().body("La licencia a sido eliminado");

    }


}
