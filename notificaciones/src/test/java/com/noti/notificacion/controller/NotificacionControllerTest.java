package com.noti.notificacion.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.noti.notificacion.model.Notificacion;
import com.noti.notificacion.service.NotiService;

@WebMvcTest(NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotiService service;

    @Test
    void crearNotificacion() throws Exception{
        String Json = """
                {"notificacionId" : 1,
                "empleadoId" : 1,
                "titulo" : "Test",
                "mensaje" : "TestTestTest"
                }
                """;
        Notificacion simulado = new Notificacion(1,1,"Test","TestTestTest",null);
        when(service.crearNotificacion(any(Notificacion.class))).thenReturn(simulado);
        mockMvc.perform(post("/notificaciones/crear").contentType(APPLICATION_JSON)
        .content(Json)).andExpect(status().isCreated());
    }
    @Test
    void actualizarNotificacion() throws Exception{
            String Json = """
                {"notificacionId" : 1,
                "empleadoId" : 1,
                "titulo" : "Test",
                "mensaje" : "TestTest"}
                """;
        Notificacion simulado = new Notificacion(1,1,"Test","TestTest",null);
        when(service.actualizarNotificacion(1,simulado)).thenReturn(simulado);
        mockMvc.perform(put("/notificaciones/actualizar/1").contentType(APPLICATION_JSON)
        .content(Json)).andExpect(status().isOk());
    }
    @Test
    void mostrarGeneral() throws Exception{
            List<Notificacion> simulado = List.of(new Notificacion(1,1,"Test","TestTestTest",null));
            when(service.verNotificaciones()).thenReturn(simulado);
            mockMvc.perform(get("/notificaciones/general")).andExpect(status().isOk());
    }
    @Test
    void eliminar() throws Exception{
        mockMvc.perform(delete("/notificaciones/eliminar/1")).andExpect(status().isOk());
    }
}
