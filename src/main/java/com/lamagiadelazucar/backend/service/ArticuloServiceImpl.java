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
    @Override
    public List<Articulo> listarArticulos() {
        return articuloRepository.findAll();
    }

    // Obtiene un artículo por su ID
    @Override
    public Optional<Articulo> obtenerArticuloPorId(Long id) {
        return articuloRepository.findById(id);
        
    }

    // Método adicional para obtener un artículo por ID, lanzando excepción si no se encuentra
    @Override
    public Articulo obtenerPorId(Long id) {
        return articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));
    }

    // Guarda un nuevo artículo o actualiza uno existente
    @Override
    public Articulo guardarArticulo(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    // Actualiza un artículo existente por su ID
    @Override
    public Articulo actualizarArticulo(Long id, Articulo articulo) {
        articulo.setId(id);
        return articuloRepository.save(articulo);
    }

    // Elimina un artículo por su ID
    @Override
    public void eliminarArticulo(Long id) {
        articuloRepository.deleteById(id);
    }
}
