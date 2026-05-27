package com.nomina.nominaPago.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nomina.nominaPago.model.Empleado;

@Service
public class NominaService {
private double sueldoTotal;
//private double sueldoBase = 540000;


/* El orden en el que los calculos se hacen dentro de la nomina, al final de este se entrega la informacion pedida */
public Optional<Empleado> nomina(Integer empleadoId){
    //datos deben buscarse en empleado
    Double sueldoTotal= 1.0;
    String AFP="modelo";
    //datos deben buscarse en empleado

    if(empleadoId == null){
        System.out.println("el empleado no existe");
    }else
        calculoAFP(empleadoId, AFP, sueldoTotal);
        // Datos temporales
        calculoBono(empleadoId, 2, "navidad", 20000, sueldoTotal);
        // cambiar a lo debido
        return null;
}

/* calculo del bono (en progreso) 
Se abre despues de calculoAFP ya que el bono es un añadido al pago final*/
public double calculoBono(Integer empleadoId, Integer bonoid, String tipoBono, double bonoEmpleado, double sueldoTotal){
//arreglar if (debe encontrar el id de empleado en bono u el id del bono en empleado dependiendo de como funciona bono)
    /*if(empleadoId.findByBonoid() == null){
    system.out.println("El empleado no cuenta con ningun bono")
    }else*/
    sueldoTotal = sueldoTotal + bonoEmpleado;
    System.out.println("El bono '"+tipoBono+"' Se añadio al sueldo.\nBono: +"+bonoEmpleado);
    
    return sueldoTotal;

}

/* calculoAFP (Terminado)
Usa Switch Case para optimizar la busquedad de afp y hacer el calculo, utilizando "tolowecase" para evitar confuciones entre uno Uno y variaciones*/
public double calculoAFP(Integer empleadoId, String AFP, double sueldoBase){
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
            break;
    }
    // se entrega lo que descuenta el afp EJ: "descuento afp: -5000", y luego da el total con el descuento, math.abs encuentra cual fue la resta u suma entre 2 numeros.
    System.out.println("\nDescuento AFP: "+(Math.abs(sueldoBase-sueldoTotal))+"\nTotal: "+sueldoTotal);
    return sueldoTotal;
     
}



}
