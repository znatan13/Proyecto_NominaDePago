package com.control.asistencia.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    //get listar 
    public List<Asistencia> listarAsistencias(){
        return repository.findAll();
    }

    //Metodo reautilizable
    public void validarRegistroAsistencia(Asistencia asistencia){

        if(asistencia == null){
            throw new IllegalArgumentException("La asistencia no puede estar nula");
        }
        if(!"Atrasado".equals(asistencia.getEstadoAsistencia()) && !"Puntual".equals(asistencia.getEstadoAsistencia()) 
            && !"Ausente".equals(asistencia.getEstadoAsistencia())){
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
    //metodo post crear un registro de asistencia
    public Asistencia crearRegistro (Asistencia asistencia){
        validarRegistroAsistencia(asistencia);
        return repository.save(asistencia);
    }
    //get buscar una asistencia por id
    public Asistencia buscarAsistencia(Integer id){
        if (id == null || id <= 0){
            throw new IllegalArgumentException("El id no debe ser nulo y debe ser mayor a 0");
        }
        Optional<Asistencia> buscarAsistencia = repository.findById(id);
        if(buscarAsistencia.isEmpty()){
            throw new RuntimeException("La asistencia con id: " + id +  "no existe");
        }
        return buscarAsistencia.get();
    }
    //put actualizar asistencia por el id
    public Asistencia actualizarAsistencia(Integer id, Asistencia asistenciaActualizada){
        if(id == null){
            throw new IllegalArgumentException("El id para actualizar no debe ser nulo");    
        }
        //consultamos si existe o no el id de asistencia
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
        throw new RuntimeException("No se pudo actualizar ese id " + id + "de asistencia intente de nuevo");
    }
    //metodo eliminar una asistencia (no muy recomendable)
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


    //-----Metodo DTO entre entidades para conectar microservicios----//
    public AsistenciaCompletaDTO obtenerAsistenciaCompleta(Integer empleadoId, Integer turnoId){

        // se asegura que no sea nulo los ids
        if(empleadoId == null || empleadoId <= 0){
            throw new IllegalArgumentException("El id de empleado no debe ser nulo y debe ser mayor a 0 ");
        }
        if(turnoId == null || turnoId <= 0){
            throw new IllegalArgumentException("El id de turno no debe ser nulo y debe ser mayor a 0 ");

        }
        RestTemplate restTemplate = new RestTemplate();
        //buscamos el empleado por id
        String url = "http://localhost:8081/empleados/buscar/id/" + empleadoId;
        Empleado empleado = restTemplate.getForObject(url, Empleado.class);
        if(empleado == null){
            throw new RuntimeException("El empleado no tiene registro u no existe ");
        }

        //Buscamos el turno al igual que empleado
        String urlTurno = "http://localhost:8082/turnos/buscar/turnoid/" + turnoId;
        Turnos turno = restTemplate.getForObject(urlTurno, Turnos.class);
        if(turno == null){
            throw new RuntimeException("No existe el turno u no esta registrado");
        }

        List<Asistencia> listarAsistencias = repository.findByEmpleadoIdAndTurnoId(empleadoId, turnoId);

        AsistenciaCompletaDTO asistenciaCompletaDTO = new AsistenciaCompletaDTO();
        //completamos los datos dto del empleado
        asistenciaCompletaDTO.setNombre(empleado.getNombre());
        asistenciaCompletaDTO.setApellido(empleado.getApellido());
        asistenciaCompletaDTO.setEmail(empleado.getEmail());
        asistenciaCompletaDTO.setCargo(empleado.getCargo());
        //completamos los datos dto de turno
        asistenciaCompletaDTO.setFecha(turno.getFecha());
        asistenciaCompletaDTO.setHoraInicio(turno.getHoraInicio());
        asistenciaCompletaDTO.setHoraFin(turno.getHoraFin());
        
        asistenciaCompletaDTO.setAsistencia(listarAsistencias);

        return asistenciaCompletaDTO;
        
    }

}
