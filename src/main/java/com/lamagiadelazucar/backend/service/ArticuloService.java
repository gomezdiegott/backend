package com.lamagiadelazucar.backend.service;

import java.util.List;
import java.util.Optional;

import com.lamagiadelazucar.backend.model.Articulo;

// Interfaz que define el contrato del servicio
public interface ArticuloService {
    List<Articulo> listarArticulos();
    Optional<Articulo> obtenerArticuloPorId(Long id);
    Articulo obtenerPorId(Long id);
    Articulo guardarArticulo(Articulo articulo);
    Articulo actualizarArticulo(Long id, Articulo articulo);
    void eliminarArticulo(Long id);
}
