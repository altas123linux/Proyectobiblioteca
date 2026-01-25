package com.mycompany.biblioteca_digital.base_datos;

import com.mycompany.biblioteca_digital.modelo.Prestamo;
import com.mycompany.biblioteca_digital.modelo.Libro;
import com.mycompany.biblioteca_digital.modelo.Usuario;
import java.time.LocalDate;
import java.util.List;

public class PruebaPrestamoDAO {
    
    public static void main(String[] args) {
        PrestamoDAO prestamoDAO = new PrestamoDAO();
        LibroDAO libroDAO = new LibroDAO();
        PersonaDAO personaDAO = new PersonaDAO();
        
        System.out.println("=== PRUEBA DE PrestamoDAO ===\n");
        
        // 1. Obtener todos los préstamos
        System.out.println("1. OBTENIENDO PRÉSTAMOS:");
        System.out.println("-------------------------");
        List<Prestamo> prestamos = prestamoDAO.obtenerTodos();
        System.out.println("Total préstamos: " + prestamos.size());
        
        // 2. Crear préstamo de prueba
        System.out.println("\n2. CREAR PRÉSTAMO DE PRUEBA:");
        System.out.println("-----------------------------");
        
        // Obtener primer libro y primer usuario
        List<Libro> libros = libroDAO.obtenerTodos();
        Usuario usuario = (Usuario) personaDAO.buscarPorUsuario("admin");
        
        if (!libros.isEmpty() && usuario != null) {
            Libro libro = libros.get(0);
            
            Usuario usuarioParaPrestamo = new Usuario();
            usuarioParaPrestamo.setIdPersona(usuario.getIdPersona());
            
            Prestamo prestamo = new Prestamo(usuarioParaPrestamo, libro);
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(7));
            prestamo.setEstado("ACTIVO");
            
            boolean creado = prestamoDAO.insertar(prestamo);
            System.out.println(creado ? "✓ Préstamo creado" : "✗ Error al crear");
        } else {
            System.out.println("⚠ No hay libros o usuario admin no existe");
        }
        
        // 3. Obtener préstamos activos
        System.out.println("\n3. PRÉSTAMOS ACTIVOS:");
        System.out.println("---------------------");
        List<Prestamo> activos = prestamoDAO.obtenerActivos();
        System.out.println("Total activos: " + activos.size());
        
        for (Prestamo p : activos) {
            System.out.println("- " + p.getLibro().getTitulo() + 
                             " (Usuario: " + p.getUsuario().getNombre() + ")");
        }
        
        System.out.println("\n=== FIN DE PRUEBAS ===");
    }
}