package com.auth.autenticacion.controller;

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

import com.auth.autenticacion.dto.BuscarDatosSegurosDTO;
import com.auth.autenticacion.dto.LoginDTO;
import com.auth.autenticacion.dto.LoginSeguroDTO;

import com.auth.autenticacion.model.Usuario;
import com.auth.autenticacion.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    // Get listar todo
    @GetMapping()
    public List<Usuario> listarTodos() {
        return service.listar();
    }

    // Metodo get con dto solo mostrar datos no sensibles del usuario.
    @GetMapping("/listar-dto")
    public ResponseEntity<?> listarUsuariosSeguros() {
        return ResponseEntity.ok(service.usuariosSeguros()); // Si sale biem retorna un 200
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarId(@PathVariable Integer id){
        BuscarDatosSegurosDTO buscar = service.buscarIdDTO(id);
        return ResponseEntity.ok(buscar);
    }

    // Get buscar email del usuario con DTO para proteger los datos sensibles
    @GetMapping("/buscar/{email}")
    public ResponseEntity<?> buscarEmail(@PathVariable String email) {
        BuscarDatosSegurosDTO existe = service.buscarEmailDTO(email);
        return ResponseEntity.status(200).body(existe);
    }

    // metodo post crear una cuenta de usuario.
    @PostMapping("/agregar")
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario nuevo = service.crear(usuario);
        return ResponseEntity.status(201).body(nuevo);
    }

    // metodo post login
    @PostMapping("/login")
    // Login seguro para no mostrar datos sensibles y LoginDTO para que solo ingrese
    // nombre y password
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginSeguroDTO login = service.loginSeguro(loginDTO.getNombreUsuario(), loginDTO.getPassword());
        return ResponseEntity.ok(login);
    }

    // Metodo Put: Actualizar por id
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarId(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
        Usuario actualizar = service.actualizarPorId(id, usuario);
        return ResponseEntity.ok(actualizar);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuarios(@PathVariable Integer id) {
            service.eliminar(id);
            return ResponseEntity.status(200).body("Usuario eliminado");
    }
}
    


