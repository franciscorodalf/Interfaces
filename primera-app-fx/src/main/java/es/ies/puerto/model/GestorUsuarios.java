package es.ies.puerto.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    private static final String ARCHIVO_JSON = "usuarios.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 🔹 Método para leer la lista de usuarios desde el JSON
    public static List<Usuario> leerUsuarios() {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>(); // Retorna una lista vacía si no hay archivo
        }

        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            return objectMapper.readValue(reader, new TypeReference<List<Usuario>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 🔹 Método para validar credenciales en el Login
    public static boolean validarUsuario(String usuario, String password) {
        List<Usuario> usuarios = leerUsuarios();

        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) && u.getContraseña().equals(password)) {
                return true; // ✅ Usuario encontrado y credenciales correctas
            }
        }
        return false; // ❌ Usuario o contraseña incorrectos
    }

    // 🔹 Método para registrar un nuevo usuario en JSON
    public static boolean agregarUsuario(Usuario nuevoUsuario) {
        List<Usuario> usuarios = leerUsuarios();

        // Comprobar si el usuario ya existe
        for (Usuario usuario : usuarios) {
            if (usuario.getUsuario().equals(nuevoUsuario.getUsuario())) {
                return false; // ❌ Usuario ya registrado
            }
        }

        // Agregar nuevo usuario y guardar en el archivo
        usuarios.add(nuevoUsuario);
        return guardarUsuarios(usuarios);
    }

    // 🔹 Método para guardar usuarios en JSON
    private static boolean guardarUsuarios(List<Usuario> usuarios) {
        try (Writer writer = new FileWriter(ARCHIVO_JSON)) {
            objectMapper.writeValue(writer, usuarios);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
