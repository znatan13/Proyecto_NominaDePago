package com.nomina.nominaPago.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.nomina.nominaPago.dto.NominaSimple;
import com.nomina.nominaPago.model.Nomina;
import com.nomina.nominaPago.service.NominaService;


@WebMvcTest(NominaController.class)
public class NominaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NominaService service;

    @Test
    void listar() throws Exception{
        List<Nomina> simulacion = List.of(new Nomina(1, 1, 1, 2000.0));
        when(service.listar()).thenReturn(simulacion);
        mockMvc.perform(get("/nomina/listar")).andExpect(status().isOk());
    }
    @Test
    void crearNomina() throws Exception{
        String Json = """
                {"nominaId" : 1,
                "nomEmpleadoId" : 1,
                "bonoId" : 1,
                "sueldoLiquido" : 2000.0}
                """;
        Nomina simulado = new Nomina(1,1,1,2000.0);
        when(service.crearNomina(any(Nomina.class))).thenReturn(simulado);
        mockMvc.perform(post("/nomina/crear").contentType(APPLICATION_JSON)
        .content(Json)).andExpect(status().isCreated());
    }
    @Test
    void nominaUsuario() throws Exception{
        NominaSimple simulado = new NominaSimple();
            simulado.setCorreo("asd");
            simulado.setNombre("aaa");
            simulado.setApellido("sss");
            simulado.setRut("1231");
            simulado.setCargo("qweqweqwe");
            simulado.setHorario("12");
            simulado.setAfp("yyy");
            simulado.setSueldo_Base(2000.0);
            simulado.setLicencias(null);
            simulado.setNomina(null);
            simulado.setNombre_Bono("ppp");
            simulado.setCantidad_bono(20.0);
            simulado.setDescripcion_Bono("KKKKKKK");
            simulado.setSueldo_Total(10.0);
    when(service.nominaDTO(1)).thenReturn(simulado);
    mockMvc.perform(get("/nomina/1")).andExpect(status().isOk());
    }
    @Test
    void buscarNominaId() throws Exception{
        Nomina simulado = new Nomina();
            simulado.setNominaId(1);
            simulado.setNomEmpleadoId(1);
            simulado.setBonoId(1);
            simulado.setSueldoLiquido(2000.0);
        when(service.buscarNomina(1)).thenReturn(simulado);
        mockMvc.perform(get("/nomina/buscar/nomina/1")).andExpect(status().isOk());
    }
    @Test
    void buscarEmpleadoId() throws Exception{
        Nomina simulado = new Nomina();
            simulado.setNominaId(1);
            simulado.setNomEmpleadoId(1);
            simulado.setBonoId(1);
            simulado.setSueldoLiquido(2000.0);
        when(service.buscarEmpleado(1)).thenReturn(simulado);
        mockMvc.perform(get("/nomina/buscar/empleado/1")).andExpect(status().isOk());
    }
    @Test
    void actualizarNomina() throws Exception{
        String Json = """
                {"nominaId" : 1,
                "nomEmpleadoId" : 1,
                "bonoId" : 1,
                "sueldoLiquido" : 3000.0}
                """;
        Nomina simulado = new Nomina(1,1,1,3000.0);
        when(service.actualizarNomina(1,simulado)).thenReturn(simulado);
        mockMvc.perform(put("/nomina/actualizar/1").contentType(APPLICATION_JSON)
        .content(Json)).andExpect(status().isOk());
    }
    @Test
    void eliminarNomina() throws Exception{
        mockMvc.perform(delete("/nomina/eliminar/1")).andExpect(status().isOk());
    }
}
