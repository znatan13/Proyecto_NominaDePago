package com.empleados.turnos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.empleados.turnos.dto.EmpleadoSimple;
import com.empleados.turnos.model.Turnos;
import com.empleados.turnos.service.TurnosService;

@WebMvcTest(TurnosController.class)

public class TurnoTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TurnosService service;

    @Test
    void listar() throws Exception{
        List<Turnos> listar = List.of(
            new Turnos(
                1,
                4,
                LocalDate.parse("2026-06-21"),
                "23:45",
                "08:45",
                "Activo"
            )
        );
        when(service.listar()).thenReturn(listar);
        mockMvc.perform(get("/turnos/listar")).andExpect(status().isOk());
    }

    @Test
    void buscarIdTurno() throws Exception{

        Turnos buscar = new Turnos();

        buscar.setTurnoId(1);
        buscar.setEmpleadoId(4);
        buscar.setFecha(LocalDate.parse("2026-06-21"));
        buscar.setHoraInicio("23:45");
        buscar.setHoraFin("08:45");
        buscar.setEstado("Activo");

        when(service.buscarPorId(1)).thenReturn(buscar);
        mockMvc.perform(get("/turnos/buscar/turnoid/1")).andExpect(status().isOk());
    }

    @Test
    void  buscarIdEmpleado() throws Exception{
        List<Turnos> lista = List.of(
            new Turnos(
                1,
                4,
                LocalDate.parse("2026-06-21"),
                "23:45",
                "08:45",
                "Activo"
            )
        );
        when(service.buscarIdEmpleado(4)).thenReturn(lista);
        mockMvc.perform(get("/turnos/buscar/empleadoid/4")).andExpect(status().isOk());
    }

    @Test
    void empleadoId() throws Exception{
        EmpleadoSimple simular = new EmpleadoSimple();
        List<Turnos> turnos = new ArrayList<>();

        simular.setRut("22257064-6");
        simular.setNombre("Manuel");
        simular.setApellido("Mora");
        simular.setCargo("backend");
        simular.setTurnos(turnos);

        when(service.obtenerTurnosConEmpleados(4)).thenReturn(simular);
        mockMvc.perform(get("/turnos/empleado/4")).andExpect(status().isOk());
    }
    
    @Test
    void crearTurno() throws Exception{
        String JSOn = """
                { "turnoId" : 1,
                  "empleadoId" : 4,
                  "fecha" : "2026-06-20",
                  "horaInicio" : "09:45",
                  "horaFin" : "18:45",
                  "estado" : "Activo"
                } 
                """;
        Turnos turnoSimulado = new Turnos(

                1,
                4,
                LocalDate.parse("2026-06-20"),
                "09:45",
                "18:45",
                "Activo"
        );
        when(service.crearTurno(any(Turnos.class))).thenReturn(turnoSimulado);
        mockMvc.perform(post("/turnos/crear")
        .contentType(APPLICATION_JSON)
        .content(JSOn)).andExpect(status().isCreated());
    }

    @Test
    void actualizar() throws Exception{
        String Json = """
                {
                  "turnoId" : 1,
                  "empleadoId" : 7,
                  "fecha" : "2026-06-30",
                  "horaInicio" : "19:45",
                  "horaFin" : "09:45",
                  "estado" : "Activo"
                }
                """;
        Turnos turnoSimuladoAc = new Turnos(

                1,
                7,
                LocalDate.parse("2026-06-30"),
                "19:45",
                "09:45",
                "Activo"
        );
        when(service.actualizarId(1, turnoSimuladoAc)).thenReturn(turnoSimuladoAc);
        mockMvc.perform(put("/turnos/actualizar/1")
        .contentType(APPLICATION_JSON)
        .content(Json)).andExpect(status().isOk());
    }

    @Test
    void  eliminar() throws Exception{
        mockMvc.perform(delete("/turnos/eliminar/1")).andExpect(status().isOk());
    }




}
