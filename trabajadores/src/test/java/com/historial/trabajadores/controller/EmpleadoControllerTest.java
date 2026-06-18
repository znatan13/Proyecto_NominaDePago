package com.historial.trabajadores.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import com.historial.trabajadores.dto.EmpleadoDTO;
import com.historial.trabajadores.model.Empleado;
import com.historial.trabajadores.service.EmpleadoService;

@WebMvcTest(EmpleadoController.class)
public class EmpleadoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService service;

    @Test
    void listarEmpleados() throws Exception{
        List<Empleado> empleados = List.of(

            new Empleado(
            1,
            "22257064-6",
            "manuel",
            "mora",
            "manuel@gmail.com",
            "+56931212907",
            "chile",
            "casado",
            LocalDate.parse("2026-06-17"), // LocalDate.parse permite agregar fecgas de tipo LocalDate sin error
            null,
            "backend",
            "Activo",
            1000000.0,
            "fondo a"
        )
    );
    when(service.listar()).thenReturn(empleados);
    mockMvc.perform(get("/empleados/listar")).andExpect(status().isOk());
}

    @Test
    void buscarPorId() throws Exception{
        
        EmpleadoDTO buscar = new EmpleadoDTO();
        buscar.setRut("22257064-6");
        buscar.setNombre("manuel");
        buscar.setApellido("mora");
        buscar.setEmail("manuel@gmail.com");
        buscar.setCargo("backend");
        buscar.setSueldoBase(100000.0);
        buscar.setAfp("fondo a");

        when(service.buscarIdDTO(1)).thenReturn(buscar);
        mockMvc.perform(get("/empleados/buscar/id/1")).andExpect(status().isOk());
    }

    @Test
    void   buscarPorRut() throws Exception{
        
        EmpleadoDTO buscar = new EmpleadoDTO();
        buscar.setRut("22257064-6");
        buscar.setNombre("manuel");
        buscar.setApellido("mora");
        buscar.setEmail("manuel@gmail.com");
        buscar.setCargo("backend");
        buscar.setSueldoBase(100000.0);
        buscar.setAfp("fondo a");

        when(service.buscarRutDTO("22257064-6")).thenReturn(buscar);
        mockMvc.perform(get("/empleados/buscar/rut/22257064-6")).andExpect(status().isOk());
    }


    @Test
    void  buscarPorEmail() throws Exception{
        
        EmpleadoDTO buscar = new EmpleadoDTO();
        buscar.setRut("22257064-6");
        buscar.setNombre("manuel");
        buscar.setApellido("mora");
        buscar.setEmail("manuel@gmail.com");
        buscar.setCargo("backend");
        buscar.setSueldoBase(100000.0);
        buscar.setAfp("fondo a");

        when(service.buscarEmailDTO("manuel@gmail.com")).thenReturn(buscar);
        mockMvc.perform(get("/empleados/buscar/email/manuel@gmail.com")).andExpect(status().isOk());
    }

    @Test
    void crearEmpleados() throws Exception{
        String JSON = """
                { 
                    "rut" : "22257064-6",
                    "nombre" : "manuel",
                    "apellido" : "mora",
                    "email" : "manuel@gmail.com",
                    "numeroTelefono" : "+56931212907",
                    "direccion" : "chile",
                    "estadoCivil" : "casado",
                    "fechaContrato" : "2026-06-17",
                    "fechaBaja" : null,
                    "cargo" : "backend",
                    "estado" : "Activo",
                    "sueldoBase" : 100000.0,
                    "afp" : "fondo a" 
                        }
                """;
        Empleado simulado = new Empleado(
            2,
            "22257064-6",
            "manuel",
            "mora",
            "manuel@gmail.com",
            "+56931212907",
            "chile",
            "casado",
            LocalDate.parse("2026-06-17"),
            null,
            "backend",
            "Activo",
            100000.0,
            "fondo a"
        );
        when(service.crearEmpleado(any(Empleado.class))).thenReturn(simulado);
        mockMvc.perform(post("/empleados/crear")
        .contentType(APPLICATION_JSON)
        .content(JSON)).andExpect(status().isCreated());
    }

    @Test
    void  actualizarEmpleado() throws Exception{
        String JSON = """
             { 
                    "rut" : "22257064-6",
                    "nombre" : "manuel",
                    "apellido" : "mora",
                    "email" : "manuel@gmail.com",
                    "numeroTelefono" : "+56931212907",
                    "direccion" : "chile",
                    "estadoCivil" : "casado",
                    "fechaContrato" : "2026-06-17",
                    "fechaBaja" : null,
                    "cargo" : "backend",
                    "estado" : "Activo",
                    "sueldoBase" : 100000.0,
                    "afp" : "fondo a" 
                        }
                """;
        Empleado actualizado = new Empleado(
                        2,
                        "12341312-2",
                        "jose",
                        "mora",
                        "coto@gmail.com",
                        "+561232907",
                        "peru",
                        "soltero",
                        LocalDate.parse("2026-06-17"),
                        null,
                        "frontend",
                        "Activo",
                        90000.0,
                        "fondo a"
        );
        when(service.actualizarPorId(2, actualizado)).thenReturn(actualizado);
        mockMvc.perform(put("/empleados/actualizar/2")
        .contentType(APPLICATION_JSON)
        .content(JSON)).andExpect(status().isOk());
    }

    @Test
    void eliminarEmpleado() throws Exception{
        mockMvc.perform(delete("/empleados/eliminar/2")).andExpect(status().isOk());
    }
}
