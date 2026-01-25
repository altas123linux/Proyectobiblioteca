package com.mycompany.biblioteca_digital.base_datos;

import com.mycompany.biblioteca_digital.modelo.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    
    /**
     * Insertar un nuevo libro en la base de datos
     */
    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO libros (isbn, titulo, autor, editorial, año, categoria, " +
                     "cantidad_total, cantidad_disponible, ubicacion, activo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setString(3, libro.getAutor());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getAño());
            stmt.setString(6, libro.getCategoria());
            stmt.setInt(7, libro.getCantidadTotal());
            stmt.setInt(8, libro.getCantidadDisponible());
            stmt.setString(9, libro.getUbicacion());
            stmt.setBoolean(10, libro.isActivo());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Libro insertado en BD: " + libro.getTitulo());
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar libro: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Buscar libro por ISBN
     */
    public Libro buscarPorISBN(String isbn) {
        String sql = "SELECT * FROM libros WHERE isbn = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, isbn);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return crearLibroDesdeResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar libro por ISBN: " + e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Buscar libro por ID
     */
    public Libro buscarPorId(int id) {
        String sql = "SELECT * FROM libros WHERE id_libro = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return crearLibroDesdeResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar libro por ID: " + e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Buscar libros por título (búsqueda parcial)
     */
    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE titulo LIKE ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + titulo + "%");
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                libros.add(crearLibroDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar libros por título: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return libros;
    }
    
    /**
     * Obtener todos los libros
     */
    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE activo = 1 ORDER BY titulo";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                libros.add(crearLibroDesdeResultSet(rs));
            }
            
            System.out.println("✓ Se obtuvieron " + libros.size() + " libros");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener libros: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return libros;
    }
    
    /**
     * Actualizar libro
     */
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET isbn = ?, titulo = ?, autor = ?, editorial = ?, " +
                     "año = ?, categoria = ?, cantidad_total = ?, cantidad_disponible = ?, " +
                     "ubicacion = ?, activo = ? WHERE id_libro = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setString(3, libro.getAutor());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getAño());
            stmt.setString(6, libro.getCategoria());
            stmt.setInt(7, libro.getCantidadTotal());
            stmt.setInt(8, libro.getCantidadDisponible());
            stmt.setString(9, libro.getUbicacion());
            stmt.setBoolean(10, libro.isActivo());
            stmt.setInt(11, libro.getIdLibro());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Libro actualizado: " + libro.getTitulo());
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar libro: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Actualizar disponibilidad (cuando se presta o devuelve)
     */
    public boolean actualizarDisponibilidad(int idLibro, int nuevaCantidadDisponible) {
        String sql = "UPDATE libros SET cantidad_disponible = ? WHERE id_libro = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, nuevaCantidadDisponible);
            stmt.setInt(2, idLibro);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar disponibilidad: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Eliminar libro (marcar como inactivo)
     */
    public boolean eliminar(int idLibro) {
        String sql = "UPDATE libros SET activo = 0 WHERE id_libro = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idLibro);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Libro eliminado (inactivo): ID " + idLibro);
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar libro: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Crear objeto Libro desde un ResultSet
     */
    private Libro crearLibroDesdeResultSet(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        
        libro.setIdLibro(rs.getInt("id_libro"));
        libro.setIsbn(rs.getString("isbn"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setEditorial(rs.getString("editorial"));
        libro.setAño(rs.getInt("año"));
        libro.setCategoria(rs.getString("categoria"));
        libro.setCantidadTotal(rs.getInt("cantidad_total"));
        libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));
        libro.setUbicacion(rs.getString("ubicacion"));
        libro.setActivo(rs.getBoolean("activo"));
        
        return libro;
    }
}