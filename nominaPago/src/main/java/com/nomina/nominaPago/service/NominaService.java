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
    }else    if (nomina.getBonoId() == null || nomina.getBonoId() <= 0){
        throw new IllegalArgumentException("El Id del bono debe ser positivo.");
    }
}

public Nomina buscarNomina (Integer nominaId){

    if(nominaId == null || nominaId <= 0){
        throw new IllegalArgumentException("El id de nomina no debe ser nula y debe ser mayor 0");
    }
    Optional<Nomina> buscar = repository.findById(nominaId);
    if(buscar.isEmpty()){
        throw new RuntimeException("El id : " + nominaId + " no existe");
    }
    return buscar.get();
}

public Nomina crearNomina(Nomina nomina){

    if(nomina == null){
        throw new IllegalArgumentException("La nomina no debe ser nula");
    }

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8081/empleados/buscar/id/"+nomina.getNomEmpleadoId();
    Empleado empleado;

    //si el microservicio de por casualidad se apaga o no prende muestre mensaje no se pudo conectar
    try{
        empleado = restTemplate.getForObject(url, Empleado.class);
    }catch(Exception error){
        throw new RuntimeException("No se pudo conectar con empleados");
    }

    if(empleado == null){
        throw new RuntimeException("El empleado buscado no existe");}

    double sueldoBase = empleado.getSueldoBase();
    String AFP = empleado.getAfp();
    double afpCalculada = calculoAFP(AFP, sueldoBase);
    Double sueldoTotal;

    /* ESTA URL LA CREE EN BONO DONDE BONO SE TRABAJA CON LIST DABA ERROR
    BUENO ESTA URL SOLO TARE LIST(0) -> ES DECIR SOLO 1 EMPLEADO ASI EVITAMOS ERRORES */
    String url2 = "http://localhost:8084/bonos/buscar/bonoUnico/" + nomina.getNomEmpleadoId();
    Bono bono;

    try{
        bono = restTemplate.getForObject(url2, Bono.class);
    }catch(Exception error){
        throw new RuntimeException("No se pudo conectar con bonos");
    }

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

public Nomina buscarEmpleado(Integer nomEmpleadoId){
    if(nomEmpleadoId == null || nomEmpleadoId <= 0){
        throw new IllegalArgumentException("El Id del empleado no puede estar Nulo y debe ser mayor a cero");
    }
    Optional<Nomina> buscar = repository.findByNomEmpleadoId(nomEmpleadoId);
    if(buscar.isEmpty()){
        throw new RuntimeException("El empleado no existe.");
    }
    return buscar.get();
}

public NominaSimple nominaDTO(Integer nomEmpleadoId){
    Nomina nomina = buscarNomina(nomEmpleadoId);
    NominaSimple dto = new NominaSimple();
    RestTemplate restTemplate = new RestTemplate();

    buscarNomina(nomEmpleadoId);

    String url = "http://localhost:8081/empleados/buscar/id/"+nomina.getNomEmpleadoId();
    Empleado empleado;
    try{    
        empleado = restTemplate.getForObject(url, Empleado.class);
    }catch(Exception error){
        throw new RuntimeException("No se pudo conectar con empleados");
    }

    if(empleado == null){
        throw new RuntimeException("El empleado buscado no existe");
    }
    
    /* ESTA URL LA CREE EN BONO DONDE BONO SE TRABAJA CON LIST DABA ERROR
    BUENO ESTA URL SOLO TARE LIST(0) -> ES DECIR SOLO 1 EMPLEADO ASI EVITAMOS ERRORES */
    String url2 = "http://localhost:8084/bonos/buscar/bonoUnico/"+nomina.getNomEmpleadoId();
    Bono bono;

    try{
        bono = restTemplate.getForObject(url2, Bono.class);
    }catch(Exception error){
        throw new RuntimeException("No se pudo conectar con bonos");
    }

    if (bono == null){
        throw new RuntimeException("No tiene bono.");
    }

    /* MATI ESE URL SIRVE PARA BUSCAR LAS LICENCIAS DE TAL EMPLEADO POR EJ : ID = 4
    MOSTRARA SOLO LAS LICENCIA DE EMPLEADO 4  */
    String ulr3 = "http://localhost:8085/licencias/buscar/empleadoLicencia/"+nomina.getNomEmpleadoId();
    Licencia licencia;
    try{
        licencia = restTemplate.getForObject(ulr3, Licencia.class);
    }catch(Exception error){
        throw new RuntimeException("No se pudo conectar con licencia");
    }
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
    dto.setAfp(empleado.getAfp());
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
public void elimimarNomina(Integer nominaId){
    if(nominaId == null || nominaId <= 0){
        throw new IllegalArgumentException("El id de nomina no debe ser nulo y debe ser mayor a 0");
    }
    if(repository.existsById(nominaId)){
        repository.deleteById(nominaId);
    }else{
        throw new RuntimeException("El id : " + nominaId + " que desea eliminar no existe");
    }
}

    
public Double calculoBono(double bonoEmpleado, String tipoBono,double sueldoTotal, String descripcion){

    if(bonoEmpleado < 0 || sueldoTotal < 0){
        throw new IllegalArgumentException("El bono empleado y sueldo total debe ser un valor positivo");
    }
    sueldoTotal = sueldoTotal + bonoEmpleado;
    System.out.println("El bono '"+tipoBono+"' Se añadio al sueldo.\nEl bono "+tipoBono+" se da por: "+descripcion+"\nBono: +"+bonoEmpleado+"\nTotal: "+sueldoTotal);
    return sueldoTotal;
    }

public double calculoAFP(String AFP, double sueldoBase){
    if(AFP == null){
        throw new IllegalArgumentException("La afp no debe ser nula");
    }
    if(sueldoBase < 0 ){
        throw new IllegalArgumentException("El sueldo base debe ser un numero positivo");
    }

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
