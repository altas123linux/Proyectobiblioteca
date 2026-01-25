package com.mycompany.biblioteca_digital.base_datos;

import com.mycompany.biblioteca_digital.modelo.Libro;
import java.util.List;

public class PruebaLibroDAO {
    
    public static void main(String[] args) {
        LibroDAO libroDAO = new LibroDAO();
        
        System.out.println("=== PRUEBA DE LibroDAO ===\n");
        
        // 1. Obtener todos los libros (los 40 que ya tienes)
        System.out.println("1. OBTENIENDO TODOS LOS LIBROS:");
        System.out.println("--------------------------------");
        List<Libro> libros = libroDAO.obtenerTodos();
        
        if (libros.isEmpty()) {
            System.out.println("❌ No hay libros en la BD");
        } else {
            System.out.println("✓ Libros encontrados: " + libros.size() + "\n");
            
            // Mostrar primeros 5 libros
            for (int i = 0; i < Math.min(5, libros.size()); i++) {
                Libro libro = libros.get(i);
                System.out.println("📚 " + libro.getTitulo());
                System.out.println("   Autor: " + libro.getAutor());
                System.out.println("   ISBN: " + libro.getIsbn());
                System.out.println("   Disponibles: " + libro.getCantidadDisponible() + "/" + libro.getCantidadTotal());
                System.out.println();
            }
            
            if (libros.size() > 5) {
                System.out.println("... y " + (libros.size() - 5) + " libros más\n");
            }
        }
        
        // 2. Buscar libro por título
        System.out.println("\n2. BUSCAR POR TÍTULO:");
        System.out.println("----------------------");
        List<Libro> encontrados = libroDAO.buscarPorTitulo("el");
        System.out.println("Libros con 'el' en el título: " + encontrados.size());
        
        // 3. Insertar libro de prueba
        System.out.println("\n3. INSERTAR LIBRO DE PRUEBA:");
        System.out.println("-----------------------------");
        Libro nuevoLibro = new Libro();
        nuevoLibro.setIsbn("TEST-123-456");
        nuevoLibro.setTitulo("Libro de Prueba");
        nuevoLibro.setAutor("Autor Test");
        nuevoLibro.setEditorial("Editorial Test");
        nuevoLibro.setAño(2026);
        nuevoLibro.setCategoria("Prueba");
        nuevoLibro.setCantidadTotal(5);
        nuevoLibro.setCantidadDisponible(5);
        nuevoLibro.setUbicacion("Estante Test");
        nuevoLibro.setActivo(true);
        
        boolean insertado = libroDAO.insertar(nuevoLibro);
        System.out.println(insertado ? "✓ Libro insertado" : "✗ Error al insertar");
        
        System.out.println("\n=== FIN DE PRUEBAS ===");
    }
}
