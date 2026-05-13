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

    public Licencia buscar(Integer id) {
        return licenciarRespository.findById(id).orElseThrow(() -> new RuntimeException("Licencia no encontrada"));
    }

    public Licencia actualizar(Integer id, Licencia nuevaLicencia) {

        Licencia licencia = buscar(id);

        licencia.setNombreEmpleado(nuevaLicencia.getNombreEmpleado());
        licencia.setTipoLicencia(nuevaLicencia.getTipoLicencia());
        licencia.setFechaInicio(nuevaLicencia.getFechaInicio());
        licencia.setFechaTermino(nuevaLicencia.getFechaTermino());
        licencia.setEstado(nuevaLicencia.getEstado());

        return licenciarRespository.save(licencia);

    }

    public void eliminar(Integer id) {
        licenciarRespository.deleteById(id);
    }

}
