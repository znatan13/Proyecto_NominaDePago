package com.auth.autenticacion.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;



import com.auth.autenticacion.dto.BuscarDatosSegurosDTO;
import com.auth.autenticacion.dto.UsuarioSeguroDTO;
import com.auth.autenticacion.model.Usuario;
import com.auth.autenticacion.service.UsuarioService;



@WebMvcTest(UsuarioController.class)
public class AutenticacionControllerTest {

    @Autowired
    public MockMvc mockMvc;
    
    @MockBean
    private UsuarioService service;

    @Test
    @WithMockUser(username = "manuemora", roles = {"Admin"})
    void listarTodos() throws Exception{
        List<Usuario>  usuarios = List.of(
            new Usuario(1,"manuemora","12345678","manuel@gmail.com","Admin")
        );
        when(service.listar()).thenReturn(usuarios);
        mockMvc.perform(get("/usuarios/listar")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manuemora", roles = {"Admin"})
    void listarUsuariosSeguros() throws Exception{
        UsuarioSeguroDTO usuarioSeguroDTO = new UsuarioSeguroDTO();
        usuarioSeguroDTO.setNombreUsuario("manuemora");
        usuarioSeguroDTO.setEmail("manuel@gmail.com");

        List<UsuarioSeguroDTO> lista = List.of(usuarioSeguroDTO);
        when(service.usuariosSeguros()).thenReturn(lista);
        mockMvc.perform(get("/usuarios/listar-dto")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manuemora", roles = {"Admin"})
    void buscarId() throws Exception{
        BuscarDatosSegurosDTO buscarDatosSegurosDTO = new BuscarDatosSegurosDTO();
        buscarDatosSegurosDTO.setId(1);
        buscarDatosSegurosDTO.setNombreUsuario("manuemora");
        buscarDatosSegurosDTO.setEmail("manuel@gmail.com");
        buscarDatosSegurosDTO.setRol("Rol");

        when(service.buscarIdDTO(1)).thenReturn(buscarDatosSegurosDTO);
        mockMvc.perform(get("/usuarios/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manuemora", roles = {"Admin"})
    void buscarEmail() throws Exception{
        BuscarDatosSegurosDTO buscarDatosSegurosDTO = new BuscarDatosSegurosDTO();
        buscarDatosSegurosDTO.setId(1);
        buscarDatosSegurosDTO.setNombreUsuario("manuemora");
        buscarDatosSegurosDTO.setEmail("manuel@gmail.com");
        buscarDatosSegurosDTO.setRol("Admin");

        when(service.buscarEmailDTO("manuel@gmail.com")).thenReturn(buscarDatosSegurosDTO);
        mockMvc.perform(get("/usuarios/buscar/manuel@gmail.com")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manuemora", roles = {"Admin"})
    void crearUsuario() throws Exception{
        String usuarioJson = """
                {   
                    "nombreUsuario": "Felipe",
                    "password" : "7777777",
                    "email" : "felipebasaez@gmail.com",
                    "rol" : "Admin" 
                }
                """;
        Usuario simulado = new Usuario(
            2,
            "Felipe",
            "7777777",
            "felipebasaez@gmail.com",
            "Admin"
        );
        when(service.crear(any(Usuario.class))).thenReturn(simulado);
        mockMvc.perform(post("/usuarios/agregar")
        .with(csrf())
        .contentType(APPLICATION_JSON)
        .content(usuarioJson)).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "manuelmora", roles = {"admin"})
    void   actualizarId() throws Exception{
        String usuarioJson = """
                {  "nombreUsuario" : "joaquin",
                   "password" : "34578895",
                   "email" : "joaco@gmail.com",
                   "rol" : "admin"
                }
                """;
        Usuario simulacion = new Usuario(
                2,
                "joaquin",
                "34578895",
                "joaco@gmail.com",
                "admin"
        );
        when(service.actualizarPorId(2, simulacion)).thenReturn(simulacion);
        mockMvc.perform(put("/usuarios/actualizar/2")
        .with(csrf())
        .contentType(APPLICATION_JSON)
        .content(usuarioJson)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "manuelmora", roles = "admin")
    void eliminarUsuarios() throws Exception{
        mockMvc.perform(delete("/usuarios/eliminar/2").with(csrf()))
        .andExpect(status().isOk());
    }


    /* Pendiente metodo Login */

    /*.with(csrf())) -> simulamos un token para evitar error 403
    solo para auth no usen esto en los suyos sigan al profe de su ppt nomas, yo use esto por la depedencia seguridad
    */
}
