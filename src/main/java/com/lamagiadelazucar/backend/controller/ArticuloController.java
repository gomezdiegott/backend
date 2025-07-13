package com.lamagiadelazucar.backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lamagiadelazucar.backend.model.Articulo;
import com.lamagiadelazucar.backend.service.ArticuloService;

@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (CORS)
@RestController // Expone métodos como API REST
@RequestMapping("/api/articulos") // Ruta base
public class ArticuloController {

    private final ArticuloService articuloService;

    @Autowired
    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @GetMapping
    public List<Articulo> listar() {
        return articuloService.listarArticulos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Articulo> obtenerPorId(@PathVariable Long id) {
        return articuloService.obtenerArticuloPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // @PostMapping
    // public Articulo crear(@RequestBody Articulo articulo) {
    //     return articuloService.guardarArticulo(articulo);
    // }

    @PostMapping
    public ResponseEntity<Articulo> crear(@RequestBody Articulo articulo) {
        Articulo guardado = articuloService.guardarArticulo(articulo);
        return ResponseEntity.ok(guardado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Articulo> actualizar(@PathVariable Long id, @RequestBody Articulo articulo) {
        if (articuloService.obtenerArticuloPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articuloService.actualizarArticulo(id, articulo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (articuloService.obtenerArticuloPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        articuloService.eliminarArticulo(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/imagenes/{nombre}")
    // public ResponseEntity<Resource> verImagen(@PathVariable String nombre) throws IOException {
    //     Path ruta = Paths.get("uploads/").resolve(nombre);
    //     Resource recurso = new UrlResource(ruta.toUri());
    //     if (!recurso.exists()) {
    //         return ResponseEntity.notFound().build();
    //     }
    //     return ResponseEntity.ok()
    //         .contentType(MediaType.IMAGE_JPEG) // o detectar el tipo real
    //         .body(recurso);
    // }

    @GetMapping("/imagenes/{nombre}")
    public ResponseEntity<Resource> verImagen(@PathVariable String nombre) throws IOException {
        Path ruta = Paths.get("uploads").resolve(nombre);
        Resource recurso = new UrlResource(ruta.toUri());

        if (!recurso.exists() || !recurso.isReadable()) {
            System.out.println("Imagen no encontrada: " + nombre);
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(ruta); // Detecta automáticamente el tipo
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body(recurso);
    }


    // @PostMapping("/{id}/imagen")
    // public ResponseEntity<String> subirImagen(
    //     @PathVariable Long id,
    //     @RequestParam("archivo") MultipartFile archivo) throws IOException {

    //     String nombreArchivo = UUID.randomUUID().toString() + "-" + archivo.getOriginalFilename();
    //     Path ruta = Paths.get("uploads/" + nombreArchivo);
    //     Files.createDirectories(ruta.getParent());
    //     Files.write(ruta, archivo.getBytes());

    //     Articulo articulo = articuloService.obtenerPorId(id);
    //     articulo.setImagenUrl(nombreArchivo);
    //     articuloService.guardarArticulo(articulo);

    //     return ResponseEntity.ok(nombreArchivo);
    // }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<String> subirImagen(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) throws IOException {

        String nombreArchivo = UUID.randomUUID().toString() + "-" + archivo.getOriginalFilename().replaceAll("\\s+", "_");

        Path ruta = Paths.get("uploads").resolve(nombreArchivo);
        Files.createDirectories(ruta.getParent());
        Files.write(ruta, archivo.getBytes());

        Articulo articulo = articuloService.obtenerPorId(id); // debe devolver un Articulo, no Optional
        articulo.setImagenUrl(nombreArchivo); // importante
        articuloService.guardarArticulo(articulo); // esto debe ejecutar save()

        System.out.println("Imagen guardada: " + nombreArchivo);
        System.out.println("Imagen URL en articulo: " + articulo.getImagenUrl());

        return ResponseEntity.ok(nombreArchivo);
    }

}