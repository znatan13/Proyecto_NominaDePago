package com.nomina.nominaPago.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nomina.nominaPago.model.Empleado;

@Service
public class NominaService {
private double sueldoTotal;
// SueldoBase= 540000


/* La informacion de la nomina */
public List<Empleado> nomina(int empleadoId){
    return null;
}

/* calculo del bono (en progreso) 
Se abre despues de calculoAFP ya que es un bono*/
public double calculoBono(int id, String tipoBono, double bonoEmpleado, double sueldoTotal){
    sueldoTotal= sueldoTotal+sueldoTotal*bonoEmpleado;
    System.out.println("El bono '"+tipoBono+"' Se añadio al sueldo.");
    return sueldoTotal;

}

/* calculoAFP (en progreso) 
Usa Switch Case para optimizar la busquedad de afp y hacer el calculo*/
public double calculoAFP(int id, int AFP, double sueldoBase){
    switch(AFP){
        case 1:
            sueldoTotal= sueldoBase - sueldoBase*0.046;
            System.out.println("AFP Uno: 0,46%");
            break;
        case 2:
            sueldoTotal= sueldoBase - sueldoBase*0.058;
            System.out.println("AFP Modelo: 0,58%");
            break;
        case 3:
            sueldoTotal= sueldoBase - sueldoBase*0.116;
            System.out.println("AFP Planvital: 1,16%");
            break;
        case 4:
            sueldoTotal= sueldoBase - sueldoBase*0.127;
            System.out.println("AFP Habitat: 1,27%");
            break;
        case 5:
            sueldoTotal= sueldoBase - sueldoBase*0.144;
            System.out.println("AFP Capital: 1,44%");
            break;
        case 6:
            sueldoTotal= sueldoBase - sueldoBase*0.144;
            System.out.println("AFP Cuprum: 1,44%");
            break;
        case 7:
            sueldoTotal= sueldoBase - sueldoBase*0.145;
            System.out.println("AFP Provida: 1,45%");
            break;
    }

    calculoBono(id, "test", 1, sueldoBase);
     return sueldoTotal;

}



}
