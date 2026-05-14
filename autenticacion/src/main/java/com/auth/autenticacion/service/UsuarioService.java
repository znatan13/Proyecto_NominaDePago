package com.auth.autenticacion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.autenticacion.dto.BuscarDatosSegurosDTO;
import com.auth.autenticacion.dto.LoginSeguroDTO;
import com.auth.autenticacion.dto.UsuarioSeguroDTO;
import com.auth.autenticacion.model.Usuario;
import com.auth.autenticacion.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * Metodo (get) listar todas las cuentas de usuarios,
     * si el administrador lo requiere, puede retornar en el historial
     */
    public List<Usuario> listar() {
        return repository.findAll();
    }

    // Get : buscar si existe email 
    public Optional<Usuario> buscarEmail(String email) {

        // Valida que no sea null, que no este en vacio ni que contenga espacios
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return repository.findByEmail(email);
    }

    // Get buscar por id
    public Usuario buscarPorId(Integer id) {
        Optional<Usuario> existeId = repository.findById(id);
        if (existeId.isPresent()) {
           return existeId.get();
            // si el Id null, retornara un mensaje de excepcion
        }
        throw new RuntimeException("El id no existe");
    }

    // Post: Metodo crear cuenta usuario
    public Usuario crear(Usuario usuario) {

        /*
         * Evito controlar duplicados, para que actue la validacion de la base de datos
         */
        // Validamos que los datos ingresados no sea null y que no tenga espacios.
        if (usuario == null) {
            return null;
        }
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isBlank()) {
            return null;
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            return null;
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            return null;
        }
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        return repository.save(usuario);
    }

    // Metodo put: Actualizar por id -> permitira actualizar todo lo que se requiera
    public Usuario actualizarPorId(Integer id, Usuario usuario) {
        
        Optional<Usuario> existe = repository.findById(id);
        if (existe.isEmpty()) {
            throw new RuntimeException("El Id no existe");
        }

        Usuario usuarios = existe.get();
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        
        usuarios.setNombreUsuario(usuario.getNombreUsuario());
        usuarios.setEmail(usuario.getEmail());
        usuarios.setPassword(passwordEncriptada); // se encripta la contraseña antes de actualizarla
        usuarios.setRol(usuario.getRol());
        return repository.save(usuarios);
    }

    // Delete: Metodo eliminar usuario
    public boolean eliminar(Integer id) {
        if (id == null) {
            throw new RuntimeException("El id que busca para eliminar no existe"); 
            //si el id llega ser nulo, retorna un mensaje de exception
        }
        if (repository.existsById(id)) { // pregunta ¿existe el id? si existe lo elimina
            repository.deleteById(id);
            return true; // deleteBtId -> es un void
        }
        throw new RuntimeException("El id que busca para eliminar no existe");
        // si el id no existe, retornara un mensaje de excepcion
    }

    // -------------------- DTO uso exclusivos --------------------------------
    // DTO listar solo datos No sensibles del usuario
    public List<UsuarioSeguroDTO> usuariosSeguros() {
        List<Usuario> usuarios = repository.findAll(); // llamo todo lo de usuario en la base de datos
        List<UsuarioSeguroDTO> lista = new ArrayList<>(); // se crea una lista vacia que contrenda dto

        for (Usuario u : usuarios) { // se recorre los usuarios

            UsuarioSeguroDTO dto = new UsuarioSeguroDTO();

            dto.setNombreUsuario(u.getNombreUsuario());
            dto.setEmail(u.getEmail());
            // Email y nombre solo se copian esos datos para los dto.
            lista.add(dto);

        }
        return lista; // Se retorna la lista con los dto listos
    }

    // DTO login seguro se evita filtrar la contraseña.
    public Optional<LoginSeguroDTO> loginSeguro(String nombreUsuario, String password) {

        if (nombreUsuario == null || nombreUsuario.isBlank() || password == null || password.isBlank()) {
            return Optional.empty();
        }

        Optional<Usuario> usuarioEncontrado = repository.findByNombreUsuario(nombreUsuario);
        LoginSeguroDTO dto = new LoginSeguroDTO();
        // Antes de llamar el usuario con todo sus atributos verificamos que venga vacio
        if (usuarioEncontrado.isEmpty()) {
            return Optional.empty();
        }
        Usuario usuario = usuarioEncontrado.get();

        if (password.equals(usuario.getPassword())) {
            dto.setNombreUsuario(usuario.getNombreUsuario());
            dto.setEmail(usuario.getEmail());
            dto.setRol(usuario.getRol());

            return Optional.of(dto); // dice si sale todo ok? entra al return

        }
        return Optional.empty(); // si no retornara vacio.
    }

    /*
     * hago un Metodo para reutilizar codigo porque DTO de los 2 buscar
     * email y id se repetia logica.
     */
    public BuscarDatosSegurosDTO buscarDatos(Usuario usuario) {
        BuscarDatosSegurosDTO dto = new BuscarDatosSegurosDTO();
        dto.setId(usuario.getId());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        return dto;
    }

        /*
         * Al utilizar el metodo buscarDatos estamos reutilizando codigo
         * se utilizara para los otros 2 metodos buscar id y email, porque se repite
         * logica
         */
    // DTO para buscar email.
    public Optional<BuscarDatosSegurosDTO> buscarEmailDTO(String email) {

        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        Optional<Usuario> users = repository.findByEmail(email);

        if (users.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buscarDatos(users.get()));
    }

    // DTO para buscar id.
    // al utilizar DTO para buscar id, evitamos mostrar datos sensibles 
    public UsuarioSeguroDTO buscarIdDTO(Integer id) {

        if (id == null) {
            throw new RuntimeException("El id no existe");
        }
        Optional<Usuario> users = repository.findById(id);

        if(users.isEmpty()){
            throw new RuntimeException("El id no existe");
        }

        Usuario usuario = users.get();
        UsuarioSeguroDTO dto = new UsuarioSeguroDTO();
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setEmail(usuario.getEmail());

        return dto;

    }


}
