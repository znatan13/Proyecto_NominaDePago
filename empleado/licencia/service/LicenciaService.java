package com.empleado.licencia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empleado.licencia.model.Licencia;
import com.empleado.licencia.repository.LicenciarRespository;

@Service
public class LicenciaService {

    @Autowired
    private LicenciarRespository licenciarRespository;

    public List<Licencia> listar() {
        return licenciarRespository.findAll();
    }

    public Licencia guardar(Licencia licencia) {
        return licenciarRespository.save(licencia);
    }

}
