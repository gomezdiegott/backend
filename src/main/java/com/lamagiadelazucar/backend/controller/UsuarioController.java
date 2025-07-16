package com.lamagiadelazucar.backend.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamagiadelazucar.backend.dto.RegistroUsuarioDTO;
import com.lamagiadelazucar.backend.dto.UsuarioResponseDTO;
import com.lamagiadelazucar.backend.model.Usuario;
import com.lamagiadelazucar.backend.repository.UsuarioRepository;
import com.lamagiadelazucar.backend.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") 
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
//    @PostMapping("/registro")
//     public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {

        

//         // Asignar rol por defecto si no viene especificado
//         if (usuario.getRol() == null) {
//             usuario.setRol("USER");
//         }
        
//         // Puedes añadir validaciones adicionales aquí
//         if (usuarioRepository.existsByEmail(usuario.getEmail())) {
//             return ResponseEntity.badRequest().body("El email ya está registrado");
//         }
        
//         // Encriptar la contraseña antes de guardar
//         usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
//         Usuario usuarioGuardado = usuarioRepository.save(usuario);
//         return ResponseEntity.ok(usuarioGuardado);
//     }
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO registroDTO, BindingResult result) {
        // Validaciones automáticas de las anotaciones
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }

        // Validación adicional de edad mínima
        if (Period.between(registroDTO.getFechaNacimiento(), LocalDate.now()).getYears() < 12) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("fechaNacimiento", "Debes tener al menos 12 años"));
        }   

        // Validación manual de email único
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("email", "El email ya está registrado"));
        }

        // Conversión a entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setApellido(registroDTO.getApellido());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        usuario.setRol("USER");
        usuario.setFechaNacimiento(registroDTO.getFechaNacimiento());
        usuario.setPais(registroDTO.getPais());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(new UsuarioResponseDTO(usuarioGuardado));
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
