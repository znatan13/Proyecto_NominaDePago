package com.empleado.licencia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.empleado.licencia.service.LicenciaService;

@WebMvcTest(LicenciaController.class)
public class LicenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LicenciaService service;

}
