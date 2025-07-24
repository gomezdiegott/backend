package com.lamagiadelazucar.backend.dto;

public class CambioContrasenaDTO {
    private String email;
    private String actual;
    private String nueva;

    // getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getActual() { return actual; }
    public void setActual(String actual) { this.actual = actual; }

    public String getNueva() { return nueva; }
    public void setNueva(String nueva) { this.nueva = nueva; }
}
