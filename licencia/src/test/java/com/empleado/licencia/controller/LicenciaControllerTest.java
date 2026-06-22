package com.empleado.licencia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.empleado.licencia.dto.LicenciaEmpleado;
import com.empleado.licencia.model.Licencia;
import com.empleado.licencia.service.LicenciaService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(LicenciaController.class)
public class LicenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LicenciaService service;

    @Test
    void listar() throws Exception{
        List<Licencia> simulado= List.of(
            new Licencia(
                1,
                2,
                LocalDate.parse("2026-06-21"),
                LocalDate.parse("2026-07-21"),
                "Activo",
                "Dios lo bendiga"
            )
        );
        when(service.listarLicencias()).thenReturn(simulado);
        mockMvc.perform(get("/licencias/listar")).andExpect(status().isOk());
    }

    @Test
    void crearLicencia() throws Exception{
        String json = """
                {
                    "empleadoid": 2,
                    "motivoLicencia": "Dios lo bendiga"
                }
                """;
        Licencia licenciaSimulado = new Licencia(
            1,
            2,
            LocalDate.now(),
            LocalDate.now().plusDays(7),
            "Activo",
            "Dios lo bendiga"
        );
        when(service.guardar(any(Licencia.class))).thenReturn(licenciaSimulado);
        mockMvc.perform(post("/licencias/crear")
        .contentType(APPLICATION_JSON)
        .content(json)).andExpect(status().isCreated());
    }

    @Test
    void actualizarLicencia() throws Exception{
        String json = """
                {
                    "empleadoId": 2,
                    "fechaCreacion" : "2026-06-21",
                    "fechaVencimiento" : "2026-06-28",
                    "estado" : "Activo",
                    "motivoLicencia" : "Influenza"
                }
                """;
        Licencia actualizadoSimulacion = new Licencia(
            1,
            2,
            LocalDate.parse("2026-06-21"),
            LocalDate.parse("2026-06-28"),
            "Activo",
            "Influenza"
        );
        when(service.actualizarLicencia(1, actualizadoSimulacion)).thenReturn(actualizadoSimulacion);
        mockMvc.perform(put("/licencias/actualizar/1")
        .contentType(APPLICATION_JSON)
        .content(json)).andExpect(status().isOk());
    }

    @Test
    void buscarLicencia() throws Exception{
        Licencia simular = new Licencia();

        simular.setEmpleadoid(2);
        simular.setEstado("Activo");
        simular.setFechaCreacion(LocalDate.parse("2026-06-21"));
        simular.setFechaVencimiento(LocalDate.parse("2026-06-22"));
        simular.setMotivoLicencia("Neumonia");

        when(service.buscarLicencia(1)).thenReturn(simular);
        mockMvc.perform(get("/licencias/buscar/licencia/1")).andExpect(status().isOk());
    }

    @Test
    void buscarEmpleadoId() throws Exception{
        List<Licencia> simulado = List.of(
            new Licencia(
                1,2,LocalDate.parse("2026-06-22"),
                LocalDate.parse("2026-06-30"), "Activo", "Lesion de futbol"

            )
        );
        when(service.buscarIdEmpleado(2)).thenReturn(simulado);
        mockMvc.perform(get("/licencias/buscar/empleado/2")).andExpect(status().isOk());
    }

    @Test
    void buscarLicenciaEmpleados() throws Exception{
        LicenciaEmpleado simulado = new LicenciaEmpleado();
        List<Licencia> licenciaSimulada = new ArrayList<>();

        simulado.setNombre("Manuel");
        simulado.setApellido("Mora");
        simulado.setRut("222523-1");
        simulado.setCargo("Backend");
        simulado.setLicencias(licenciaSimulada);

        when(service.licenciaEmpleado(2)).thenReturn(simulado);
        mockMvc.perform(get("/licencias/buscar/empleadoLicencia/2"))
        .andExpect(status().isOk());
    }

    @Test
    void eliminarLicencia() throws Exception{
        mockMvc.perform(delete("/licencias/eliminar/1")).andExpect(status().isOk());
    }

}
