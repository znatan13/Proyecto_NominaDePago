package com.auth.autenticacion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.autenticacion.dto.BuscarDatosSegurosDTO;
import com.auth.autenticacion.dto.LoginSeguroDTO;
import com.auth.autenticacion.dto.UsuarioSeguroDTO;
import com.auth.autenticacion.model.Usuario;
import com.auth.autenticacion.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    //contrustor para inyectar dependencias y no usar autowired
    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    /*
     * Metodo (get) listar todas las cuentas de usuarios,
     * si el administrador lo requiere, puede retornar en el historial
     */
    public List<Usuario> listar() {
        return repository.findAll();
    } 
    
    // Post: Metodo crear cuenta usuario
    public Usuario crear(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo o vacio");
        }
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo o vacio");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacia");
        }
        //encriptamos la contraseña y que sea segura
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
        //encriptamos la contraseña para actualizarla
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        
        usuarios.setNombreUsuario(usuario.getNombreUsuario());
        usuarios.setEmail(usuario.getEmail());
        usuarios.setPassword(passwordEncriptada); // se encripta la contraseña antes de actualizarla
        usuarios.setRol(usuario.getRol());
        return repository.save(usuarios);
    }

    // Delete: Metodo eliminar usuario
    public void eliminar(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        Optional<Usuario> existe = repository.findById(id);
        if (existe.isEmpty()) {
            throw new RuntimeException("El id que desea eliminar no existe");
        }
        repository.deleteById(id);
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
    public LoginSeguroDTO loginSeguro(String nombreUsuario, String password) {

        if (nombreUsuario == null || nombreUsuario.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario o la contraseña no pueden ser nulos o vacios");
        }

        Optional<Usuario> usuarioEncontrado = repository.findByNombreUsuario(nombreUsuario);
        LoginSeguroDTO dto = new LoginSeguroDTO();
        // Antes de llamar el usuario con todo sus atributos verificamos que venga vacio
        if (usuarioEncontrado.isEmpty()) {
            throw new RuntimeException("Credenciales incorrectas, vuelva intentarlo");
        }
        Usuario usuario = usuarioEncontrado.get();

        // se compara la contraseña ingresada con la encriptada 
        if (passwordEncoder.matches(password, usuario.getPassword())) {
            dto.setNombreUsuario(usuario.getNombreUsuario());
            dto.setEmail(usuario.getEmail());
            dto.setRol(usuario.getRol());

            return dto; // dice si sale todo ok? entra al return

        }
        throw new RuntimeException("Credenciales incorrectas, vuelva intentarlo");
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
    public BuscarDatosSegurosDTO buscarEmailDTO(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vaco");
        }
        Optional<Usuario> users = repository.findByEmail(email);

        if (users.isEmpty()) {
            throw new RuntimeException("El email registrado no existe");
        }

        return buscarDatos(users.get());
    }
    //DTO para buscar por id
    public BuscarDatosSegurosDTO buscarIdDTO(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        Optional<Usuario> users = repository.findById(id);

        if(users.isEmpty()){
            throw new RuntimeException("El id no existe");
        }
        return buscarDatos(users.get());
    }
}
