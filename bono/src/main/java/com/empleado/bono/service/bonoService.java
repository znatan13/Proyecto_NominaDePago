package com.empleado.bono.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.empleado.bono.dto.BonosEmpleado;
import com.empleado.bono.model.Bono;
import com.empleado.bono.model.Empleado;
import com.empleado.bono.model.Notificacion;
import com.empleado.bono.repository.bonoRepository;

@Service
public class bonoService {

    private bonoRepository repository;

    public bonoService(bonoRepository repository){
        this.repository = repository;
    }

  
    public List<Bono> listarBonos(){
        return repository.findAll();
    }
    public Bono buscarBono (Integer bonoId){
        if(bonoId == null){
            throw new IllegalArgumentException("El id de bono no debe ser nulo");
        }
        Optional<Bono> buscar = repository.findById(bonoId);
        if(buscar.isEmpty()){
            throw new RuntimeException("El id :" + bonoId + " que busca no existe");
        }
        return buscar.get();
    }

     public List<Bono> buscarIdEmpleado(Integer empleadoId){
        if(empleadoId == null || empleadoId <= 0){
            throw new IllegalArgumentException("El id del empleado no debe ser nulo y debe ser mayor a 0");
        }
        List<Bono> buscarIdEmpleado = repository.findByEmpleadoId(empleadoId);
        if(buscarIdEmpleado.isEmpty()){
            throw new RuntimeException("El id : " + empleadoId + "no existe");
        }
        return buscarIdEmpleado;
    }
    public Bono bonoUnico (Integer empleadoId){
        if(empleadoId ==null || empleadoId <= 0 ){
            throw new IllegalArgumentException("El id del empleado no debe ser nulo y debe ser mayor a 0");
        }
        List<Bono> bonoUnico = repository.findByEmpleadoId(empleadoId);
        if(bonoUnico.isEmpty()){
            throw new RuntimeException("No existe bono");
        }
        return bonoUnico.get(0);

    }
    public void validarBono(Bono bono){

        if(bono == null){
            throw new IllegalArgumentException("El bono no debe ser nulo");
        }
        if(bono.getEmpleadoId() == null || bono.getEmpleadoId() <=  0){
            throw new IllegalArgumentException("El id del empleado debe ser mayor a 0");
        }
        if(bono.getBonoEmpleado() > 1000000000){
            throw new IllegalArgumentException("El bono de empleado debe ser de un monto coherente");
        }
        if(bono.getBonoEmpleado() <= 0){
            throw new IllegalArgumentException("El bono de empleado debe ser positivo");
        }
        if(bono.getDescripcion() != null && bono.getDescripcion().length() > 250){
            throw new IllegalArgumentException("La descripcion debe ser menor de 250 caracteres");
        }
        if(bono.getNombreBono() != null && bono.getNombreBono().length() > 100){
            throw new IllegalArgumentException("El nombre del bono debe ser menor a 100 caracteres");
        }
        if(bono.getPorcentajeBono() <= 0){
            throw new IllegalArgumentException("El porcentaje de bono debe ser mayor de 0");
        }
        if (bono.getPorcentajeBono() > 100) {
            throw new IllegalArgumentException("El bono no debe superar el 100%");
        }
        if(bono.getFechaEntrega() == null){
            throw new IllegalArgumentException("La fecha de entrega del bono no debe ser nula");
        }
    }
    public Bono crearBono (Bono bonoNuevo){
        validarBono(bonoNuevo);
        RestTemplate restTemplate = new RestTemplate();
    
        try{

            String url ="http://localhost:8081/empleados/buscar/id/" + bonoNuevo.getEmpleadoId();
            Empleado empleado = restTemplate.getForObject(url, Empleado.class);
            
            if(empleado == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id de empleado no existe");
            }
        }catch(HttpClientErrorException.NotFound ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id no existe, ingrese otro valido");
        }
        Notificacion notificacion = new Notificacion();
        notificacion.setEmpleadoId(bonoNuevo.getEmpleadoId());
        notificacion.setTitulo("Generacion de bono");
        notificacion.setMensaje(
            "Bono generado para : " +
            bonoNuevo.getNombreBono() + " " +
            bonoNuevo.getBonoEmpleado()
    );
        notificacion.setFecha(LocalDate.now());
        restTemplate.postForObject( "http://localhost:8088/notificacion/notificaciones/crear", notificacion, Notificacion.class);
        return repository.save(bonoNuevo);
    }

    public Bono actualizarBono (Integer bonoId, Bono bonoActualizado){

        if(bonoId == null || bonoId <= 0){
            throw new IllegalArgumentException("El bono no debe ser nulo y debe ser mayor a 0");
        }
        Optional<Bono> existe = repository.findById(bonoId);
        if(existe.isEmpty()){
            throw new RuntimeException("El bono no existe id: " + bonoId);
        }
        validarBono(bonoActualizado);
        Bono bono = existe.get();
        bono.setEmpleadoId(bonoActualizado.getEmpleadoId());
        bono.setNombreBono(bonoActualizado.getNombreBono());
        bono.setFechaEntrega(bonoActualizado.getFechaEntrega());
        bono.setBonoEmpleado(bonoActualizado.getBonoEmpleado());
        bono.setPorcentajeBono(bonoActualizado.getPorcentajeBono());
        bono.setDescripcion(bonoActualizado.getDescripcion());

        return repository.save(bono);
    }

    public void eliminarBono (Integer bonoId){
        if(bonoId == null){
            throw new IllegalArgumentException("El Id Bono no puede venir nulo");
        }
        if(repository.existsById(bonoId)){
            repository.deleteById(bonoId);
        }else{
            throw new RuntimeException("El bono con id: " + bonoId + " no existe");
        }
    }
    public BonosEmpleado bonosEmpleado (Integer empleadoId){
        if(empleadoId == null || empleadoId <= 0){
            throw new IllegalArgumentException("El id de empleado no debe ser nulo y debe ser mayor a 0");
        }
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/empleados/buscar/id/" + empleadoId;
        Empleado empleado = restTemplate.getForObject(url, Empleado.class);

        if(empleado == null){
            throw new RuntimeException("El empleado no existe");
        }
        List<Bono> bonos = repository.findByEmpleadoId(empleadoId);
        if(bonos.isEmpty()){
            throw new RuntimeException("El empleado no tiene bonos registrados");
        }
        BonosEmpleado bonosEmpleado = new BonosEmpleado();
        
        bonosEmpleado.setNombre(empleado.getNombre()); 
        bonosEmpleado.setApellido(empleado.getApellido());
        bonosEmpleado.setRut(empleado.getRut());
        bonosEmpleado.setCargo(empleado.getCargo());

        bonosEmpleado.setBono(bonos);

        return bonosEmpleado;
    }

}
