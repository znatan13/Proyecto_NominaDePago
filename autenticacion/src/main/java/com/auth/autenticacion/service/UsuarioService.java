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

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listar() {
        return repository.findAll();
    } 
    
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

        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        return repository.save(usuario);
    }
    public Usuario actualizarPorId(Integer id, Usuario usuario) {
        
        Optional<Usuario> existe = repository.findById(id);
        if (existe.isEmpty()) {
            throw new RuntimeException("El Id no existe");
        }

        Usuario usuarios = existe.get();
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        
        usuarios.setNombreUsuario(usuario.getNombreUsuario());
        usuarios.setEmail(usuario.getEmail());
        usuarios.setPassword(passwordEncriptada); 
        usuarios.setRol(usuario.getRol());
        return repository.save(usuarios);
    }

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


    public List<UsuarioSeguroDTO> usuariosSeguros() {
        List<Usuario> usuarios = repository.findAll(); 
        List<UsuarioSeguroDTO> lista = new ArrayList<>();

        for (Usuario u : usuarios) { 
            UsuarioSeguroDTO dto = new UsuarioSeguroDTO();

            dto.setNombreUsuario(u.getNombreUsuario());
            dto.setEmail(u.getEmail());

            lista.add(dto);

        }
        return lista; 
    }

    public LoginSeguroDTO loginSeguro(String nombreUsuario, String password) {

        if (nombreUsuario == null || nombreUsuario.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario o la contraseña no pueden ser nulos o vacios");
        }

        Optional<Usuario> usuarioEncontrado = repository.findByNombreUsuario(nombreUsuario);
        LoginSeguroDTO dto = new LoginSeguroDTO();
        if (usuarioEncontrado.isEmpty()) {
            throw new RuntimeException("Credenciales incorrectas, vuelva intentarlo");
        }
        Usuario usuario = usuarioEncontrado.get();

        if (passwordEncoder.matches(password, usuario.getPassword())) {
            dto.setNombreUsuario(usuario.getNombreUsuario());
            dto.setEmail(usuario.getEmail());
            dto.setRol(usuario.getRol());

            return dto; 

        }
        throw new RuntimeException("Credenciales incorrectas, vuelva intentarlo");
    }


    public BuscarDatosSegurosDTO buscarDatos(Usuario usuario) {
        BuscarDatosSegurosDTO dto = new BuscarDatosSegurosDTO();
        dto.setId(usuario.getId());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        return dto;
    }


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
