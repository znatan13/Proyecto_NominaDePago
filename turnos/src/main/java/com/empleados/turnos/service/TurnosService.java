package com.empleados.turnos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.empleados.turnos.dto.EmpleadoSimple;
import com.empleados.turnos.model.Empleado;
import com.empleados.turnos.model.Turnos;
import com.empleados.turnos.repository.TurnoRepository;

@Service
public class TurnosService {
    
    private final TurnoRepository repository;

    public TurnosService(TurnoRepository repository){
        this.repository = repository;
    }

    public List<Turnos> listar() {
        return repository.findAll();
    }

    public Turnos buscarPorId(Integer turnoId) {

        if (turnoId == null || turnoId <= 0){
            throw new IllegalArgumentException("El id que busca no debe ser nulo y debe ser mayor a 0");
        }

        Optional<Turnos> buscarTurno = repository.findById(turnoId);

        if (buscarTurno.isEmpty()) {
            throw new RuntimeException("El Turno no existe");
        }
        return buscarTurno.get();
    }
    public List<Turnos> buscarIdEmpleado(Integer empleadoId) {

        if(empleadoId == null || empleadoId <= 0){
            throw new IllegalArgumentException("El id del empleado no debe ser nulo y debe ser mayor a 0");
        }
        List<Turnos> buscarTurnoEmpleado = repository.findByEmpleadoId(empleadoId);
      
        if(buscarTurnoEmpleado.isEmpty()){
            throw new RuntimeException("No existe un turno para ese id de empleado");
        }
        return buscarTurnoEmpleado;
        
    }

    public void validarTurno(Turnos turno){

        if(turno == null){
            throw new IllegalArgumentException("El turno no puede ser nulo");
        }
        if(!"trabajando".equals(turno.getEstado().toLowerCase()) && !"descansando".equals(turno.getEstado().toLowerCase())){
            throw new IllegalArgumentException("El estado del turno debe ser trabajando o descansando");
        }

    }

    public Turnos crearTurno(Turnos turno) {
        RestTemplate restTemplate = new RestTemplate();
        validarTurno(turno);

        try{

            String url = "http://trabajadores:8081/empleados/buscar/id/" +  turno.getEmpleadoId();
            Empleado empleado = restTemplate.getForObject(url, Empleado.class);

            if(empleado == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id no existe");
            }

        }catch(HttpClientErrorException error){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El Id de Empleado no existe, Ingrese otro valido");
        }
        
        return repository.save(turno);
    }

    public Turnos actualizarId(Integer turnoId, Turnos turnoNuevo) {
        if (turnoId == null) {
            throw new RuntimeException("El id que desea actualizar no existe");
        }
        Optional<Turnos> existe = repository.findById(turnoId);

        validarTurno(turnoNuevo); 

        if (existe.isPresent() && turnoNuevo != null) {
            Turnos turnos = existe.get();
            turnos.setFecha(turnoNuevo.getFecha());
            turnos.setHoraInicio(turnoNuevo.getHoraInicio());
            turnos.setHoraFin(turnoNuevo.getHoraFin());
            turnos.setEstado(turnoNuevo.getEstado());

            return repository.save(turnos);
        }
        throw new RuntimeException("No existe ese Id de turno");
    }

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

    public EmpleadoSimple obtenerTurnosConEmpleados(Integer empleadoId){

       if(empleadoId == null || empleadoId <= 0){
            throw new IllegalArgumentException("El id no debe ser nulo y debe ser mayor a 0");
       }
       
       RestTemplate restTemplate = new RestTemplate();

       String url = "http://trabajadores:8081/empleados/buscar/id/" + empleadoId;
       Empleado empleado = restTemplate.getForObject(url, Empleado.class);
       
       if(empleado == null){
            throw new RuntimeException("El id del empleado no existe");
       }

       List<Turnos> listarTurnosEmpleado = repository.findByEmpleadoId(empleadoId);
       EmpleadoSimple empleadoSimple = new EmpleadoSimple();
       empleadoSimple.setRut(empleado.getRut());
       empleadoSimple.setNombre(empleado.getNombre());
       empleadoSimple.setApellido(empleado.getApellido());
       empleadoSimple.setCargo(empleado.getCargo());

       empleadoSimple.setTurnos(listarTurnosEmpleado);

       return empleadoSimple;
    }
}
