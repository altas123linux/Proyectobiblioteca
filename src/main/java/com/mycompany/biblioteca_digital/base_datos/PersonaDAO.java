package com.mycompany.biblioteca_digital.base_datos;

import com.mycompany.biblioteca_digital.modelo.Usuario;
import com.mycompany.biblioteca_digital.modelo.Administrador;
import com.mycompany.biblioteca_digital.modelo.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones con la tabla personas
 */
public class PersonaDAO {
    
    /**
     * Insertar un nuevo usuario en la base de datos
     */
    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO personas (cedula, nombre, apellido, mail, direccion, tipo, usuario, contraseña, activo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, usuario.getCedula());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getMail());
            stmt.setString(5, usuario.getDireccion());
            stmt.setString(6, usuario.getTipo());
            stmt.setString(7, usuario.getUsuario());
            stmt.setString(8, usuario.getContraseña());
            stmt.setBoolean(9, usuario.isActivo());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Usuario insertado en BD: " + usuario.getUsuario());
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar usuario: " + e.getMessage());
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
     * Buscar usuario por nombre de usuario
     */
    public Persona buscarPorUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM personas WHERE usuario = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombreUsuario);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return crearPersonaDesdeResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar usuario: " + e.getMessage());
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
     * Buscar persona por cédula
     */
    public Persona buscarPorCedula(String cedula) {
        String sql = "SELECT * FROM personas WHERE cedula = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cedula);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return crearPersonaDesdeResultSet(rs);
            }
            
            return null;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar por cédula: " + e.getMessage());
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
 * Buscar persona por ID
 */
public Persona buscarPorId(int idPersona) {
    String sql = "SELECT * FROM personas WHERE id_persona = ?";
    
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionBD.obtenerConexion();
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idPersona);
        
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            return crearPersonaDesdeResultSet(rs);
        }
        
        return null;
        
    } catch (SQLException e) {
        System.err.println("✗ Error al buscar persona por ID: " + e.getMessage());
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
 * Eliminar persona (marcar como inactivo)
 */
public boolean eliminar(int idPersona) {
    String sql = "UPDATE personas SET activo = 0 WHERE id_persona = ?";
    
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        conn = ConexionBD.obtenerConexion();
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idPersona);
        
        int filasAfectadas = stmt.executeUpdate();
        
        if (filasAfectadas > 0) {
            System.out.println("✓ Persona eliminada (inactiva): ID " + idPersona);
            return true;
        }
        
        return false;
        
    } catch (SQLException e) {
        System.err.println("✗ Error al eliminar persona: " + e.getMessage());
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
     * Login - Validar usuario y contraseña
     */
    public Persona login(String nombreUsuario, String contraseña) {
        String sql = "SELECT * FROM personas WHERE usuario = ? AND contraseña = ? AND activo = 1";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contraseña);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("✓ Login exitoso: " + nombreUsuario);
                return crearPersonaDesdeResultSet(rs);
            }
            
            System.out.println("✗ Login fallido: credenciales incorrectas");
            return null;
            
        } catch (SQLException e) {
            System.err.println("✗ Error en login: " + e.getMessage());
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
     * Obtener todas las personas
     */
    public List<Persona> obtenerTodos() {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM personas";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.obtenerConexion();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                personas.add(crearPersonaDesdeResultSet(rs));
            }
            
            System.out.println("✓ Se obtuvieron " + personas.size() + " personas");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener personas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return personas;
    }
    
    /**
     * Crear objeto Persona desde un ResultSet
     */
    private Persona crearPersonaDesdeResultSet(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        
        Persona persona;
        
        if ("ADMINISTRADOR".equalsIgnoreCase(tipo)) {
            persona = new Administrador();
        } else {
            persona = new Usuario();
        }
        
        persona.setCedula(rs.getString("cedula"));
        persona.setNombre(rs.getString("nombre"));
        persona.setApellido(rs.getString("apellido"));
        persona.setMail(rs.getString("mail"));
        persona.setDireccion(rs.getString("direccion"));
        
        persona.setUsuario(rs.getString("usuario"));
        persona.setContraseña(rs.getString("contraseña"));
        persona.setActivo(rs.getBoolean("activo"));
        
        return persona;
    }
    
    /**
     * Método de prueba
     */
    public static void main(String[] args) {
        PersonaDAO dao = new PersonaDAO();
        
        System.out.println("=== PRUEBA DE PersonaDAO ===\n");
        
        // 1. Crear usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setCedula("1234567890");
        usuario.setNombre("Test");
        usuario.setApellido("Usuario");
        usuario.setMail("test@mail.com");
        usuario.setDireccion("Calle Test 123");
        
        usuario.setUsuario("testuser");
        usuario.setContraseña("test123");
        usuario.setActivo(true);
        
        // 2. Insertar
        System.out.println("1. Insertando usuario...");
        boolean insertado = dao.insertar(usuario);
        System.out.println("   Resultado: " + (insertado ? "ÉXITO" : "FALLO") + "\n");
        
        // 3. Buscar por usuario
        System.out.println("2. Buscando por usuario 'testuser'...");
        Persona encontrado = dao.buscarPorUsuario("testuser");
        if (encontrado != null) {
            System.out.println("   Encontrado: " + encontrado.getNombre() + " " + encontrado.getApellido() + "\n");
        } else {
            System.out.println("   No encontrado\n");
        }
        
        // 4. Login
        System.out.println("3. Probando login...");
        Persona logueado = dao.login("testuser", "test123");
        System.out.println("   Login: " + (logueado != null ? "ÉXITO" : "FALLO") + "\n");
        
        // 5. Listar todos
        System.out.println("4. Listando todos los usuarios...");
        List<Persona> todos = dao.obtenerTodos();
        for (Persona p : todos) {
            System.out.println("   - " + p.getUsuario() + " (" + p.getTipo() + ")");
        }
        
        System.out.println("\n=== FIN DE PRUEBAS ===");
    }
}
