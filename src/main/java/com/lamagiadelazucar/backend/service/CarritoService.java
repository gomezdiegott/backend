package com.lamagiadelazucar.backend.service;

import com.lamagiadelazucar.backend.model.Articulo;
import com.lamagiadelazucar.backend.model.Carrito;
import com.lamagiadelazucar.backend.model.Usuario;

public interface CarritoService {
    Carrito obtenerCarritoDeUsuario(Usuario usuario);
    Carrito agregarArticulo(Usuario usuario, Articulo articulo, int cantidad);
    Carrito eliminarArticulo(Usuario usuario, Long articuloId);
    void vaciarCarrito(Usuario usuario);
}
