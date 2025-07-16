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
                .requestMatchers(
                    "/api/articulos/**", 
                    "/api/carrito/**",  // 💥 Agregamos el carrito
                    "/imagenes/**", 
                    "/usuarios/login", 
                    "/usuarios/registro"
                ).permitAll()
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
