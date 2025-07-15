package com.lamagiadelazucar.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lamagiadelazucar.backend.model.Usuario;
import com.lamagiadelazucar.backend.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario registrar(Usuario usuario) {
        // Encriptar la contraseña
        String passwordEncriptada = new BCryptPasswordEncoder().encode(usuario.getPassword());
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
                .filter(user -> new BCryptPasswordEncoder().matches(password, user.getPassword()));
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

}
