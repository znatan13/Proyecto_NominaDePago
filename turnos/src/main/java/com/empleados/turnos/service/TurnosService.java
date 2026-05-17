package com.empleados.turnos.service;


import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.empleados.turnos.model.Empleado;
import com.empleados.turnos.model.Turnos;
import com.empleados.turnos.repository.TurnoRepository;

@Service
public class TurnosService {
    
    private final TurnoRepository repository;

    public TurnosService(TurnoRepository repository){
        this.repository = repository;
    }

    // Metodo Get: listar todos los turnos
    public List<Turnos> listar() {
        return repository.findAll();
    }

    // Metodo Get: buscar por id
    //buscamos id turno para mostrar datos del turno.
    public Turnos buscarPorId(Integer turnoId) {
        //evitamos nullPointerException
        if (turnoId == null || turnoId <= 0){
            throw new IllegalArgumentException("El id que busca no debe ser nulo y debe ser mayor a 0");
        }
        //Buscamos si existe el turno por id
        Optional<Turnos> buscarTurno = repository.findById(turnoId);
        // si no existe lanzamos una exception
        if (buscarTurno.isEmpty()) { // si el id no es nulo y mayor a 0 lo busca
            throw new RuntimeException("El Turno no existe");
        }
        return buscarTurno.get();
    }

    // Metodo get: buscar por id del empleado
    //buscamos id del empleado para mostrar los turnos de ese empleado.
    public List<Turnos> buscarIdEmpleado(Integer empleadoId) {

        if(empleadoId == null || empleadoId <= 0){
            throw new IllegalArgumentException("El id del empleado no debe ser nulo y debe ser mayor a 0");
        }
        List<Turnos> buscarTurnoEmpleado = repository.findByEmpleadoId(empleadoId);
        // isEmpty dice que no existe
        if(buscarTurnoEmpleado.isEmpty()){
            throw new RuntimeException("No existe un turno para ese id de empleado");
        }
        return buscarTurnoEmpleado;
        
    }

    //metodo para validar datos de turnos y se va a reautilizar
    public void validarTurno(Turnos turno){

        if(turno == null){
            throw new IllegalArgumentException("El turno no puede ser nulo");
        }
        if (turno.getEmpleadoId() == null || turno.getEmpleadoId() <= 0){
            throw new IllegalArgumentException("Los datos del turno no son validos");
        }
        if(!"Trabajando".equals(turno.getEstado()) && !"Descansando".equals(turno.getEstado())){
            throw new IllegalArgumentException("El estado del turno debe ser trabajando o descansando");
        }

    }

    // Metodo post crear un turno
    public Turnos crearTurno(Turnos turno) {
        /*
         * No se valida duplicados aqui ni en la base de datos;porque el mismo empleado
         * puede tener muchos turnos de
         * trabajo. nada es unico en este microservicio excepto el id turno que lo
         * asigna la base de datos.
         */
        validarTurno(turno);
        return repository.save(turno);
    }

    // Metodo Put; Actualizar un turno.
    public Turnos actualizarId(Integer turnoId, Turnos turnoNuevo) {
        if (turnoId == null) {
            throw new RuntimeException("El id que desea actualizar no existe");
        }
        Optional<Turnos> existe = repository.findById(turnoId);

        validarTurno(turnoNuevo); //Validamos el turno del metodo reautilizado

        if (existe.isPresent() && turnoNuevo != null) {
            Turnos turnos = existe.get();
            turnos.setEmpleadoId(turnoNuevo.getEmpleadoId());
            turnos.setFecha(turnoNuevo.getFecha());
            turnos.setHoraInicio(turnoNuevo.getHoraInicio());
            turnos.setHoraFin(turnoNuevo.getHoraFin());
            turnos.setEstado(turnoNuevo.getEstado());

            return repository.save(turnos);
        }
        throw new RuntimeException("No existe ese Id de turno");
    }

    // Metodo delete : eliminar turno por id
    public void  eliminarPorId(Integer turnoId) {

        if(turnoId == null){
            throw new IllegalArgumentException("El id que desea eliminar no puede ser nulo");
        }
        if (repository.existsById(turnoId)) {
            repository.deleteById(turnoId);
            
        }else{
            throw new RuntimeException("El id que quiere eliminar no existe");
        }  
    }

    //Metodo para unir turnos con empleados solo por el id
    //hacemos una relacion un empleado puede tener muchos turnos.
    public Empleado obtenerTurnosConEmpleados(Integer empleadoId){

       if(empleadoId == null || empleadoId <= 0){
            throw new IllegalArgumentException("El id no debe ser nulo y debe ser mayor a 0");
       }
       
       RestTemplate restTemplate = new RestTemplate();

       String url = "http://localhost:8081/empleados/buscar/id/" + empleadoId;
       Empleado empleado = restTemplate.getForObject(url, Empleado.class);

       return empleado;
    }
}
