package com.lamagiadelazucar.backend.service;

import java.util.List;
import java.util.Optional;

import com.lamagiadelazucar.backend.model.Usuario;

// PAL CRUD USUARIOS

public interface UsuarioService {
    Usuario registrar(Usuario usuario);
    Optional<Usuario> login(String email, String password);
    Optional<Usuario> buscarPorEmail(String email);
    Optional<Usuario> buscarPorId(Long id);
    Usuario buscarPorIdConRoles(Long id);
    Usuario actualizarConRoles(Long id, Usuario usuario);
    void eliminar(Long id);
    Usuario actualizar(Long id, Usuario usuario);
    List<Usuario> obtenerTodos();
    boolean existePorId(Long id);
    boolean existePorEmail(String email);
    Usuario guardar(Usuario usuario);
    void cambiarContrasena(String email, String actual, String nueva);
}
