package com.empleado.licencia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.empleado.licencia.dto.LicenciaEmpleado;
import com.empleado.licencia.model.Empleado;
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

    // metodo para conectar microservicios
    public LicenciaEmpleado licenciaEmpleado(Integer empleadoId){
        if(empleadoId == null || empleadoId <= 0){
            throw new IllegalArgumentException("El id del empleado no debe ser nulo y debe ser mayor a 0");         
        }
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/empleados/buscar/id/" + empleadoId;
        Empleado empleado = restTemplate.getForObject(url, Empleado.class);
        if(empleado == null){
            throw new RuntimeException("El empleado no existe");
        }
        List<Licencia> listarLicenciaEmpleados = licenciarRespository.findByEmpleadoid(empleadoId);

        LicenciaEmpleado licenciaEmpleado = new LicenciaEmpleado();
        licenciaEmpleado.setNombre(empleado.getNombre());
        licenciaEmpleado.setApellido(empleado.getApellido());
        licenciaEmpleado.setRut(empleado.getRut());
        licenciaEmpleado.setCargo(empleado.getCargo());

        licenciaEmpleado.setLicencia(listarLicenciaEmpleados);

        return licenciaEmpleado;
    }
}
