package com.nomina.nominaPago.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nomina.nominaPago.model.Bono;
import com.nomina.nominaPago.model.Empleado;
import com.nomina.nominaPago.model.Nomina;
import com.nomina.nominaPago.repository.NominaRepository;

@Service
public class NominaService {

    private final NominaRepository repository;
    public NominaService(NominaRepository repository){
        this.repository = repository;
    }
private double sueldoTotal;
//private double sueldoBase = 540000;

/* busqueda de datos para la nomina */
public Empleado nomina(Integer nomEmpleadoId){
    Optional<Nomina> nominaOp = repository.findById(nomEmpleadoId);
    if(nominaOp.isEmpty()){
        System.out.println("el empleado no existe");
        return null;
    }

    Nomina nomina = nominaOp.get();
    RestTemplate restTemplate = new RestTemplate();

    Integer empleadoId = nomina.getNomEmpleadoId();
        String url = ""+empleadoId;
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

        calculoAFP(AFP, sueldoBase);
        //me canse, son las 6am pero ahora en teoria si le mandas el id (con el url correcto que lo deje vacio) ya hace el calculo del afp perfectamente en base al empleado, ahora me falta hacer la conexion con bono y por ultimo ordenarlo para que parezca una nomina de veritas, pero tengo sueño, goodnight.
        calculoBono(nomEmpleadoId, empleadoId, AFP, sueldoBase, sueldoBase);

    return empleado;



}

/* calculo del bono (en progreso) 
Se abre despues de calculoAFP ya que el bono es un añadido al pago final*/
public Double calculoBono(Integer empleadoId, Integer bonoid, String tipoBono, double bonoEmpleado, double sueldoTotal){
//arreglar if (debe encontrar el id de empleado en bono u el id del bono en empleado dependiendo de como funciona bono)
    if(bono == null){
        System.out.println("El empleado no cuenta con ningun bono");
    }else{
    sueldoTotal = sueldoTotal + bonoEmpleado;
    System.out.println("El bono '"+tipoBono+"' Se añadio al sueldo.\nBono: +"+bonoEmpleado);
    return sueldoTotal;
    }

}

/* calculoAFP (Terminado)
Usa Switch Case para optimizar la busquedad de afp y hacer el calculo, utilizando "tolowecase" para evitar confuciones entre uno Uno y variaciones*/
public double calculoAFP(String AFP, double sueldoBase){
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
