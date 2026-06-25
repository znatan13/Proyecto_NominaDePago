package com.empleado.licencia.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.empleado.licencia.dto.LicenciaEmpleado;
import com.empleado.licencia.model.Empleado;
import com.empleado.licencia.model.Licencia;
import com.empleado.licencia.model.Notificacion;
import com.empleado.licencia.repository.LicenciarRespository;

@Service
public class LicenciaService {

    
    private LicenciarRespository repository;

    public LicenciaService(LicenciarRespository respository){
        this.repository = respository;
    }

    public List<Licencia> listarLicencias() {
        return repository.findAll();
    }

    public Licencia guardar(Licencia licencia) {
        RestTemplate restTemplate = new RestTemplate();
        validarLicencia(licencia);
        boolean licenciaActiva = repository.existsByEmpleadoIdAndEstado(licencia.getEmpleadoId(), "Activo");

        if(licenciaActiva){
            throw new RuntimeException("Ya tiene una licencia asignada");
        }

        licencia.setFechaCreacion(LocalDate.now());
        licencia.setFechaVencimiento(licencia.getFechaCreacion().plusDays(7));
        licencia.setEstado("Activo");
        String url = "http://localhost:8081/empleados/buscar/id/" + licencia.getEmpleadoId();
        Empleado empleado = restTemplate.getForObject(url, Empleado.class);
        
        if (empleado == null){
            throw new RuntimeException("El Id de empleado no puede ser nulo");
        }
        
        Notificacion notificacion = new Notificacion();
       
        notificacion.setEmpleadoId(licencia.getEmpleadoId());
        notificacion.setTitulo("Se Presenta licencia");
        notificacion.setMensaje("El empleado: " + empleado.getNombre() +  "  "  +
        "presenta una licencia su motivo es: " + "  "  + licencia.getMotivoLicencia() +  " " +
        "Fecha de inicio de licencia: " + licencia.getFechaCreacion() + "Fecha de termino de licencia: " + licencia.getFechaVencimiento()
    );
        notificacion.setFecha(LocalDate.now());
        restTemplate.postForObject("http://localhost:8088/notificaciones/crear", notificacion, Notificacion.class);
        return repository.save(licencia);
    }
    public Licencia buscarLicencia(Integer idLicencia) {

        if (idLicencia == null || idLicencia <= 0) {
            throw new IllegalArgumentException("El id licencia debe ser mayor a 0 y no debe ser nulo");
        }

        Licencia licencia = repository.findById(idLicencia).orElseThrow(() -> new RuntimeException("La licencia no existe"));

        actualizarEstadoLicencia(licencia);

        return licencia;
    }
    public List<Licencia> buscarIdEmpleado(Integer empleadoId) {

        if(empleadoId == null || empleadoId <= 0) {
            throw new IllegalArgumentException("El Id de empleado debe ser mayor a 0 y no debe ser nulo");
        }

        List<Licencia> licencias = repository.findByEmpleadoId(empleadoId);

        if (licencias.isEmpty()) {
            throw new RuntimeException("No existen una licencia para el empleado");
        }

        return licencias;
    }
    public void actualizarEstadoLicencia(Licencia licencia) {

        if (LocalDate.now().isAfter(licencia.getFechaVencimiento())){
            licencia.setEstado("Vencido");
            repository.save(licencia);
        }
    }
    public Licencia actualizarLicencia (Integer idLicencia, Licencia licenciaActualizado) {

        if (idLicencia == null || idLicencia <= 0) {
            throw new IllegalArgumentException("La licencia no debe ser nulo y debe ser mayor a 0");
        }
        Optional<Licencia> existe = repository.findById(idLicencia);
        if (existe.isEmpty()) {
            throw new RuntimeException("La licencia no existe id: " + idLicencia);
        }
        validarLicencia(licenciaActualizado);
        Licencia licencia = existe.get();

        licencia.setEmpleadoId(licenciaActualizado.getEmpleadoId());
        licencia.setFechaCreacion(licenciaActualizado.getFechaCreacion());
        licencia.setFechaVencimiento(licenciaActualizado.getFechaVencimiento());
        licencia.setEstado(licenciaActualizado.getEstado());
        licencia.setMotivoLicencia(licenciaActualizado.getMotivoLicencia());

        return repository.save(licencia);
    }
    public void validarLicencia(Licencia licencia) {

        if (licencia == null) {
            throw new IllegalArgumentException("La licencia no puede ser nula");
        }

        if (licencia.getEmpleadoId() == null || licencia.getEmpleadoId() <= 0) {
            throw new IllegalArgumentException("El id empleado debe ser mayor a 0");
        }
        if(licencia.getEstado() != null && licencia.getEstado().length() > 30){
            throw new IllegalArgumentException("El estado de licencia no puede tener mas de 30 caracteres");
        }

        if(licencia.getMotivoLicencia() != null && licencia.getMotivoLicencia().length() > 300){
            throw new IllegalArgumentException("El motivo de licencia no puede superar las 300 caracteres");
        } 
    }
    public void eliminarLicencia(Integer idLicencia) {
        Licencia licencia = buscarLicencia(idLicencia);
        repository.delete(licencia);
    }

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
        List<Licencia> listarLicenciaEmpleados = repository.findByEmpleadoId(empleadoId);

        LicenciaEmpleado licenciaEmpleado = new LicenciaEmpleado();
        
        licenciaEmpleado.setNombre(empleado.getNombre());
        licenciaEmpleado.setApellido(empleado.getApellido());
        licenciaEmpleado.setRut(empleado.getRut());
        licenciaEmpleado.setCargo(empleado.getCargo());

        licenciaEmpleado.setLicencias(listarLicenciaEmpleados);

        return licenciaEmpleado;
    }
}
