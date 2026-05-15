package com.empleado.bono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empleado.bono.model.Bono;
import com.empleado.bono.repository.BonoRepository;

@Service
public class BonoService {

    @Autowired
    private BonoRepository bonoRepository;

    public List<Bono> listar() {
        return bonoRepository.findAll();
    }

    public Bono guardar(Bono bono) {
        return bonoRepository.save(bono);
    }

    public Bono buscar(Integer id) {
        return bonoRepository.findById(id).orElseThrow(() -> new RuntimeException("Bono no encontrado"));
    }

    public Bono actualizar(Integer id, Bono nuevoBono) {

        Bono bono = buscar(id);

        bono.setTipoBono(nuevoBono.getTipoBono());
        bono.setBonoEmpleado(nuevoBono.getBonoEmpleado());

        return bonoRepository.save(bono);
    }

}
