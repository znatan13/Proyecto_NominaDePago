package com.historial.trabajadores.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.historial.trabajadores.dto.EmpleadoDTO;
import com.historial.trabajadores.model.Empleado;
import com.historial.trabajadores.repository.EmpleadoRepository;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository repository;

    // Metodo get listar empleado
    public List<Empleado> listar() {
        return repository.findAll();
    }

    // Metodo get buscar empleado por id
    public Optional<Empleado> buscarPorId(Integer id) {
        if (id != null && id > 0) { // valido que el id sea mayor a 0
            return repository.findById(id);
        }
        return Optional.empty();

    }

    // Metodo get buscar por rut
    public Optional<Empleado> buscarPorRut(String rut) {
        // Rut: Que no sea nulo y que no tenga espacios en blanco
        if (rut == null || rut.isBlank()) {
            return Optional.empty();
        }
        return repository.findByRut(rut);
    }

    // Metodo get buscar por email.
    public Optional<Empleado> buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return repository.findByEmailIgnoreCase(email);
    }

    // Metodo get buscar Por numeroTelefono
    public Optional<Empleado> buscarPorNumeroTelefono(String numeroTelefono) {
        if (numeroTelefono == null || numeroTelefono.isBlank()) {
            return Optional.empty();
        }
        return repository.findByNumeroTelefono(numeroTelefono);
    }

    // Metodo post crear un empleado
    public Optional<Empleado> crearEmpleado(Empleado empleado) {

        // 1ero validamos que no este null
        if (empleado == null) {
            return Optional.empty();
        }
        // Consultamos en la base de datos si existe
        Optional<Empleado> existeRut = repository.findByRut(empleado.getRut());
        Optional<Empleado> existeEmail = repository.findByEmailIgnoreCase(empleado.getEmail());
        Optional<Empleado> existeNumeroTelefono = repository.findByNumeroTelefono(empleado.getNumeroTelefono());

        if (existeRut.isPresent() || existeEmail.isPresent() || existeNumeroTelefono.isPresent()) {
            return Optional.empty();
        }
        repository.save(empleado);
        return Optional.of(empleado);

    }

    // Metodo put actualizar por id
    public Optional<Empleado> actualizarPorId(Integer id, Empleado empleadoNuevo) {
        if (id == null) {
            return Optional.empty();
        }
        Optional<Empleado> existe = repository.findById(id);
        if (existe.isEmpty()) {
            return Optional.empty();
        }
        Empleado empleado = existe.get();
        empleado.setRut(empleadoNuevo.getRut());
        empleado.setNombre(empleadoNuevo.getNombre());
        empleado.setApellido(empleadoNuevo.getApellido());
        empleado.setEmail(empleadoNuevo.getEmail());
        empleado.setNumeroTelefono(empleadoNuevo.getNumeroTelefono());
        empleado.setDireccion(empleadoNuevo.getDireccion());
        empleado.setEstadoCivil(empleadoNuevo.getEstadoCivil());
        empleado.setFechaIngreso(empleadoNuevo.getFechaIngreso());
        empleado.setFechaSalida(empleadoNuevo.getFechaSalida());
        empleado.setCargo(empleadoNuevo.getCargo());
        empleado.setEstado(empleadoNuevo.getEstado());
        empleado.setSueldoBase(empleadoNuevo.getSueldoBase());

        repository.save(empleado);
        return Optional.of(empleado);
    }

    // Metodo delete eliminar por id
    public boolean eliminarEmpleado(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
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
    public Optional<EmpleadoDTO> buscarIdDTO(Integer id) {
        Optional<Empleado> empleados = repository.findById(id);
        if (empleados.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(empleadoDto(empleados.get()));
    }

    // Metodo DTO buscar por rut
    public Optional<EmpleadoDTO> buscarRutDTO(String rut) {
        Optional<Empleado> empleados = repository.findByRut(rut);
        if (empleados.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(empleadoDto(empleados.get()));
    }

    // metodo DTO buscar por email
    public Optional<EmpleadoDTO> buscarEmailDTO(String email) {
        Optional<Empleado> empleados = repository.findByEmailIgnoreCase(email);
        if (empleados.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(empleadoDto(empleados.get()));
    }

    // metodo DTO buscar por numero telefono
    public Optional<EmpleadoDTO> buscarNumeroTelefonoDTO(String numeroTelefono) {
        Optional<Empleado> empleados = repository.findByNumeroTelefono(numeroTelefono);
        if (empleados.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(empleadoDto(empleados.get()));
    }
}
