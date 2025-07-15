package com.lamagiadelazucar.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable()) // opcional: si ya usás @CrossOrigin
            .authorizeHttpRequests(auth -> auth
                // ✅ Permitimos explícitamente acceso público a artículos e imágenes
                .requestMatchers("/api/articulos/**", "/imagenes/**").permitAll()
                // ✅ Permitimos login y registro
                .requestMatchers("/usuarios/login", "/usuarios/registro").permitAll()
                // ❌ Todo lo demás requiere login (más adelante)
                .anyRequest().authenticated()
            )
            .formLogin(login -> login.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
