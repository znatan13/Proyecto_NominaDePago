package com.nomina.nominaPago.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomina.nominaPago.dto.NominaSimple;
import com.nomina.nominaPago.model.Nomina;
import com.nomina.nominaPago.service.NominaService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/nomina")
public class NominaController {
    
    private NominaService service;

    public NominaController(NominaService service){
        this.service = service;
    }
    @GetMapping()
    public ResponseEntity<List<Nomina>> listar() {
        return ResponseEntity.ok().body(service.listar());
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearNomina (@Valid @RequestBody Nomina nomina){
        Nomina nominaCreada = service.crearNomina(nomina);
        return ResponseEntity.status(201).body(nominaCreada);
    }

    @GetMapping("/{empleadoId}")
    public ResponseEntity<?> nominaUsuario(@PathVariable Integer empleadoId) {
        NominaSimple nomina = service.nominaDTO(empleadoId);
        return ResponseEntity.ok().body(nomina);
    }

    @GetMapping("/buscar/nomina/{nominaId}")
    public ResponseEntity<?> buscarNominaId (@PathVariable Integer nominaId) {
        Nomina nominaBuscada = service.buscarNomina(nominaId);
        return ResponseEntity.ok().body(nominaBuscada);
    }

    @GetMapping("/buscar/empleado/{nomEmpleadoId}")
    public ResponseEntity<?> buscarEmpleadoId (@PathVariable Integer nomEmpleadoId) {
        Nomina buscarEmpleado = service.buscarEmpleado(nomEmpleadoId);
        return ResponseEntity.ok().body(buscarEmpleado);
    }

    @DeleteMapping("/eliminar/{nominaId}")
    public ResponseEntity<?> eliminarNomina (@PathVariable Integer nominaId){
        service.elimimarNomina(nominaId);
        return ResponseEntity.ok().body("Nomina eliminada");
    }
    
    


    
}
