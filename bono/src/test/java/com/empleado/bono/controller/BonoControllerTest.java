package com.empleado.bono.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.empleado.bono.dto.BonosEmpleado;
import com.empleado.bono.model.Bono;
import com.empleado.bono.service.bonoService;

@WebMvcTest(BonoController.class)
public class BonoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private bonoService service;

    @Test
    void listar() throws Exception{
        List<Bono> lista = List.of(
            new Bono(
                1,
                4,
                "Bono navidad",
                LocalDate.parse("2026-06-21"),
                160000.0f,
                10.0f,
                "se otorga un bono de navidad"
            )
        );
        when(service.listarBonos()).thenReturn(lista);
        mockMvc.perform(get("/bonos/listar")).andExpect(status().isOk());
    }

    @Test
    void crearBono() throws Exception{
        String json = """
                {
                    "empleadoId": 4,
                    "nombreBono" : "bono navidad",
                    "fechaEntrega" : "2026-06-19",
                    "bonoEmpleado" : 16000.0,
                    "porcentajeBono" : "10.0",
                    "descripcion" : "se otorga bono navidad" 
                }
                """;
        Bono bonoSimulado = new Bono(
            1,
            4,
            "bono de Navidad", 
            LocalDate.parse("2026-06-19"),
            16000.0f,
            10.0f, 
            "se otorga bono navidad" 
        );
        when(service.crearBono(any(Bono.class))).thenReturn(bonoSimulado);
        mockMvc.perform(post("/bonos/crear")
        .contentType(APPLICATION_JSON)
        .content(json)).andExpect(status().isCreated());
    }

    @Test
    void  actualizarBono() throws Exception{

        String Json = """
                {
                    "bonoid": 1,
                    "empleadoId": 1,
                    "nombreBono" : "bono por hijos",
                    "fechaEntrega" : "2026-06-20",
                    "bonoEmpleado" : 50000.0,
                    "porcentajeBono" : "2.0",
                    "descripcion" : "se otorga bono por ser papa"
                        }
                """;
        Bono actualizadoSimulacion = new Bono(
                    1,
                    1,
                    "bono por hijos",
                    LocalDate.parse( "2026-06-20"),
                    50000.0f, 
                    2.0f,  
                    "se otorga bono por ser papa"
        );
        when(service.actualizarBono(1, actualizadoSimulacion)).thenReturn(actualizadoSimulacion);
        mockMvc.perform(put("/bonos/actualizar/1")
        .contentType(APPLICATION_JSON)
        .content(Json)).andExpect(status().isOk());
    }

    @Test
    void  buscarBono() throws Exception{

        Bono simular = new Bono();

        simular.setEmpleadoId(1);
        simular.setNombreBono("kino");
        simular.setFechaEntrega(LocalDate.parse("2026-06-20"));
        simular.setBonoEmpleado(4000000.0f);
        simular.setPorcentajeBono(60.0f);
        simular.setDescripcion("se ha ganado un kino y se aplica como bono como ganador");

        when(service.buscarBono(1)).thenReturn(simular);
        mockMvc.perform(get("/bonos/buscar/bono/1")).andExpect(status().isOk());
    }
    
    @Test
    void  buscarEmpleadoId() throws Exception{

       List<Bono> buscarSimulacion = List.of(
        new Bono(
            1,
            1,
            "kino",
            LocalDate.parse("2026-06-20"),
            4000000.0f,
            60.0f,
            "se ha ganado un kino y se aplica como bono como ganador"
        )
       );
       when(service.buscarIdEmpleado(1)).thenReturn(buscarSimulacion);
       mockMvc.perform(get("/bonos/buscar/empleado/1")).andExpect(status().isOk());
    }

    @Test
    void bonosEmpleado() throws Exception{
        BonosEmpleado simulado = new BonosEmpleado();
        List<Bono> simuladoBono = new ArrayList<>();

        simulado.setNombre("Manuel");
        simulado.setApellido("Mora");
        simulado.setRut("22257064-6");
        simulado.setCargo("backend");
        simulado.setBono(simuladoBono);

        when(service.bonosEmpleado(1)).thenReturn(simulado);
        mockMvc.perform(get("/bonos/empleados/1")).andExpect(status().isOk());
    }

    @Test
    void bonoUnico() throws Exception{
        Bono bonounicoSimulado = new Bono();

        bonounicoSimulado.setBonoid(1);
        bonounicoSimulado.setEmpleadoId(1);
        bonounicoSimulado.setNombreBono("Kino");
        bonounicoSimulado.setFechaEntrega(LocalDate.parse("2026-06-23"));
        bonounicoSimulado.setBonoEmpleado(4000000.0f);
        bonounicoSimulado.setPorcentajeBono(60.0f);
        bonounicoSimulado.setDescripcion("Se otorga bono de ganador de kino");

        when(service.bonoUnico(1)).thenReturn(bonounicoSimulado);
        mockMvc.perform(get("/bonos/buscar/bonoUnico/1")).andExpect(status().isOk());
    }

    @Test
    void eliminarBono() throws Exception{
        mockMvc.perform(delete("/bonos/eliminar/1")).andExpect(status().isOk());
    }
}
