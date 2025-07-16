package com.lamagiadelazucar.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lamagiadelazucar.backend.model.Articulo;
import com.lamagiadelazucar.backend.model.Carrito;
import com.lamagiadelazucar.backend.model.Usuario;
import com.lamagiadelazucar.backend.repository.ArticuloRepository;
import com.lamagiadelazucar.backend.repository.UsuarioRepository;
import com.lamagiadelazucar.backend.service.CarritoService;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @PostMapping("/agregar")
    public ResponseEntity<Void> agregarAlCarrito(
            @RequestParam String email,
            @RequestParam Long articuloId,
            @RequestParam(defaultValue = "1") int cantidad) {

        System.out.println("Agregando al carrito → email: " + email + ", articuloId: " + articuloId + ", cantidad: " + cantidad);

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        Optional<Articulo> articuloOpt = articuloRepository.findById(articuloId);

        if (usuarioOpt.isEmpty() || articuloOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); 
        }

        carritoService.agregarArticulo(usuarioOpt.get(), articuloOpt.get(), cantidad);

        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> obtener(@RequestParam String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        Carrito carrito = carritoService.obtenerCarritoDeUsuario(usuario);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", carrito.getId());
        
        List<Map<String, Object>> items = carrito.getItems().stream().map(item -> {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("id", item.getId());
            itemMap.put("cantidad", item.getCantidad());
            
            Map<String, Object> articuloMap = new HashMap<>();
            articuloMap.put("id", item.getArticulo().getId());
            articuloMap.put("nombre", item.getArticulo().getNombre());
            articuloMap.put("precio", item.getArticulo().getPrecio());
            articuloMap.put("imagenUrl", item.getArticulo().getImagenUrl());
            
            itemMap.put("articulo", articuloMap);
            return itemMap;
        }).collect(Collectors.toList());
        
        response.put("items", items);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar")
    public Carrito eliminar(@RequestParam String email,
                            @RequestParam Long articuloId) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        return carritoService.eliminarArticulo(usuario, articuloId);
    }

    @DeleteMapping("/vaciar")
    public void vaciar(@RequestParam String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        carritoService.vaciarCarrito(usuario);
    }
}
