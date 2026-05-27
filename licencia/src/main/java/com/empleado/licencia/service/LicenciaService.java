package com.empleado.licencia.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    private LicenciarRespository repository;

    public List<Licencia> listarLicencias() {
        return repository.findAll();
    }

    //Metodo Guardar licencias con fecha de cracion y vencimiento
    public Licencia guardar(Licencia licencia) {

        validarLicencia(licencia);

        licencia.setFechaCreacion(LocalDate.now());

        licencia.setFechaVencimiento(licencia.getFechaCreacion().plusDays(7));

        licencia.setEstado("Activo");

        return repository.save(licencia);
    }
    //Metodo Buscar licencias por id
    public Licencia buscarLicencia(Integer idLicencia) {

        if (idLicencia == null || idLicencia <= 0) {
            throw new IllegalArgumentException("El id licencia debe ser mayor a 0");
        }

        Licencia licencia = repository.findById(idLicencia).orElseThrow(() -> new RuntimeException("La licencia no existe"));

        actualizarEstadoLicencia(licencia);

        return licencia;
    }
    //Metodo buscar empleado por id
    public List<Licencia> buscarIdEmpleado(Integer empleadoId) {

        if(empleadoId == null || empleadoId <= 0) {
            throw new IllegalArgumentException("El Id de empleado debe ser mayor a 0");
        }

        List<Licencia> licencias = repository.findByEmpleadoid(empleadoId);

        if (licencias.isEmpty()) {
            throw new RuntimeException("No existen una licencia para el empleado");
        }

        return licencias;
    }
    //Actulizar licencias de Activo a Vencido o viceversa
    public void actualizarEstadoLicencia(Licencia licencia) {

        if (LocalDate.now().isAfter(licencia.getFechaVencimiento())){
            licencia.setEstado("Vencido");
            repository.save(licencia);
        }
    }
    //Metodo actualizar una licencia
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

        licencia.setEmpleadoid(licenciaActualizado.getEmpleadoid());
        licencia.setFechaCreacion(licenciaActualizado.getFechaCreacion());
        licencia.setFechaVencimiento(licenciaActualizado.getFechaVencimiento());
        licencia.setEstado(licenciaActualizado.getEstado());
        licencia.setMotivoLicencia(licenciaActualizado.getMotivoLicencia());

        return repository.save(licencia);
    }
    //Validacion de licencias
    public void validarLicencia(Licencia licencia) {

        if (licencia == null) {
            throw new IllegalArgumentException("La licencia no puede ser nula");
        }

        if (licencia.getEmpleadoid() == null || licencia.getEmpleadoid() <= 0) {
            throw new IllegalArgumentException("El id empleado debe ser mayor a 0");
        }
        if(licencia.getEstado() != null && licencia.getEstado().length() > 30){
            throw new IllegalArgumentException("El estado de licencia no puede tener mas de 30 caracteres");
        }

        if(licencia.getMotivoLicencia() != null && licencia.getMotivoLicencia().length() > 300){
            throw new IllegalArgumentException("El motivo de licencia no puede superar las 300 caracteres");
        } 
    }
    //Eliminar licencias
    public void eliminarLicencia(Integer idLicencia) {
        Licencia licencia = buscarLicencia(idLicencia);

        repository.delete(licencia);
    }

    //Metodo para conectar microservicios
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
        List<Licencia> listarLicenciaEmpleados = repository.findByEmpleadoid(empleadoId);

        LicenciaEmpleado licenciaEmpleado = new LicenciaEmpleado();
        
        licenciaEmpleado.setNombre(empleado.getNombre());
        licenciaEmpleado.setApellido(empleado.getApellido());
        licenciaEmpleado.setRut(empleado.getRut());
        licenciaEmpleado.setCargo(empleado.getCargo());

        licenciaEmpleado.setLicencia(listarLicenciaEmpleados);

        return licenciaEmpleado;
    }
}
