package com.lamagiadelazucar.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lamagiadelazucar.backend.model.Carrito;
import com.lamagiadelazucar.backend.model.Usuario;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuario(Usuario usuario);
}
