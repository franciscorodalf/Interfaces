package es.ies.puerto.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {
    private String usuario;
    private String email;
    private String contraseña;

    // 🔹 Constructor vacío requerido por Jackson
    public Usuario() {}

    // 🔹 Constructor con parámetros para crear usuarios
    @JsonCreator
    public Usuario(@JsonProperty("usuario") String usuario,
                   @JsonProperty("email") String email,
                   @JsonProperty("contraseña") String contraseña) {
        this.usuario = usuario;
        this.email = email;
        this.contraseña = contraseña;
    }

    // 🔹 Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
