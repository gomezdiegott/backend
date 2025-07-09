package com.lamagiadelazucar.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lamagiadelazucar.backend.model.Articulo;

// Se conecta con la base de datos y maneja operaciones sobre la entidad Articulo
@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    // (repasar esto)
    // ===============================================
    // 🚀 MÉTODOS CRUD INCLUIDOS AUTOMÁTICAMENTE
    // ===============================================
    // findAll()                -> Lista todos los artículos
    // findById(Long id)        -> Busca uno por ID
    // save(Articulo a)         -> Inserta o actualiza
    // deleteById(Long id)      -> Elimina por ID
    // count()                  -> Cuenta registros
    // existsById(Long id)      -> Verifica si existe un ID

    // ===============================================
    // 🛠️ MÉTODOS PERSONALIZADOS (se generan por nombre)
    // ===============================================

    // (Tratar de IMPLEMENTAR esto)
    // Buscar artículos por nombre exacto
    List<Articulo> findByNombre(String nombre);

    // Buscar artículos cuyo nombre contenga una palabra (LIKE '%texto%')
    List<Articulo> findByNombreContaining(String texto);

    // Buscar artículos con precio mayor a un valor dado
    List<Articulo> findByPrecioGreaterThan(Double precio);

    // Buscar artículos con precio entre dos valores
    List<Articulo> findByPrecioBetween(Double min, Double max);

    // Buscar por nombre ignorando mayúsculas y minúsculas
    List<Articulo> findByNombreIgnoreCase(String nombre);

    // Buscar artículos ordenados por precio ascendente
    List<Articulo> findAllByOrderByPrecioAsc();

    // Buscar artículos por nombre y precio mayor a cierto valor
    List<Articulo> findByNombreAndPrecioGreaterThan(String nombre, Double precio);
}
