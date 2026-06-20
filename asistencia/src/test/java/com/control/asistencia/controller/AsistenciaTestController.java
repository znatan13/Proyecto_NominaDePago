package com.control.asistencia.controller;

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
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.control.asistencia.dto.AsistenciaCompletaDTO;
import com.control.asistencia.model.Asistencia;
import com.control.asistencia.service.AsistenciaService;

@WebMvcTest(AsistenciaController.class)
public class AsistenciaTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AsistenciaService service;

    @Test
    void listar() throws Exception {
        List<Asistencia> lista = List.of(
            new Asistencia(
            1,
            2,
            4,
            LocalDate.parse("2026-06-17"),
            "LLego puntual",
            "puntual"
            )
        );
      when(service.listarAsistencias()).thenReturn(lista);
      mockMvc.perform(get("/asistencias/listar"))
      .andExpect(status().isOk());
    }

    @Test
    void buscarAsistencia() throws Exception{

        Asistencia asistencia = new Asistencia();

        asistencia.setAsistenciaId(1);
        asistencia.setEmpleadoId(2);
        asistencia.setTurnoId(4);
        asistencia.setFechaRegistro(LocalDate.parse("2026-06-17"));
        asistencia.setObservaciones("No llego a trabajar");
        asistencia.setEstadoAsistencia("Ausente");

        when(service.buscarAsistencia(1)).thenReturn(asistencia);
        mockMvc.perform(get("/asistencias/buscar/asistencia/1"))
        .andExpect(status().isOk());
    }

    @Test
    void crearRegistro() throws Exception{
        String JSON = """
                {
                    "empleadoId" : 4,
                    "turnoId" : 2,
                    "fechaRegistro" : "2026-06-17",
                    "observaciones" : "LLega puntual al trabajo",
                    "estadoAsistencia" : "Ausencia"
                        }
                """;
        Asistencia asistencia = new Asistencia(
            1,
            4,
            2,
            LocalDate.parse("2026-06-17"),
            "LLega puntual al trabajo",
            "Ausencia"
        );
    when(service.crearRegistro(any(Asistencia.class))).thenReturn(asistencia);
    mockMvc.perform(post("/asistencias/crear")
    .contentType(APPLICATION_JSON)
    .content(JSON)).andExpect(status().isCreated());
    }

    @Test
    void actualizar() throws Exception{
            String JSON = """
                {
                    "empleadoId" : 4,
                    "turnoId" : 2,
                    "fechaRegistro" : "2026-06-17",
                    "observaciones" : "LLega puntual al trabajo",
                    "estadoAsistencia" : "Ausente"
                        }
                """;
            Asistencia actualizada = new Asistencia(

                    1,
                    2,
                    1,
                    LocalDate.parse("2026-06-25"),
                    "LLega puntual al trabajo",
                    "Puntual"
            );
        when(service.actualizarAsistencia(1, actualizada)).thenReturn(actualizada);
        mockMvc.perform(put("/asistencias/actualizar/1")
        .contentType(APPLICATION_JSON)
        .content(JSON)).andExpect(status().isOk());
    }

    @Test
    void eliminar() throws Exception{
        mockMvc.perform(delete("/asistencias/eliminar/1"))
        .andExpect(status().isOk());
    }

    @Test
    void registroCompleto() throws Exception{

        AsistenciaCompletaDTO regCompletaDTO = new AsistenciaCompletaDTO();
        List<Asistencia> asistencias =  new ArrayList<>();
        
        regCompletaDTO.setNombre("Manuel");
        regCompletaDTO.setApellido("Mora");
        regCompletaDTO.setEmail("manuel@gmail.com");
        regCompletaDTO.setCargo("backend");
        regCompletaDTO.setFecha(LocalDate.parse("2026-06-17"));
        regCompletaDTO.setHoraInicio("8:30");
        regCompletaDTO.setHoraFin("16:45");
        regCompletaDTO.setAsistencia(asistencias);

        when(service.obtenerAsistenciaCompleta(7, 1))
        .thenReturn(regCompletaDTO);
        mockMvc.perform(get("/asistencias/registro/empleado/7/turno/1"))
        .andExpect(status().isOk());
    }
}
