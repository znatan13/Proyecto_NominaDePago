package com.empleado.licencia.controller;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.empleado.licencia.model.Licencia;
import com.empleado.licencia.service.LicenciaService;

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
        mockMvc.perform(get("/licencias/listar")).andExpect(status().isOk);
    }

    @Test
    void crearLicencia() throws Exception{
        String json = """
                {
                    "empleadoid": 2,
                    "motivoLicencia": Dios lo bendiga
                }
                """;
        Licencia licenciaSimulado = new licencia(
            1,
            2,
            "2026-06-21",
            "2026-07-21",
            "Activo",
            "Dios lo bendiga"
        );
        when(service.crearLicencia(any(Licencia.class))).thenReturn(licenciaSimulado);
        mockMvc.perform(post("licencias/crear")
        .contentType(APPLICATION.JSON)
        .content(json)).andExpect(status().isCreated());
    }

    @Test
    void actualizarLicencia() throws Exception{
        String json = """
                {
                    "fechaVencimiento": "2026-06-22",
                    "estado": "Vencido"
                }
                """;
        Licencia actualizadoSimulacion = new Licencia(
            1,
            2,
            LocalDate.parse("2026-06-21"),
            LocalDate.parse("2026-06-22"),
            "Vencido",
            "Dios lo bendiga"
        );
        when(service.actualizarLicencia(1, actualizadoSimulacion)).thenReturn(actualizadoSimulacion);
        mockMvc.perform(put("licencias/actualizar/1")
        .contentType(APPLICATION_JSON)
        .content(json)).andExpect(status().isOk());
    }

    @Test
    void buscarLicencia() throws Exception{
        Licencia simular = new Licencia();

        simular.setIdLicencia(1);
        simular.setEmpleadoid(2);
        simular.setEstado("Activo");
        simular.setFechaCreacion("2026-06-21");
        simular.setFechaVencimiento("2026-06-22");
        simular.setMotivoLicencia("Dios lo bendiga");

        when(service.buscarLicencia(1)).thenReturn(simular);
        mockMvc.perform(get("licencias/buscar/licencia/1")).andExpect(status().isOk());
    }

    @Test
    void buscarEmpleadoid() throws Exception{

        List<Licencia> buscarSimulacion = List.of(
            new Licencia(
                2,
                "Activo"
            )
        );
        when(service.buscarIdEmpleado(1)).thenReturn(buscarSimulacion);
        mockMvc.perform(get("/licencias/buscar/empleado/1")).andExpect(status().isOk());
    }

    @Test
    void buscarLicenciaEmpleado() throws Exception{
        
    }

}
