package com.lamagiadelazucar.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lamagiadelazucar.backend.model.Articulo;
import com.lamagiadelazucar.backend.repository.ArticuloRepository;

@Service // Marca la clase como servicio de Spring
public class ArticuloServiceImpl implements ArticuloService {

    private final ArticuloRepository articuloRepository;


    @Autowired
    public ArticuloServiceImpl(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    // Lista todos los artículos
    public List<Articulo> listarArticulos() {
        return articuloRepository.findAll();
    }

    // Obtiene un artículo por su ID
    public Optional<Articulo> obtenerArticuloPorId(Long id) {
        return articuloRepository.findById(id);
    }

    // Guarda un nuevo artículo o actualiza uno existente
    public Articulo guardarArticulo(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    // Actualiza un artículo existente por su ID
    public Articulo actualizarArticulo(Long id, Articulo articulo) {
        articulo.setId(id);
        return articuloRepository.save(articulo);
    }

    // Elimina un artículo por su ID
    public void eliminarArticulo(Long id) {
        articuloRepository.deleteById(id);
    }
}
