package com.control.asistencia.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.control.asistencia.dto.AsistenciaCompletaDTO;
import com.control.asistencia.model.Asistencia;
import com.control.asistencia.service.AsistenciaService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/asistencias")
public class AsistenciaController {

    private final AsistenciaService service;

    public AsistenciaController(AsistenciaService service){
        this.service = service;
    }
    //Metodo listar a los registro de asistencias
    @GetMapping()
    public ResponseEntity<List<Asistencia>> listar() {
        return ResponseEntity.ok(service.listarAsistencias());
    }
    //metodo get buscar asistencia por id
    @GetMapping("/buscar/asistencia/{asistenciaId}")
    public ResponseEntity<?> buscarAsistencia (@PathVariable Integer asistenciaId) {
        Asistencia buscar = service.buscarAsistencia(asistenciaId);
        return ResponseEntity.ok().body(buscar);
    }
    
    //metodo crear un registro asistencia
    @PostMapping("/crear")
    public ResponseEntity<?> crearRegistro (@Valid @RequestBody Asistencia asistencia){
        Asistencia asistenciaNueva = service.crearRegistro(asistencia);
        return ResponseEntity.status(201).body(asistenciaNueva);
    }
    //metodo actualizar un registro de asistencia
    @PutMapping("/actualizar/{asistenciaId}")
    public ResponseEntity<?> actualizar(@PathVariable Integer asistenciaId, @Valid @RequestBody Asistencia asistencia ) {
        Asistencia asistenciaActualizada = service.actualizarAsistencia(asistenciaId, asistencia); 
        return ResponseEntity.ok(asistenciaActualizada);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar (@PathVariable Integer id){
        service.eliminarRegistro(id);
        return ResponseEntity.ok().body("El registro : " + id + " ha sido eliminado");
    }

    @GetMapping("/registro/empleado{empleadoId}/turno/{turnoId}")
    public ResponseEntity<?> registroCompleto(@PathVariable Integer empleadoId, @PathVariable Integer turnoId) {
        AsistenciaCompletaDTO registroCompleto = service.obtenerAsistenciaCompleta(empleadoId, turnoId);
        return ResponseEntity.ok().body(registroCompleto);
    }
    

   
    

}
