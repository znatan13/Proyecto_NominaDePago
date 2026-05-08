package com.empleado.bono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empleado.bono.model.Bono;
import com.empleado.bono.repository.bonoRepository;

@Service
public class bonoService {

    @Autowired
    private bonoRepository bonoRepository;

    public List<Bono> listar() {
        return bonoRepository.findAll();
    }

    public Bono guardar(Bono bono) {
        return bono;
    }

    public Bono buscar(Long id) {
        return bonoRepository.findById(id).orElseThrow(() -> new RuntimeException("Bono no encontrado"));
    }

    public Bono actualizar(Long id, Bono nuevoBono) {
        Bono bono = buscar(id);

        bono.setNombreEmpleado(nuevoBono.getNombreEmpleado());
        bono.setTipoBono(nuevoBono.getTipoBono());
        bono.setFechaAsignacion(nuevoBono.getFechaAsignacion());
        bono.setMonto(nuevoBono.getMonto());
        bono.setEstado(nuevoBono.getEstado());

        return bonoRepository.save(bono);
        
    }

    public void eliminar(Long id) {
        bonoRepository.deleteById(id);
    }

}
