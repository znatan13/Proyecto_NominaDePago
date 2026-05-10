package com.empleados.turnos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empleados.turnos.model.Turnos;
import com.empleados.turnos.repository.TurnoRepository;

@Service
public class TurnosService {
    @Autowired
    private TurnoRepository repository;

    // Metodo Get: listar todos los turnos
    public List<Turnos> listar() {
        return repository.findAll();
    }

    // Metodo Get: buscar por id
    public Turnos buscarPorId(Integer turnoId) {

        Optional<Turnos> buscarTurno = repository.findById(turnoId);

        if (buscarTurno == null) { // si el id no es nulo y mayor a 0 lo busca
            throw new RuntimeException("El id no existe");
        }
        return buscarTurno.get();
    }

    // Metodo get: buscar por id del empleado
    public Optional<Turnos> buscarIdEmpleado(Integer empleadoId) {

        if (empleadoId != null && empleadoId > 0) {
            return repository.findByEmpleadoId(empleadoId);
        }
        return Optional.empty();
    }

    // Metodo post crear un turno
    public Optional<Turnos> crearTurno(Turnos turno) {
        /*
         * No se valida duplicados aqui ni en la base de datos;porque el mismo empleado
         * puede tener muchos turnos de
         * trabajo. nada es unico en este microservicio excepto el id turno que lo
         * asigna la base de datos. solo validamos que no sea nulo
         * ni que el id del empleado dea menor a 0
         */
        if (turno != null && turno.getEmpleadoId() != null && turno.getEmpleadoId() > 0) {
            repository.save(turno);
            return Optional.of(turno);
        }
        return Optional.empty();
    }

    // Metodo Put; Actualizar un turno.
    public Turnos actualizarId(Integer turnoId, Turnos turnoNuevo) {
        if (turnoId == null) {
            throw new RuntimeException("El id que desea actualizar no existe");
        }
        Optional<Turnos> existe = repository.findById(turnoId);

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
    public boolean eliminarPorId(Integer turnoId) {
        if (repository.existsById(turnoId)) {
            repository.deleteById(turnoId);
            return true;
        }
        throw new RuntimeException("El id que quiere eliminar no existe");
    }

}
