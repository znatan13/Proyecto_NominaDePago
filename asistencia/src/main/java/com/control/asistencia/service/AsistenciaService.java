package com.control.asistencia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.control.asistencia.dto.AsistenciaCompletaDTO;
import com.control.asistencia.model.Asistencia;
import com.control.asistencia.model.Empleado;
import com.control.asistencia.model.Turnos;
import com.control.asistencia.repository.AsistenciaRepository;

@Service
public class AsistenciaService {

    private final AsistenciaRepository repository;

    public AsistenciaService(AsistenciaRepository repository){
        this.repository = repository;
    }

    public List<Asistencia> listarAsistencias(){
        return repository.findAll();
    }

    public void validarRegistroAsistencia(Asistencia asistencia){

        if(asistencia == null){
            throw new IllegalArgumentException("La asistencia no puede estar nula");
        }
        if(!"atrasado".equals(asistencia.getEstadoAsistencia().toLowerCase()) && !"puntual".equals(asistencia.getEstadoAsistencia().toLowerCase()) 
            && !"ausente".equals(asistencia.getEstadoAsistencia().toLowerCase())){
            throw new IllegalArgumentException("El estado de la asistencia debe ser: puntual, atrasado o ausente");
        }
        if(asistencia.getTurnoId() == null || asistencia.getTurnoId() <= 0){
            throw new IllegalArgumentException("El id de turno debe ser mayor a 0");
        }
        if(asistencia.getEmpleadoId() == null || asistencia.getEmpleadoId() <= 0){
            throw new IllegalArgumentException("El id del empleado deber mayor a 0");
        }
        
        if(asistencia.getObservaciones() != null && asistencia.getObservaciones().length() > 200){
            throw new IllegalArgumentException("El mensaje para el empleado debe ser menor a 200 caracteres");
        }
        if(asistencia.getFechaRegistro() == null){
            throw new IllegalArgumentException("La fecha de registro de la asistencia no debe ser nula");
        }
    }
    public Asistencia crearRegistro (Asistencia asistencia){
        RestTemplate restTemplate = new RestTemplate();
        validarRegistroAsistencia(asistencia);
        
        try{
            String url = "http://localhost:8081/empleados/buscar/id/" + asistencia.getEmpleadoId();
            Empleado empleado = restTemplate.getForObject(url, Empleado.class);

            if(empleado == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "empleado no existe");
            }
        }catch(HttpClientErrorException error){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id empleado no existente para registrar asistencia, Ingrese otro");
        }

        try{
            String url = "http://localhost:8082/turnos/buscar/turnoid/" + asistencia.getTurnoId();
            Turnos turnos = restTemplate.getForObject(url, Turnos.class);

            if(turnos == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "turno no registrado");
            }
        }catch(HttpClientErrorException error){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "turno no registrado, ingrese otro valido");
        }
        return repository.save(asistencia);
    }
    public Asistencia buscarAsistencia(Integer id){
        if (id == null || id <= 0){
            throw new IllegalArgumentException("El id no debe ser nulo y debe ser mayor a 0");
        }
        Optional<Asistencia> buscarAsistencia = repository.findById(id);
        if(buscarAsistencia.isEmpty()){
            throw new RuntimeException("La asistencia con id: " + id +  " no existe");
        }
        return buscarAsistencia.get();
    }
    public Asistencia actualizarAsistencia(Integer id, Asistencia asistenciaActualizada){
        if(id == null){
            throw new IllegalArgumentException("El id para actualizar no debe ser nulo");    
        }
        Optional<Asistencia> existe = repository.findById(id);
        validarRegistroAsistencia(asistenciaActualizada);
        if(existe.isPresent() && asistenciaActualizada != null){

            Asistencia asistencia = existe.get();
            asistencia.setEmpleadoId(asistenciaActualizada.getEmpleadoId());
            asistencia.setTurnoId(asistenciaActualizada.getTurnoId());
            asistencia.setFechaRegistro(asistenciaActualizada.getFechaRegistro());
            asistencia.setObservaciones(asistenciaActualizada.getObservaciones());
            asistencia.setEstadoAsistencia(asistenciaActualizada.getEstadoAsistencia());

            return repository.save(asistencia);
        }
        throw new RuntimeException("No se pudo actualizar ese id " + id + " de asistencia intente de nuevo");
    }
    public void eliminarRegistro(Integer id){
        if(id == null){
            throw new IllegalArgumentException("El id no puede venir nulo");
        }
        if(repository.existsById(id)){
            repository.deleteById(id);
        }else{
            throw new RuntimeException("El id: " + id + " que desea eliminar no existe ");
        }

    }

    public AsistenciaCompletaDTO obtenerAsistenciaCompleta(Integer empleadoId, Integer turnoId){

        if(empleadoId == null || empleadoId <= 0){
            throw new IllegalArgumentException("El id de empleado no debe ser nulo y debe ser mayor a 0 ");
        }
        if(turnoId == null || turnoId <= 0){
            throw new IllegalArgumentException("El id de turno no debe ser nulo y debe ser mayor a 0 ");

        }
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/empleados/buscar/id/" + empleadoId;
        Empleado empleado = restTemplate.getForObject(url, Empleado.class);
        if(empleado == null){
            throw new RuntimeException("El empleado no tiene registro u no existe");
        }


        String urlTurno = "http://localhost:8082/turnos/buscar/turnoid/" + turnoId;
        Turnos turno = restTemplate.getForObject(urlTurno, Turnos.class);
        if(turno == null){
            throw new RuntimeException("No existe el turno u no esta registrado");
        }

        List<Asistencia> listarAsistencias = repository.findByEmpleadoIdAndTurnoId(empleadoId, turnoId);

        AsistenciaCompletaDTO asistenciaCompletaDTO = new AsistenciaCompletaDTO();
        asistenciaCompletaDTO.setNombre(empleado.getNombre());
        asistenciaCompletaDTO.setApellido(empleado.getApellido());
        asistenciaCompletaDTO.setEmail(empleado.getEmail());
        asistenciaCompletaDTO.setCargo(empleado.getCargo());
        asistenciaCompletaDTO.setFecha(turno.getFecha());
        asistenciaCompletaDTO.setHoraInicio(turno.getHoraInicio());
        asistenciaCompletaDTO.setHoraFin(turno.getHoraFin());
        
        asistenciaCompletaDTO.setAsistencia(listarAsistencias);

        return asistenciaCompletaDTO;
        
    }

}
