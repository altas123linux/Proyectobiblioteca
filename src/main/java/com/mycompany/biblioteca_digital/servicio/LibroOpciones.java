package com.mycompany.biblioteca_digital.servicio;

import com.mycompany.biblioteca_digital.modelo.Libro;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibroOpciones {
    // Lista temporal de libros
    private List<Libro> libros;
    private int contadorId;
    
    // Constructor
    public LibroOpciones() {
        this.libros = new ArrayList<>();
        this.contadorId = 1;
        
    }
    
    /**
     * Agregar un nuevo libro
     */
    public boolean agregarLibro(Libro libro) {
        // Validar que no exista el ISBN
        if (buscarPorISBN(libro.getIsbn()) != null) {
            System.out.println("Error: Ya existe un libro con ese ISBN");
            return false;
        }
        
        // Validar datos
        if (!validarDatosLibro(libro)) {
            return false;
        }
        
        // Asignar ID
        libro.setIdLibro(contadorId++);
        
        // Agregar a la lista
        libros.add(libro);
        System.out.println("Libro agregado: " + libro.getTitulo());
        return true;
    }
    
    /**
     * Buscar libro por ISBN
     */
    public Libro buscarPorISBN(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn) && libro.isActivo()) {
                return libro;
            }
        }
        return null;
    }
    
    /**
     * Buscar libros por título (búsqueda parcial)
     */
    public List<Libro> buscarPorTitulo(String titulo) {
        return libros.stream()
                .filter(libro -> libro.isActivo() && 
                        libro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Buscar libros por autor
     */
    public List<Libro> buscarPorAutor(String autor) {
        return libros.stream()
                .filter(libro -> libro.isActivo() && 
                        libro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Buscar libros por categoría
     */
    public List<Libro> buscarPorCategoria(String categoria) {
        return libros.stream()
                .filter(libro -> libro.isActivo() && 
                        libro.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener todos los libros activos
     */
    public List<Libro> obtenerTodosLosLibros() {
        return libros.stream()
                .filter(Libro::isActivo)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtener libros disponibles para préstamo
     */
    public List<Libro> obtenerLibrosDisponibles() {
        return libros.stream()
                .filter(Libro::estaDisponible)
                .collect(Collectors.toList());
    }
    
    /**
     * Actualizar información de libro
     */
    public boolean actualizarLibro(Libro libro) {
        Libro existente = buscarPorISBN(libro.getIsbn());
        
        if (existente == null) {
            System.out.println("Error: Libro no encontrado");
            return false;
        }
        
        // Actualizar datos
        existente.setTitulo(libro.getTitulo());
        existente.setAutor(libro.getAutor());
        existente.setEditorial(libro.getEditorial());
        existente.setAño(libro.getAño());
        existente.setCategoria(libro.getCategoria());
        existente.setCantidadTotal(libro.getCantidadTotal());
        existente.setUbicacion(libro.getUbicacion());
        
        // Ajustar cantidad disponible si cambió el total
        if (existente.getCantidadDisponible() > existente.getCantidadTotal()) {
            existente.setCantidadDisponible(existente.getCantidadTotal());
        }
        
        System.out.println("Libro actualizado: " + existente.getTitulo());
        return true;
    }
    
    /**
     * Eliminar (desactivar) libro
     */
    public boolean eliminarLibro(String isbn) {
        Libro libro = buscarPorISBN(isbn);
        
        if (libro == null) {
            System.out.println("Error: Libro no encontrado");
            return false;
        }
        
        // Verificar que no haya ejemplares prestados
        int prestados = libro.getCantidadTotal() - libro.getCantidadDisponible();
        if (prestados > 0) {
            System.out.println("Error: Hay " + prestados + " ejemplares prestados");
            return false;
        }
        
        libro.setActivo(false);
        System.out.println("Libro eliminado: " + libro.getTitulo());
        return true;
    }
    
    /**
     * Validar datos de libro
     */
    private boolean validarDatosLibro(Libro libro) {
        // Validar ISBN
        if (libro.getIsbn() == null || libro.getIsbn().length() < 10) {
            System.out.println("Error: ISBN inválido");
            return false;
        }
        
        // Validar título
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            System.out.println("Error: El título es obligatorio");
            return false;
        }
        
        // Validar autor
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            System.out.println("Error: El autor es obligatorio");
            return false;
        }
        
        // Validar cantidad
        if (libro.getCantidadTotal() < 1) {
            System.out.println("Error: La cantidad total debe ser al menos 1");
            return false;
        }
        
        return true;
    }
    
    /**
     * Obtener estadísticas de libros
     */
    public String obtenerEstadisticas() {
        int totalLibros = libros.size();
        int librosActivos = (int) libros.stream().filter(Libro::isActivo).count();
        int librosDisponibles = (int) libros.stream().filter(Libro::estaDisponible).count();
        
        return "Total de libros: " + totalLibros + "\n" +
               "Libros activos: " + librosActivos + "\n" +
               "Libros disponibles: " + librosDisponibles;
    }
}