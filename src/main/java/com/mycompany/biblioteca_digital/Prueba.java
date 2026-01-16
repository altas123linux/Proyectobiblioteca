package com.mycompany.biblioteca_digital;

import com.mycompany.biblioteca_digital.modelo.*;
import com.mycompany.biblioteca_digital.servicio.*;
import java.time.LocalDate;

public class Prueba {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE SERVICIOS ===\n");
        
        // Crear servicios
        RegistroUsuario registroUsuario = new RegistroUsuario();
        LibroOpciones libroOpciones = new LibroOpciones();
        PrestamoControl prestamoControl = new PrestamoControl();
        
        System.out.println("--- 1. PRUEBA DE USUARIOS ---");
        
        // Registrar nuevo usuario
        Usuario nuevoUsuario = new Usuario("0987654321", "Ana", "García", 
                                          "ana@mail.com", "Calle 456", 
                                          "ana456", "password123");
        boolean registrado = registroUsuario.registrarUsuario(nuevoUsuario);
        System.out.println("Usuario registrado: " + registrado + "\n");
        
        // Login exitoso
        Usuario usuarioLogin = registroUsuario.login("juan123", "password");
        System.out.println("Login exitoso: " + (usuarioLogin != null) + "\n");
        
        // Login fallido
        Usuario loginFallido = registroUsuario.login("juan123", "incorrecta");
        System.out.println("Login fallido: " + (loginFallido == null) + "\n");
        
        System.out.println("--- 2. PRUEBA DE LIBROS ---");
        
        // Agregar nuevo libro
        Libro nuevoLibro = new Libro("978-0-06-112008-4", "1984", "George Orwell", 
                                    "Secker & Warburg", 1949, "Ficción", 2, "Estante C3");
        boolean libroAgregado = libroOpciones.agregarLibro(nuevoLibro);
        System.out.println("Libro agregado: " + libroAgregado + "\n");
        
        // Buscar libro por título
        var librosEncontrados = libroOpciones.buscarPorTitulo("Harry Potter");
        System.out.println("Libros encontrados con 'Harry Potter': " + librosEncontrados.size());
        for (Libro libro : librosEncontrados) {
            System.out.println("  - " + libro.getTitulo() + " por " + libro.getAutor());
        }
        System.out.println();
        
        // Ver libros disponibles
        var librosDisponibles = libroOpciones.obtenerLibrosDisponibles();
        System.out.println("Total de libros disponibles: " + librosDisponibles.size() + "\n");
        
        System.out.println("--- 3. PRUEBA DE PRÉSTAMOS ---");
        
        // Obtener usuario y libro para préstamo
        Usuario usuario = registroUsuario.login("juan123", "password");
        Libro libro = libroOpciones.buscarPorISBN("978-3-16-148410-0");
        
        if (usuario != null && libro != null) {
            System.out.println("Intentando préstamo...");
            System.out.println("Usuario: " + usuario.getNombre());
            System.out.println("Libro: " + libro.getTitulo());
            System.out.println("Cantidad disponible ANTES: " + libro.getCantidadDisponible());
            System.out.println("Libros prestados del usuario ANTES: " + usuario.getLibrosPrestados() + "\n");
            
            // Realizar préstamo
            Prestamo prestamo = prestamoControl.realizarPrestamo(usuario, libro);
            
            if (prestamo != null) {
                System.out.println("\nCantidad disponible DESPUÉS: " + libro.getCantidadDisponible());
                System.out.println("Libros prestados del usuario DESPUÉS: " + usuario.getLibrosPrestados());
                System.out.println("ID del préstamo: " + prestamo.getIdPrestamo());
                System.out.println("Días restantes: " + prestamo.getDiasRestantes() + "\n");
                
                // Ver préstamos activos del usuario
                var prestamosActivos = prestamoControl.obtenerPrestamosActivosUsuario(usuario);
                System.out.println("Préstamos activos del usuario: " + prestamosActivos.size() + "\n");
                
                // Intentar devolver el libro
                System.out.println("--- Devolviendo el libro ---");
                boolean devuelto = prestamoControl.devolverLibro(prestamo.getIdPrestamo());
                
                if (devuelto) {
                    System.out.println("Cantidad disponible DESPUÉS DE DEVOLVER: " + libro.getCantidadDisponible());
                    System.out.println("Libros prestados del usuario DESPUÉS DE DEVOLVER: " + usuario.getLibrosPrestados() + "\n");
                }
            }
        }
        
        System.out.println("--- 4. ESTADÍSTICAS ---");
        System.out.println(registroUsuario.obtenerTodosLosUsuarios().size() + " usuarios registrados");
        System.out.println(libroOpciones.obtenerTodosLosLibros().size() + " libros en el sistema");
        System.out.println();
        System.out.println(prestamoControl.obtenerEstadisticas());
        
        System.out.println("\n=== FIN DE PRUEBAS ===");
    }
}