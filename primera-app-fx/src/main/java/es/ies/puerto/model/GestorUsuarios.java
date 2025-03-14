package es.ies.puerto.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    private static final String ARCHIVO_JSON = "usuarios.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Usuario> leerUsuarios() {
        File archivo = new File(ARCHIVO_JSON);

        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(archivo, new TypeReference<List<Usuario>>() {
            });
        } catch (IOException e) {
            System.err.println("❌ Error al leer el archivo JSON: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static boolean validarUsuario(String usuario, String password) {
        List<Usuario> usuarios = leerUsuarios();
        return usuarios.stream().anyMatch(u -> u.getUsuario().equals(usuario) && u.getContraseña().equals(password));
    }

    public static boolean agregarUsuario(Usuario nuevoUsuario) {
        List<Usuario> usuarios = leerUsuarios();

        if (usuarios.stream().anyMatch(u -> u.getUsuario().equals(nuevoUsuario.getUsuario()))) {
            System.out.println("⚠️ Usuario ya registrado.");
            return false;
        }

        usuarios.add(nuevoUsuario);
        return guardarUsuarios(usuarios);
    }

    private static boolean guardarUsuarios(List<Usuario> usuarios) {
        try (Writer writer = new FileWriter(ARCHIVO_JSON)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, usuarios);
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error al guardar usuarios en JSON: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public static boolean actualizarContrasenia(String usuario, String nuevaContrasenia) {
        List<Usuario> usuarios = leerUsuarios();
    
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario)) {
                u.setContraseña(nuevaContrasenia);  
                return guardarUsuarios(usuarios);  
            }
        }
    
        return false;
    }
    
}
