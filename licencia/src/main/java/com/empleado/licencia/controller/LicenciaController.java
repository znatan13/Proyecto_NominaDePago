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

import com.empleado.licencia.service.LicenciaService;
import com.empleado.licencia.dto.LicenciaEmpleado;
import com.empleado.licencia.model.Licencia;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/licencias")
public class LicenciaController {

    private LicenciaService service;

    public LicenciaController(LicenciaService service){
        this.service = service;
    }
    @GetMapping()
    public ResponseEntity<List<Licencia>> listar() {
        return ResponseEntity.ok().body(service.listarLicencias());
    }
    @PostMapping("/crear")
    public ResponseEntity<?> crearLicencia(@Valid @RequestBody Licencia licenciaNueva) {
        Licencia licenciaCreado = service.guardar(licenciaNueva);
        return ResponseEntity.status(201).body(licenciaCreado);
    }

    @PutMapping("/actualizar/{idLicencia}")
    public ResponseEntity<?> actualizarLicencia(@PathVariable Integer idLicencia, @RequestBody Licencia licenciaNueva) {
        Licencia licenciaActualizar = service.actualizarLicencia(idLicencia, licenciaNueva);
        return ResponseEntity.ok().body(licenciaActualizar);
    }
    @GetMapping("/buscar/licencia/{idLicencia}")
    public ResponseEntity<?> buscarLicencia(@PathVariable Integer idLicencia) {
        Licencia buscar = service.buscarLicencia(idLicencia);
        return ResponseEntity.ok().body(buscar);
    }
    @GetMapping("/buscar/empleado/{empleadoId}")    
    public ResponseEntity<?> buscarEmpleadoId(@PathVariable Integer empleadoId) {
        List<Licencia> buscar = service.buscarIdEmpleado(empleadoId);
        return ResponseEntity.ok().body(buscar);
    }

    @GetMapping("/buscar/empleadoLicencia/{empleadoId}")
    public ResponseEntity<?> buscarLicenciaEmpleados(@PathVariable Integer empleadoId) {
        LicenciaEmpleado licenciaEmpleado = service.licenciaEmpleado(empleadoId);
        return ResponseEntity.ok().body(licenciaEmpleado);
    }
    
    @DeleteMapping("/eliminar/{idLicencia}")
    public ResponseEntity<?> eliminarLicencia (@PathVariable Integer idLicencia) {
        service.eliminarLicencia(idLicencia);
        return ResponseEntity.ok().body("La licencia a sido eliminado");

    }


}
