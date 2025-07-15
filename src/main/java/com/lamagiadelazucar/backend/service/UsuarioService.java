package com.lamagiadelazucar.backend.service;

import java.util.Optional;

import com.lamagiadelazucar.backend.model.Usuario;

// PAL CRUD USUARIOS

public interface UsuarioService {
    Usuario registrar(Usuario usuario);
    Optional<Usuario> login(String email, String password);
    Optional<Usuario> buscarPorEmail(String email);

}
