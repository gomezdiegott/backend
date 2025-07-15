package com.lamagiadelazucar.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamagiadelazucar.backend.model.Usuario;
import com.lamagiadelazucar.backend.service.UsuarioService;
import com.lamagiadelazucar.backend.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") // Cambialo si necesitás restringirlo luego
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // @PostMapping("/registro")
    // public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
    //     Optional<Usuario> existente = usuarioService.buscarPorEmail(usuario.getEmail());

    //     if (existente.isPresent()) {
    //         return ResponseEntity.status(HttpStatus.CONFLICT).body("Ese correo ya está en uso.");
    //     }

    //     Usuario creado = usuarioService.registrar(usuario);
    //     return ResponseEntity.ok(creado);
    // }
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
   @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {

        

        // Asignar rol por defecto si no viene especificado
        if (usuario.getRol() == null) {
            usuario.setRol("USER");
        }
        
        // Puedes añadir validaciones adicionales aquí
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }
        
        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioGuardado);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> userOpt = usuarioService.buscarPorEmail(usuario.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El correo no está registrado.");
        }

        Usuario user = userOpt.get();
        boolean passwordCorrecta = new BCryptPasswordEncoder().matches(usuario.getPassword(), user.getPassword());

        if (!passwordCorrecta) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta.");
        }

        return ResponseEntity.ok(user);
    }

}
