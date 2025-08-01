package com.lamagiadelazucar.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lamagiadelazucar.backend.model.Usuario;
import com.lamagiadelazucar.backend.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrar(Usuario usuario) {
        // Encriptar la contraseña
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);

        // Asignar rol por defecto si no viene (ej: "USER")
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USER");
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> login(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario buscarPorIdConRoles(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public Usuario actualizarConRoles(Long id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            // No se debe cambiar el rol al actualizar, solo los datos del usuario
            Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow();
            usuario.setRol(usuarioExistente.getRol());
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + id);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + id);
    }

    @Override
    public void eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    @Override
    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        // Encriptar la contraseña si es nueva
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public void cambiarContrasena(String email, String actual, String nueva) {
        Usuario user = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(actual, user.getPassword())) {
            throw new RuntimeException("La contraseña actual no es correcta");
        }

        user.setPassword(passwordEncoder.encode(nueva));
        usuarioRepository.save(user);
    }

}
