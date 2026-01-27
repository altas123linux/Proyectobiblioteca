package com.mycompany.views;

import com.mycompany.biblioteca_digital.base_datos.PrestamoDAO;
import com.mycompany.biblioteca_digital.base_datos.LibroDAO;
import com.mycompany.biblioteca_digital.base_datos.PersonaDAO;
import com.mycompany.biblioteca_digital.modelo.Prestamo;
import com.mycompany.biblioteca_digital.modelo.Libro;
import com.mycompany.biblioteca_digital.modelo.Usuario;
import com.mycompany.biblioteca_digital.modelo.Persona;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import java.util.List;

public class vista2 extends javax.swing.JPanel {

    private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;
    private PersonaDAO personaDAO;
    
    public vista2() {
        initComponents();
        
    // Configurar imagen
    foto1.setIcon(new javax.swing.ImageIcon(
        new javax.swing.ImageIcon(getClass().getResource("/imagenes/pinterest1.png"))
        .getImage().getScaledInstance(350, 500, java.awt.Image.SCALE_SMOOTH)));
    foto1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    
    // Inicializar DAOs y cargar datos
    inicializar();
}

/**
 * Inicializar DAOs
 */
private void inicializar() {
    prestamoDAO = new PrestamoDAO();
    libroDAO = new LibroDAO();
    personaDAO = new PersonaDAO();
    
    // Cargar datos en los selectores
    cargarUsuarios();
    cargarLibros();
}

/**
 * Cargar usuarios en el ComboBox
 */
private void cargarUsuarios() {
    cmbUsuarios.removeAllItems();
    cmbUsuarios.addItem("-- Seleccione un usuario --");
    
    List<Persona> usuarios = personaDAO.obtenerTodos();
    for (Persona persona : usuarios) {
        if (persona.isActivo() && "USUARIO".equals(persona.getTipo())) {
            String item = persona.getIdPersona() + " - " + 
                         persona.getNombre() + " " + persona.getApellido() + 
                         " (" + persona.getCedula() + ")";
            cmbUsuarios.addItem(item);
        }
    }
        // Deshabilitar la primera opción (placeholder)
    if (cmbUsuarios.getItemCount() > 0) {
        cmbUsuarios.setSelectedIndex(0);
        }
    }
   

/**
 * Cargar libros en el ComboBox
 */
private void cargarLibros() {
    cmbLibros.removeAllItems();
    cmbLibros.addItem("-- Seleccione un libro --");
    
    List<Libro> libros = libroDAO.obtenerTodos();
    for (Libro libro : libros) {
        if (libro.isActivo() && libro.getCantidadDisponible() > 0) {
            String item = libro.getIdLibro() + " - " + 
                         libro.getTitulo() + " por " + libro.getAutor() + 
                         " (Disponibles: " + libro.getCantidadDisponible() + ")";
            cmbLibros.addItem(item);
        }
    }
    
    if (cmbLibros.getItemCount() > 0) {
        cmbLibros.setSelectedIndex(0);
    }
}   

  /**
 * Realizar un nuevo préstamo
 */
private void realizarPrestamo() {
  // Verificar índices (no puede ser 0 que es el placeholder)
        if (cmbUsuarios.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this,
                "⚠ Por favor seleccione un usuario de la lista",
                "Usuario No Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (cmbLibros.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this,
                "⚠ Por favor seleccione un libro de la lista",
                "Libro No Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener selecciones
String usuarioSeleccionado = (String) cmbUsuarios.getSelectedItem();
String libroSeleccionado = (String) cmbLibros.getSelectedItem();

// DEBUG: Ver qué se seleccionó
System.out.println("DEBUG - Usuario seleccionado: [" + usuarioSeleccionado + "]");
System.out.println("DEBUG - Libro seleccionado: [" + libroSeleccionado + "]");
        
        // Extraer IDs de las selecciones
int idUsuario = -1;
int idLibro = -1;

try {
    // El formato es: "ID - Nombre..."
    String[] partesUsuario = usuarioSeleccionado.split(" - ");
    String[] partesLibro = libroSeleccionado.split(" - ");
    
    // Verificar que tenga el formato correcto
    if (partesUsuario.length > 0 && !partesUsuario[0].trim().isEmpty()) {
        idUsuario = Integer.parseInt(partesUsuario[0].trim());
    }
    
    if (partesLibro.length > 0 && !partesLibro[0].trim().isEmpty()) {
        idLibro = Integer.parseInt(partesLibro[0].trim());
    }
    
    // Validar que se obtuvieron IDs válidos
    if (idUsuario <= 0 || idLibro <= 0) {
        JOptionPane.showMessageDialog(this,
            "⚠ Por favor seleccione un usuario Y un libro válidos",
            "Selección Inválida",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
} catch (Exception e) {
    JOptionPane.showMessageDialog(this,
        "⚠ Error al procesar la selección\n\n" +
        "Por favor seleccione opciones válidas de las listas.",
        "Error de Selección",
        JOptionPane.ERROR_MESSAGE);
    return;
}
    
    // Buscar el usuario
    Persona persona = personaDAO.buscarPorId(idUsuario);
    if (persona == null) {
        JOptionPane.showMessageDialog(this,
            "❌ Usuario no encontrado\n\n" +
            "No existe un usuario con ID: " + idUsuario,
            "Usuario No Encontrado",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (!(persona instanceof Usuario)) {
        JOptionPane.showMessageDialog(this,
            "❌ La persona no es un usuario válido\n\n" +
            "ID: " + idUsuario + " corresponde a: " + persona.getTipo(),
            "Tipo Inválido",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Buscar el libro
    Libro libro = libroDAO.buscarPorId(idLibro);
    if (libro == null) {
        JOptionPane.showMessageDialog(this,
            "❌ Libro no encontrado\n\n" +
            "No existe un libro con ID: " + idLibro,
            "Libro No Encontrado",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Verificar disponibilidad
    if (libro.getCantidadDisponible() <= 0) {
        JOptionPane.showMessageDialog(this,
            "⚠ Libro no disponible\n\n" +
            "Título: " + libro.getTitulo() + "\n" +
            "Disponibles: " + libro.getCantidadDisponible() + "/" + libro.getCantidadTotal() + "\n\n" +
            "El libro está prestado en este momento.",
            "No Disponible",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Crear el préstamo
    Usuario usuario = (Usuario) persona;
    Prestamo prestamo = new Prestamo();
    prestamo.setUsuario(usuario);
    prestamo.setLibro(libro);
    prestamo.setFechaPrestamo(LocalDate.now());
    prestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(7)); // 7 días
    prestamo.setEstado("ACTIVO");
    
    // Guardar en BD
    boolean exito = prestamoDAO.insertar(prestamo);
    
    if (exito) {
        // Actualizar disponibilidad del libro
        int nuevaDisponibilidad = libro.getCantidadDisponible() - 1;
        libroDAO.actualizarDisponibilidad(idLibro, nuevaDisponibilidad);
        
        // Mensaje de éxito
        JOptionPane.showMessageDialog(this,
            "✓ PRÉSTAMO REALIZADO EXITOSAMENTE\n\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "📚 LIBRO:\n" +
            "   • " + libro.getTitulo() + "\n" +
            "   • Autor: " + libro.getAutor() + "\n" +
            "   • ISBN: " + libro.getIsbn() + "\n\n" +
            "👤 USUARIO:\n" +
            "   • " + usuario.getNombre() + " " + usuario.getApellido() + "\n" +
            "   • Cédula: " + usuario.getCedula() + "\n\n" +
            "📅 FECHAS:\n" +
            "   • Préstamo: " + prestamo.getFechaPrestamo() + "\n" +
            "   • Devolución: " + prestamo.getFechaDevolucionEsperada() + "\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
            "Préstamo Exitoso",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiar campos
        limpiarCampos();
        
    } else {
        JOptionPane.showMessageDialog(this,
            "❌ Error al realizar el préstamo\n\n" +
            "No se pudo guardar el préstamo en la base de datos.\n" +
            "Por favor intente nuevamente.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

/**
 * Limpiar campos del formulario
 */
private void limpiarCampos() {
    cmbUsuarios.setSelectedIndex(0);
    cmbLibros.setSelectedIndex(0);
    
    // Recargar libros por si cambió disponibilidad
    cargarLibros();
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        foto1 = new javax.swing.JLabel();
        texto3 = new javax.swing.JLabel();
        texto4 = new javax.swing.JLabel();
        prestar = new javax.swing.JButton();
        paleta = new javax.swing.JLabel();
        titulo3 = new javax.swing.JLabel();
        cmbUsuarios = new javax.swing.JComboBox<>();
        cmbLibros = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(706, 457));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setForeground(new java.awt.Color(204, 0, 51));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setBackground(new java.awt.Color(153, 0, 51));
        jSeparator1.setForeground(new java.awt.Color(204, 0, 51));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        bg.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 40, 440));

        foto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pinterest1.png"))); // NOI18N
        foto1.setMaximumSize(new java.awt.Dimension(1200, 1200));
        foto1.setPreferredSize(new java.awt.Dimension(1200, 1200));
        bg.add(foto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-9, 1, 370, 460));

        texto3.setBackground(new java.awt.Color(0, 51, 102));
        texto3.setFont(new java.awt.Font("STZhongsong", 0, 18)); // NOI18N
        texto3.setForeground(new java.awt.Color(0, 51, 102));
        texto3.setText("Usuario:");
        bg.add(texto3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 190, 45));

        texto4.setFont(new java.awt.Font("STZhongsong", 0, 18)); // NOI18N
        texto4.setForeground(new java.awt.Color(0, 51, 102));
        texto4.setText("Libros");
        bg.add(texto4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, 158, 48));

        prestar.setBackground(new java.awt.Color(0, 51, 102));
        prestar.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        prestar.setForeground(new java.awt.Color(255, 255, 255));
        prestar.setText("Realizar Prestamo");
        prestar.setBorder(null);
        prestar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        prestar.addActionListener(this::prestarActionPerformed);
        bg.add(prestar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, 200, 50));

        paleta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paleta.png"))); // NOI18N
        paleta.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 78, 1, 56, new java.awt.Color(0, 0, 0)));
        paleta.setMaximumSize(new java.awt.Dimension(300, 256));
        paleta.setMinimumSize(new java.awt.Dimension(300, 256));
        paleta.setPreferredSize(new java.awt.Dimension(300, 200));
        bg.add(paleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 290, 10));

        titulo3.setBackground(new java.awt.Color(0, 0, 0));
        titulo3.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 28)); // NOI18N
        titulo3.setForeground(new java.awt.Color(204, 0, 51));
        titulo3.setText("Panel Prestamo");
        bg.add(titulo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 240, 40));

        cmbUsuarios.setForeground(new java.awt.Color(204, 204, 204));
        cmbUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        bg.add(cmbUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 230, 40));

        cmbLibros.setForeground(new java.awt.Color(204, 204, 204));
        cmbLibros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        bg.add(cmbLibros, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 270, 230, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void prestarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prestarActionPerformed
        realizarPrestamo();
    }//GEN-LAST:event_prestarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JComboBox<String> cmbLibros;
    private javax.swing.JComboBox<String> cmbUsuarios;
    private javax.swing.JLabel foto1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel paleta;
    private javax.swing.JButton prestar;
    private javax.swing.JLabel texto3;
    private javax.swing.JLabel texto4;
    private javax.swing.JLabel titulo3;
    // End of variables declaration//GEN-END:variables

   
}
