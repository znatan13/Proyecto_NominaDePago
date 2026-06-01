package com.nomina.nominaPago.service;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nomina.nominaPago.dto.NominaSimple;
import com.nomina.nominaPago.model.Bono;
import com.nomina.nominaPago.model.Empleado;
import com.nomina.nominaPago.model.Licencia;
import com.nomina.nominaPago.model.Nomina;
import com.nomina.nominaPago.model.Notificacion;
import com.nomina.nominaPago.repository.NominaRepository;

@Service
public class NominaService {

    private final NominaRepository repository;
    public NominaService(NominaRepository repository){
        this.repository = repository;
    }

public void validaciones(Nomina nomina){
    if (nomina == null){
        throw new IllegalArgumentException("Nomina no puede estar Nulo.");
    }else     if (nomina.getSueldoLiquido() == null){
        throw new IllegalArgumentException("El sueldo no puede ser Nulo.");
    }else    if (nomina.getSueldoLiquido() < 0 || nomina.getSueldoLiquido() > 999999999){
        throw new IllegalArgumentException("El sueldo liquido debe ser positivo y en un margen coherente.");
    }else    if (nomina.getNomEmpleadoId() == null || nomina.getNomEmpleadoId() <= 0){
        throw new IllegalArgumentException("El Id del empleado no puede estar Nulo y debe ser mayor a cero.");
    }else    if (nomina.getBonoId() <= 0){
        throw new IllegalArgumentException("El Id del bono debe ser positivo.");
    }
}

public Nomina crearNomina(Nomina nomina){
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8081/empleados/buscar/id/"+nomina.getNomEmpleadoId();
    Empleado empleado = restTemplate.getForObject(url, Empleado.class);
    if(empleado == null){
        throw new RuntimeException("El empleado buscado no existe");}
    double sueldoBase = empleado.getSueldoBase();
    String AFP = empleado.getAFP();
    double afpCalculada = calculoAFP(AFP, sueldoBase);
    Double sueldoTotal;

    String url2 = "http://localhost:8084/buscar/empleado/"+nomina.getNomEmpleadoId();
    Bono bono = restTemplate.getForObject(url2, Bono.class);
    if (bono != null){
        String nomBono = bono.getNombreBono();
        Float bonoEmp = bono.getBonoEmpleado();
        String bonoDesc = bono.getDescripcion();
        sueldoTotal = calculoBono(bonoEmp, nomBono, afpCalculada, bonoDesc);
    }else{sueldoTotal = afpCalculada;}
    nomina.setSueldoLiquido(sueldoTotal);

    validaciones(nomina);
    return repository.save(nomina);
}

public Nomina buscarNomina(Integer nomEmpleadoId){
    if(nomEmpleadoId == null || nomEmpleadoId <= 0){
        throw new IllegalArgumentException("El Id del empleado no puede estar Nulo y debe ser mayor a cero");
    }
    Optional<Nomina> buscar = repository.findByNomEmpleadoId(nomEmpleadoId);
    if(buscar.isEmpty()){
        throw new RuntimeException("El usuario no existe.");
    }
    return buscar.get();
}

public NominaSimple nominaDTO(Integer nomEmpleadoId){
    Nomina nomina = buscarNomina(nomEmpleadoId);
    NominaSimple dto = new NominaSimple();
    RestTemplate restTemplate = new RestTemplate();

    buscarNomina(nomEmpleadoId);

    String url = "http://localhost:8081/empleados/buscar/id/"+nomina.getNomEmpleadoId();
    Empleado empleado = restTemplate.getForObject(url, Empleado.class);
    if(empleado == null){
        throw new RuntimeException("El empleado buscado no existe");
    }
    
    String url2 = "http://localhost:8084/buscar/empleado/"+nomina.getNomEmpleadoId();
    Bono bono = restTemplate.getForObject(url2, Bono.class);
    if (bono != null){
        throw new RuntimeException("No tiene bono.");
    }

    String ulr3 = "http://localhost:8085/licencias/buscar/empleado/"+nomina.getNomEmpleadoId();
    Licencia licencia = restTemplate.getForObject(ulr3, Licencia.class);
    if(licencia != null){
        //contar
    }

    dto.setNombre(empleado.getNombre());
    dto.setApellido(empleado.getApellido());
    dto.setCargo(empleado.getCargo());
    dto.setHorario(empleado.getFechaIngreso()+"-"+empleado.getFechaSalida());
    dto.setRut(empleado.getRut());
    dto.setCorreo(empleado.getEmail());
    dto.setSueldo_Base(empleado.getSueldoBase());
    dto.setLicencias(licencia);
    dto.setAFP(empleado.getAFP());
    dto.setNombre_Bono(bono.getNombreBono());
    dto.setCantidad_bono(bono.getBonoEmpleado());
    dto.setDescripcion_Bono(bono.getDescripcion());
    dto.setSueldo_Total(nomina.getSueldoLiquido());
    dto.setNomina(nomina);

        try {
        Notificacion notificacion = new Notificacion();

        notificacion.setEmpleadoId(nomEmpleadoId);
        notificacion.setTitulo("Generacion de nomina");
        notificacion.setMensaje("Nomina de pago creada para el usuario:\nNombre: "+empleado.getNombre()+" "+empleado.getApellido()+"\nRut: "+empleado.getRut()+"\nSueldo bruto: "+empleado.getSueldoBase()+"\nSueldo liquido: "+nomina.getSueldoLiquido());
        notificacion.setFecha(LocalDate.now());

        String url5 = "http://localhost:8080/notificaciones";
        restTemplate.postForObject(url5, notificacion, Notificacion.class);
        }catch(Exception e){
            System.out.println("Error al guardar notificacion");
        }

    return dto;
    }

public Double calculoBono(double bonoEmpleado, String tipoBono,double sueldoTotal, String descripcion){
    sueldoTotal = sueldoTotal + bonoEmpleado;
    System.out.println("El bono '"+tipoBono+"' Se añadio al sueldo.\nEl bono "+tipoBono+" se da por: "+descripcion+"\nBono: +"+bonoEmpleado+"\nTotal: "+sueldoTotal);
    return sueldoTotal;
    }

public double calculoAFP(String AFP, double sueldoBase){
    double sueldoTotal;
    switch(AFP.toLowerCase()){
        case "uno":
            sueldoTotal= sueldoBase - sueldoBase*0.1046;
            System.out.println("AFP Uno: 10,46%");
            break;
        case "modelo":
            sueldoTotal= sueldoBase - sueldoBase*0.1058;
            System.out.println("AFP Modelo: 10,58%");
            break;
        case "planvital":
            sueldoTotal= sueldoBase - sueldoBase*0.1116;
            System.out.println("AFP Planvital: 11,16%");
            break;
        case "habitat":
            sueldoTotal= sueldoBase - sueldoBase*0.1127;
            System.out.println("AFP Habitat: 11,27%");
            break;
        case "capital":
            sueldoTotal= sueldoBase - sueldoBase*0.1144;
            System.out.println("AFP Capital: 11,44%");
            break;
        case "cuprum":
            sueldoTotal= sueldoBase - sueldoBase*0.1144;
            System.out.println("AFP Cuprum: 11,44%");
            break;
        case "provida":
            sueldoTotal= sueldoBase - sueldoBase*0.1145;
            System.out.println("AFP Provida: 11,45%");
            break;
        default:
            System.out.println("AFP no reconocida.");
            sueldoTotal = sueldoBase;
            break;
    }
return sueldoTotal;
     
}



}
