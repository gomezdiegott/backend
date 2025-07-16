package com.lamagiadelazucar.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lamagiadelazucar.backend.model.Carrito;
import com.lamagiadelazucar.backend.model.CarritoItem;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    List<CarritoItem> findByCarrito(Carrito carrito);
}
