package com.nomina.nominaPago.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.management.Notification;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nomina.nominaPago.dto.NominaSimple;
import com.nomina.nominaPago.model.Bono;
import com.nomina.nominaPago.model.Empleado;
import com.nomina.nominaPago.model.Nomina;
import com.nomina.nominaPago.model.Notificacion;
import com.nomina.nominaPago.repository.NominaRepository;

@Service
public class NominaService {

    private final NominaRepository repository;
    public NominaService(NominaRepository repository){
        this.repository = repository;
    }

/* busqueda de datos para la nomina */
public NominaSimple nomina(Integer nomEmpleadoId){
    Optional<Nomina> nominaOp = repository.findById(nomEmpleadoId);
    if(nominaOp.isEmpty()){
        System.out.println("el empleado no existe");
        return null;
    }
    Nomina nomina = nominaOp.get();
    RestTemplate restTemplate = new RestTemplate();

    Integer empleadoId = nomina.getNomEmpleadoId();
        String url = "http://localhost:8080/buscar/id/"+empleadoId;
        Empleado empleado = restTemplate.getForObject(url, Empleado.class);

        String nombre = empleado.getNombre();
        String apellido = empleado.getApellido();
        String rut = empleado.getRut();
        String mail = empleado.getEmail();
        String num = empleado.getNumeroTelefono();
        String dir = empleado.getDireccion();
        String estadoCivil = empleado.getEstadoCivil();
        String fechaIngreso = empleado.getFechaIngreso();
        String fechaSalida = empleado.getFechaSalida();
        String cargo = empleado.getCargo();
        String estado = empleado.getEstado();
        double sueldoBase = empleado.getSueldoBase();
        String AFP = empleado.getAFP();

        double afpCalculada = calculoAFP(AFP, sueldoBase);
        String url2 = "http://localhost:8080/buscar/empleado/"+empleadoId;
        Bono bono = restTemplate.getForObject(url2, Bono.class);

        double sueldoTotal = afpCalculada;
        String nomBono = null;
        double bonoEmp = 0;
        String bonoDesc = null; 
        if (bono != null){
            nomBono = bono.getNombreBono();
            bonoEmp = bono.getBonoEmpleado();
            bonoDesc = bono.getDescripcion();
            System.out.println("***Nomina de Pago***\nTrabajador: "+nombre+" "+apellido+"\nRut: "+rut+"\nCargo: "+cargo+"\nEstado Civil: "+estadoCivil);
            System.out.println("Estado de contrato: "+estado+"\nCorreo: "+mail+"\nNumero telefonico: "+num+"\nDirección vivienda: "+dir);
            System.out.println("Fecha de Firma del contrato: "+fechaIngreso+"\nFecha de Retiro: "+fechaSalida);
            sueldoTotal = calculoBono(bonoEmp, nomBono, afpCalculada, bonoDesc);
        }
        
        Double afpDescuento = Math.abs(afpCalculada - sueldoBase);
        NominaSimple dto = new NominaSimple();

        

        dto.setNombre(nombre);
        dto.setApellido(apellido);
        dto.setCargo(cargo);
        dto.setRut(rut);
        dto.setCorreo(mail);
        dto.setSueldo_Base(sueldoBase);
        dto.setAFP(AFP);
        dto.setDescuento_de_AFP(afpDescuento);
        dto.setNombre_Bono(nomBono);
        dto.setCantidad_bono(bonoEmp);
        dto.setDescripcion_Bono(bonoDesc);
        dto.setSueldo_Total(sueldoTotal);

        try {
        Notificacion notificacion = new Notificacion();

        notificacion.setEmpleadoId(empleadoId);
        notificacion.setTitulo("Generacion de nomina");
        notificacion.setMensaje("Nomina de pago creada para el usuario:\nNombre: "+nombre+" "+apellido+"\nRut: "+rut+"\nSueldo bruto: "+sueldoBase+"\nSueldo liquido: "+sueldoTotal);
        notificacion.setFecha(LocalDate.now());

        String url3 = "http://localhost:8080/notificaciones";
        restTemplate.postForObject(url3, notificacion, Notificacion.class);
        }catch(Exception e){
            System.out.println("Error al guardar notificacion");
        }
        return dto;
}

/* calculo del bono
Se abre despues de calculoAFP ya que el bono es un añadido al pago final*/
public Double calculoBono(double bonoEmpleado, String tipoBono,double sueldoTotal, String descripcion){
    sueldoTotal = sueldoTotal + bonoEmpleado;
    System.out.println("El bono '"+tipoBono+"' Se añadio al sueldo.\nEl bono "+tipoBono+" se da por: "+descripcion+"\nBono: +"+bonoEmpleado+"\nTotal: "+sueldoTotal);
    return sueldoTotal;
    }

/* calculoAFP
Usa Switch Case para optimizar la busquedad de afp y hacer el calculo, utilizando "tolowecase" para evitar confuciones entre uno Uno y variaciones*/
public double calculoAFP(String AFP, double sueldoBase){
    double sueldoTotal;
    switch(AFP.toLowerCase()){
        case "uno":
            sueldoTotal= sueldoBase - sueldoBase*0.046;
            System.out.println("AFP Uno: 0,46%");
            break;
        case "modelo":
            sueldoTotal= sueldoBase - sueldoBase*0.058;
            System.out.println("AFP Modelo: 0,58%");
            break;
        case "planvital":
            sueldoTotal= sueldoBase - sueldoBase*0.116;
            System.out.println("AFP Planvital: 1,16%");
            break;
        case "habitat":
            sueldoTotal= sueldoBase - sueldoBase*0.127;
            System.out.println("AFP Habitat: 1,27%");
            break;
        case "capital":
            sueldoTotal= sueldoBase - sueldoBase*0.144;
            System.out.println("AFP Capital: 1,44%");
            break;
        case "cuprum":
            sueldoTotal= sueldoBase - sueldoBase*0.144;
            System.out.println("AFP Cuprum: 1,44%");
            break;
        case "provida":
            sueldoTotal= sueldoBase - sueldoBase*0.145;
            System.out.println("AFP Provida: 1,45%");
            break;
        default:
            System.out.println("AFP no reconocida.");
            sueldoTotal = sueldoBase;
            break;
    }
    // se entrega lo que descuenta el afp EJ: "descuento afp: -5000", y luego da el total con el descuento, math.abs encuentra cual fue la resta u suma entre 2 numeros.
    System.out.println("\nDescuento AFP: -"+(Math.abs(sueldoBase-sueldoTotal))+"\nTotal: "+sueldoTotal);
    return sueldoTotal;
     
}



}
