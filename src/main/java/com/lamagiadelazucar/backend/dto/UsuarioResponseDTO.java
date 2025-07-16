package com.lamagiadelazucar.backend.dto;

import java.time.LocalDate;

import com.lamagiadelazucar.backend.model.Usuario;

import lombok.Value;



@Value
public class UsuarioResponseDTO {
    Long id;
    String nombre;
    String apellido;
    String email;
    String rol;
    LocalDate fechaNacimiento;
    String pais;
    
    // Constructor que acepta un Usuario
    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.email = usuario.getEmail();
        this.rol = usuario.getRol();
        this.fechaNacimiento = usuario.getFechaNacimiento();
        this.pais = usuario.getPais();
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getEmail() {
        return email;
    }
    public String getRol() {
        return rol;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public String getPais() {
        return pais;
    }
}