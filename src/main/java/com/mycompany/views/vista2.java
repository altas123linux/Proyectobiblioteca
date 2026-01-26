package com.mycompany.views;

import java.awt.Image;
import javax.swing.ImageIcon;
import com.mycompany.biblioteca_digital.base_datos.PrestamoDAO;
import com.mycompany.biblioteca_digital.base_datos.LibroDAO;
import com.mycompany.biblioteca_digital.base_datos.PersonaDAO;
import com.mycompany.biblioteca_digital.modelo.Prestamo;
import com.mycompany.biblioteca_digital.modelo.Libro;
import com.mycompany.biblioteca_digital.modelo.Usuario;
import com.mycompany.biblioteca_digital.modelo.Persona;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class vista2 extends javax.swing.JPanel {

    private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;
    private PersonaDAO personaDAO;
    
    public vista2() {
        initComponents();
        inicializar();
}

/**
 * Inicializar DAOs
 */
private void inicializar() {
    prestamoDAO = new PrestamoDAO();
    libroDAO = new LibroDAO();
    personaDAO = new PersonaDAO();
        
        
foto1.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pinterest1.png")).getImage().getScaledInstance(350, 500, java.awt.Image.SCALE_SMOOTH)));

// 2. Forzar el centrado horizontal dentro del espacio del Label
foto1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
  
}
    
  private void realizarPrestamo() {
    // Obtener datos de los campos
    String folioUsuarioStr = respuesta.getText().trim();
    String libroIdStr = respuesta1.getText().trim();
    
    // Validar campos vacíos
    if (folioUsuarioStr.isEmpty() || libroIdStr.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "⚠ Por favor complete todos los campos\n\n" +
            "- Folio Usuario (ID del usuario)\n" +
            "- Libro ID (ID del libro)",
            "Campos Vacíos",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Convertir a números
    int idUsuario;
    int idLibro;
    
    try {
        idUsuario = Integer.parseInt(folioUsuarioStr);
        idLibro = Integer.parseInt(libroIdStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
            "⚠ Los IDs deben ser números válidos\n\n" +
            "Por favor verifique que ingresó valores numéricos.",
            "Error de Formato",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Buscar el usuario
    Persona persona = personaDAO.buscarPorId(idUsuario);
    if (persona == null) {
        JOptionPane.showMessageDialog(this,
            "❌ Usuario no encontrado\n\n" +
            "No existe un usuario con ID: " + idUsuario + "\n\n" +
            "Verifique el Folio Usuario e intente nuevamente.",
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
            "No existe un libro con ID: " + idLibro + "\n\n" +
            "Verifique el Libro ID e intente nuevamente.",
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
    Prestamo prestamo = new Prestamo(usuario, libro);
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
    respuesta.setText("");
    respuesta1.setText("");
    respuesta.requestFocus();
}

/**
 * Buscar y mostrar información del usuario
 */
private void buscarUsuario() {
    String folioStr = respuesta.getText().trim();
    
    if (folioStr.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Ingrese un Folio Usuario para buscar",
            "Campo Vacío",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        int idUsuario = Integer.parseInt(folioStr);
        Persona persona = personaDAO.buscarPorId(idUsuario);
        
        if (persona != null) {
            JOptionPane.showMessageDialog(this,
                "Usuario encontrado:\n\n" +
                "Nombre: " + persona.getNombre() + " " + persona.getApellido() + "\n" +
                "Cédula: " + persona.getCedula() + "\n" +
                "Email: " + persona.getMail() + "\n" +
                "Tipo: " + persona.getTipo(),
                "Usuario Encontrado",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "No se encontró usuario con ID: " + idUsuario,
                "No Encontrado",
                JOptionPane.WARNING_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
            "El Folio Usuario debe ser un número",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

/**
 * Buscar y mostrar información del libro
 */
private void buscarLibro() {
    String libroIdStr = respuesta1.getText().trim();
    
    if (libroIdStr.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Ingrese un Libro ID para buscar",
            "Campo Vacío",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        int idLibro = Integer.parseInt(libroIdStr);
        Libro libro = libroDAO.buscarPorId(idLibro);
        
        if (libro != null) {
            JOptionPane.showMessageDialog(this,
                "Libro encontrado:\n\n" +
                "Título: " + libro.getTitulo() + "\n" +
                "Autor: " + libro.getAutor() + "\n" +
                "ISBN: " + libro.getIsbn() + "\n" +
                "Disponibles: " + libro.getCantidadDisponible() + "/" + libro.getCantidadTotal(),
                "Libro Encontrado",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "No se encontró libro con ID: " + idLibro,
                "No Encontrado",
                JOptionPane.WARNING_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
            "El Libro ID debe ser un número",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        foto1 = new javax.swing.JLabel();
        texto3 = new javax.swing.JLabel();
        texto4 = new javax.swing.JLabel();
        respuesta = new javax.swing.JTextField();
        respuesta1 = new javax.swing.JTextField();
        prestar = new javax.swing.JButton();
        paleta = new javax.swing.JLabel();
        titulo3 = new javax.swing.JLabel();

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
        texto3.setText("Folio Usuario:");
        bg.add(texto3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 190, 45));

        texto4.setFont(new java.awt.Font("STZhongsong", 0, 18)); // NOI18N
        texto4.setForeground(new java.awt.Color(0, 51, 102));
        texto4.setText("Libro ID:");
        bg.add(texto4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, 158, 48));

        respuesta.setFont(new java.awt.Font("STZhongsong", 0, 14)); // NOI18N
        respuesta.addActionListener(this::respuestaActionPerformed);
        bg.add(respuesta, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 240, 40));

        respuesta1.addActionListener(this::respuesta1ActionPerformed);
        bg.add(respuesta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 270, 240, 40));

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

    private void respuestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_respuestaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_respuestaActionPerformed

    private void respuesta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_respuesta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_respuesta1ActionPerformed

    private void prestarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prestarActionPerformed
        realizarPrestamo();
    }//GEN-LAST:event_prestarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JLabel foto1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel paleta;
    private javax.swing.JButton prestar;
    private javax.swing.JTextField respuesta;
    private javax.swing.JTextField respuesta1;
    private javax.swing.JLabel texto3;
    private javax.swing.JLabel texto4;
    private javax.swing.JLabel titulo3;
    // End of variables declaration//GEN-END:variables

    
}
