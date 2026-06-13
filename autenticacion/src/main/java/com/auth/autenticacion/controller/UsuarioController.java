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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @Operation(
        summary = "Listar a todos los usuarios",
        description = "Obtiene una lista de todos los usuarios registrados en el sistema"
    )
    @ApiResponses(value = {

        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
})

    @GetMapping()
    public List<Usuario> listarTodos() {
        return service.listar();
    }

        @Operation(
        summary = "Listar a todos los usuarios ocultando datos sensibles",
        description = "Obtiene una lista de todos los usuarios registrados en el sistema"
    )
    @ApiResponses(value = {

        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
})

    @GetMapping("/listar-dto")
    public ResponseEntity<?> listarUsuariosSeguros() {
        return ResponseEntity.ok(service.usuariosSeguros()); 
    }

        @Operation(
        summary = "Busca usuarios por ID",
        description = "Se obtiene informacion de usuario por su id"
    )
    @ApiResponses(value = {

        @ApiResponse(responseCode = "200", description = "busqueda obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encotrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
})

    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarId(@PathVariable Integer id){
        BuscarDatosSegurosDTO buscar = service.buscarIdDTO(id);
        return ResponseEntity.ok(buscar);
    }

        @Operation(
        summary = "Busca usuarios por su correo electronico",
        description = "Obtiene informacion de un usuario a travez su correo electronico"
    )
    @ApiResponses(value = {

        @ApiResponse(responseCode = "200", description = "busqueda obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
})


    @GetMapping("/buscar/{email}")
    public ResponseEntity<?> buscarEmail(@PathVariable String email) {
        BuscarDatosSegurosDTO existe = service.buscarEmailDTO(email);
        return ResponseEntity.status(200).body(existe);
    }

    
        @Operation(
        summary = "Registra un usuario nuevo",
        description = "guarda un usuario nuevo en el sistema "
    )
    @ApiResponses(value = {

        @ApiResponse(responseCode = "201", description = "Creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
})
    

    @PostMapping("/agregar")
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario nuevo = service.crear(usuario);
        return ResponseEntity.status(201).body(nuevo);
    }

        @Operation(
        summary = "Autenticar usuario",
        description = "Permite a los usuarios validar sus credenciales y ingresar al sistema"
    )
    @ApiResponses(value = {

        @ApiResponse(responseCode = "200", description = "Autenticado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
})
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginSeguroDTO login = service.loginSeguro(loginDTO.getNombreUsuario(), loginDTO.getPassword());
        return ResponseEntity.ok(login);
    }

        @Operation(
        summary = "Actualizar usuario por id",
        description = "Permite actualizar cualquier dato solo por el id correspondiente"
    )
    @ApiResponses(value = {

        @ApiResponse(responseCode = "200", description = "Actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
})

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarId(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {
        Usuario actualizar = service.actualizarPorId(id, usuario);
        return ResponseEntity.ok(actualizar);
    }

        @Operation(
        summary = "Eliminar un usuario",
        description = "Permite eliminar a usuarios por su id correspondiente"
    )
    @ApiResponses(value = {

        @ApiResponse(responseCode = "200", description = "Eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error del sistema")
})

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuarios(@PathVariable Integer id) {
            service.eliminar(id);
            return ResponseEntity.status(200).body("Usuario eliminado");
    }
}
    


