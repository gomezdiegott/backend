package com.lamagiadelazucar.backend.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lamagiadelazucar.backend.model.Articulo;
import com.lamagiadelazucar.backend.model.Carrito;
import com.lamagiadelazucar.backend.model.CarritoItem;
import com.lamagiadelazucar.backend.model.Usuario;
import com.lamagiadelazucar.backend.repository.ArticuloRepository;
import com.lamagiadelazucar.backend.repository.CarritoItemRepository;
import com.lamagiadelazucar.backend.repository.CarritoRepository;
import com.lamagiadelazucar.backend.repository.UsuarioRepository;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoItemRepository carritoItemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Override
    public Carrito obtenerCarritoDeUsuario(Usuario usuario) {
        return carritoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    nuevo.setUsuario(usuario);
                    return carritoRepository.save(nuevo);
                });
    }

    @Override
    public Carrito agregarArticulo(Usuario usuario, Articulo articulo, int cantidad) {
        Carrito carrito = obtenerCarritoDeUsuario(usuario);

        Optional<CarritoItem> itemExistente = carrito.getItems().stream()
                .filter(item -> item.getArticulo().getId().equals(articulo.getId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            CarritoItem item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
            carritoItemRepository.save(item); // 🔧 Guardar actualización
        } else {
            CarritoItem nuevoItem = new CarritoItem();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setArticulo(articulo);
            nuevoItem.setCantidad(cantidad);
            carrito.getItems().add(nuevoItem);
        }


        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito eliminarArticulo(Usuario usuario, Long articuloId) {
        Carrito carrito = obtenerCarritoDeUsuario(usuario);
        carrito.getItems().removeIf(item -> item.getArticulo().getId().equals(articuloId));
        return carritoRepository.save(carrito);
    }

    @Override
    public void vaciarCarrito(Usuario usuario) {
        Carrito carrito = obtenerCarritoDeUsuario(usuario);
        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }
}
