package com.empleado.licencia.service;

import java.time.LocalDate;
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

        validarLicencia(licencia);

        licencia.setFechaCreacion(LocalDate.now());

        licencia.setFechaVencimiento(licencia.getFechaCreacion().plusDays(7));

        licencia.setEstado("Activo");

        return licenciarRespository.save(licencia);
    }

    public Licencia buscarLicencia(Integer idLicencia) {

        if (idLicencia == null || idLicencia <= 0) {
            throw new IllegalArgumentException("El id licencia debe ser mayor a 0");
        }

        Licencia licencia = licenciarRespository.findById(idLicencia).orElseThrow(() -> new RuntimeException("La licencia no existe"));

        actualizarEstadoLicencia(licencia);

        return licencia;
    }

    public List<Licencia> buscarIdEmpleado(Integer empleadoId) {

        if(empleadoId == null || empleadoId <= 0) {
            throw new IllegalArgumentException("El Id de empleado debe ser mayor a 0");
        }

        List<Licencia> licencias = licenciarRespository.findByEmpleadoid(empleadoId);

        if (licencias.isEmpty()) {
            throw new RuntimeException("No existen una licencia para el empleado");
        }

        return licencias;
    }

    public void actualizarEstadoLicencia(Licencia licencia) {

        if (LocalDate.now().isAfter(licencia.getFechaVencimiento()));

        licencia.setEstado("Vencido");

        licenciarRespository.save(licencia);
    }

    public void validarLicencia(Licencia licencia) {

        if (licencia == null) {
            throw new IllegalArgumentException("La licencia no puede ser nula");
        }

        if (licencia.getEmpleadoid() == null || licencia.getEmpleadoid() <= 0) {
            throw new IllegalArgumentException("El id empleado debe ser mayor a 0");
        }
    }

    public void eliminar(Integer idLicencia) {
        Licencia licencia = buscarLicencia(idLicencia);

        licenciarRespository.delete(licencia);
    }
}
