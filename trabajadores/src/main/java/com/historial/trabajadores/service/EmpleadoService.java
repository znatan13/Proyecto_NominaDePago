package com.historial.trabajadores.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.historial.trabajadores.dto.EmpleadoDTO;
import com.historial.trabajadores.model.Empleado;
import com.historial.trabajadores.repository.EmpleadoRepository;

@Service
public class EmpleadoService {
    
    private final EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    // Metodo get listar empleado
    public List<Empleado> listar() {
        return repository.findAll();
    }

    // metodo reatilizable para validar el empleado, se usa en crear y actualizar
    public void validarEmpleado(Empleado empleadoNuevo){
        if(empleadoNuevo == null){
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }
        if(empleadoNuevo.getSueldoBase() <= 0){
            throw new IllegalArgumentException("El sueldo base no puede ser negativo");
        }
        if(!"Activo".equals(empleadoNuevo.getEstado()) && !"Inactivo".equals(empleadoNuevo.getEstado())){
            throw new IllegalArgumentException("El empleado debe tener un estado activo o inactivo");
        }
        if("Activo".equals(empleadoNuevo.getEstado()) && empleadoNuevo.getFechaDeBaja() != null){
            throw new IllegalArgumentException("El empleado activo no puede tener fecha de baja");
        }
        if ("Inactivo".equals(empleadoNuevo.getEstado()) && empleadoNuevo.getFechaDeBaja() == null){
            throw new IllegalArgumentException("El empleado inactivo debe tener una fecha de baja");
        }
    }

    // Metodo post crear un empleado
    public Empleado crearEmpleado(Empleado empleadoNuevo) {
        validarEmpleado(empleadoNuevo);
        return repository.save(empleadoNuevo);
    }

    // Metodo put actualizar por id
    public Empleado actualizarPorId(Integer id, Empleado empleadoNuevo) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede estar null");
        }

        Optional<Empleado> existe = repository.findById(id);
        if (existe.isEmpty()) {
            throw new RuntimeException("El id que desea actualizar no existe");
        }

        validarEmpleado(empleadoNuevo); // validamos el empleado del metodo reautilzado
        
        Empleado empleado = existe.get();
        empleado.setRut(empleadoNuevo.getRut());
        empleado.setNombre(empleadoNuevo.getNombre());
        empleado.setApellido(empleadoNuevo.getApellido());
        empleado.setEmail(empleadoNuevo.getEmail());
        empleado.setNumeroTelefono(empleadoNuevo.getNumeroTelefono());
        empleado.setDireccion(empleadoNuevo.getDireccion());
        empleado.setEstadoCivil(empleadoNuevo.getEstadoCivil());
        empleado.setFechaContrato(empleadoNuevo.getFechaContrato());
        empleado.setFechaDeBaja(empleadoNuevo.getFechaDeBaja());
        empleado.setCargo(empleadoNuevo.getCargo());
        empleado.setEstado(empleadoNuevo.getEstado());
        empleado.setSueldoBase(empleadoNuevo.getSueldoBase());
        empleado.setAfp(empleadoNuevo.getAfp());

       return repository.save(empleado);
    }

    // Metodo delete eliminar por id
    public void eliminarEmpleado(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }else{
            throw new RuntimeException("El id que desea eliminar no existe");
        }
    }

    // ------ Metodos DTO ---------
    // Metodo para reutilizar codigo en los get buscar.
    public EmpleadoDTO empleadoDto(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setEmail(empleado.getEmail());
        dto.setCargo(empleado.getCargo());
        dto.setEstadoCivil(empleado.getEstadoCivil());
        return dto;
    }

    // Metodo DTO buscar por id
    //muy util porque el id es unico de cada empleado
    public EmpleadoDTO buscarIdDTO(Integer id) {
        if (id == null || id <= 0){
            throw new IllegalArgumentException("El id no puede ser nulo ni menor o igual a 0");
        }
        // buscamos el id y validamos que exista para luego hacerlo en DTO
        Optional<Empleado> empleadoBuscado = repository.findById(id);
        if (empleadoBuscado.isEmpty()) {
            throw new RuntimeException("El id que desea buscar no existe");  
        }
        return empleadoDto(empleadoBuscado.get());
    }

    // Metodo DTO buscar por rut
    //buscar rut hace similitud con id porque rut es unico de cada empleado.
    public EmpleadoDTO buscarRutDTO(String rut) {
        if(rut == null || rut.isBlank()){
            throw new IllegalArgumentException("El rut del empleado no puede estar en nulo ni en blanco");
        }
        Optional<Empleado> empleadoBuscado = repository.findByRut(rut);
        if (empleadoBuscado.isEmpty()) {
            throw new RuntimeException("El rut que desea buscar no existe");
        }
        return empleadoDto(empleadoBuscado.get());
    }

    // metodo DTO buscar por email
    //el email es unico y mas adelante cuando tengamos tokens lo conectamos con el login
    public EmpleadoDTO buscarEmailDTO(String email) {
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("El email del empleado no puede estar en nulo ni en blanco");
        }
        Optional<Empleado> empleadoBuscado = repository.findByEmailIgnoreCase(email);
        if (empleadoBuscado.isEmpty()) {
            throw new RuntimeException("El email que desea buscar no existe");
        }
        return empleadoDto(empleadoBuscado.get());
    }
}
