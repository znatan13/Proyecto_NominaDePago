package com.empleado.bono.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

}
