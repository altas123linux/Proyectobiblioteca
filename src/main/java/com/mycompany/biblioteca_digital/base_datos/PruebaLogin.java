package com.mycompany.biblioteca_digital.base_datos;

import com.mycompany.biblioteca_digital.modelo.Persona;

public class PruebaLogin {
    
    public static void main(String[] args) {
        PersonaDAO dao = new PersonaDAO();
        
        System.out.println("=== PRUEBA DE LOGIN ===\n");
        
        // Probar con el usuario que ves en phpMyAdmin
        String usuario = "clgalarza1";
        String contrasena = "password";
        
        System.out.println("Intentando login con:");
        System.out.println("Usuario: " + usuario);
        System.out.println("Contraseña: " + contrasena);
        System.out.println();
        
        Persona persona = dao.login(usuario, contrasena);
        
        if (persona != null) {
            System.out.println("✓ LOGIN EXITOSO");
            System.out.println("Nombre: " + persona.getNombre());
            System.out.println("Apellido: " + persona.getApellido());
            System.out.println("Tipo: " + persona.getTipo());
        } else {
            System.out.println("✗ LOGIN FALLIDO");
            System.out.println("Usuario o contraseña incorrectos");
        }
    }
}