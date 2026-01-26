package com.mycompany.views;

import java.awt.Image;
import javax.swing.ImageIcon;
import com.mycompany.biblioteca_digital.base_datos.PrestamoDAO;
import com.mycompany.biblioteca_digital.base_datos.LibroDAO;
import com.mycompany.biblioteca_digital.base_datos.PersonaDAO;
import com.mycompany.biblioteca_digital.modelo.Prestamo;
import com.mycompany.biblioteca_digital.modelo.Libro;
import java.util.List;
import javax.swing.JOptionPane;

public class vista4 extends javax.swing.JPanel {

   private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;
    private PersonaDAO personaDAO;
    
    public vista4() {
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
        
foto2.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/entrada.png")).getImage().getScaledInstance(350, 500, java.awt.Image.SCALE_SMOOTH)));

// 2. Forzar el centrado horizontal dentro del espacio del Label
foto2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
  
}
    
/**
 * Devolver un libro
 */
private void devolverLibro() {
    // Obtener datos
    String folioUsuarioStr = respuesta5.getText().trim();
    String libroIdStr = respuesta6.getText().trim();
    
    // Validar campos vacíos
    if (folioUsuarioStr.isEmpty() || libroIdStr.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "⚠ Por favor complete todos los campos\n\n" +
            "- Folio Usuario (ID del usuario)\n" +
            "- Libro ID (ID del libro a devolver)",
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
    
    // Obtener préstamos activos del usuario
    List<Prestamo> prestamos = prestamoDAO.obtenerPorUsuario(idUsuario);
    
    if (prestamos.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "⚠ El usuario no tiene préstamos registrados\n\n" +
            "Folio Usuario: " + idUsuario,
            "Sin Préstamos",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Buscar el préstamo activo de ese libro
    Prestamo prestamoActivo = null;
    for (Prestamo p : prestamos) {
        if (p.getLibro().getIdLibro() == idLibro && "ACTIVO".equals(p.getEstado())) {
            prestamoActivo = p;
            break;
        }
    }
    
    if (prestamoActivo == null) {
        JOptionPane.showMessageDialog(this,
            "⚠ No se encontró un préstamo activo\n\n" +
            "El usuario no tiene un préstamo activo del libro con ID: " + idLibro + "\n\n" +
            "Verifique:\n" +
            "• Que el usuario tenga el libro prestado\n" +
            "• Que el préstamo no haya sido devuelto ya\n" +
            "• Que los IDs sean correctos",
            "Préstamo No Encontrado",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Devolver el libro
    boolean exito = prestamoDAO.devolver(prestamoActivo.getIdPrestamo());
    
    if (exito) {
        // Actualizar disponibilidad
        Libro libro = prestamoActivo.getLibro();
        int nuevaDisponibilidad = libro.getCantidadDisponible() + 1;
        libroDAO.actualizarDisponibilidad(idLibro, nuevaDisponibilidad);
        
        // Calcular si hubo retraso
        java.time.LocalDate hoy = java.time.LocalDate.now();
        boolean conRetraso = hoy.isAfter(prestamoActivo.getFechaDevolucionEsperada());
        long diasRetraso = 0;
        
        if (conRetraso) {
            diasRetraso = java.time.temporal.ChronoUnit.DAYS.between(
                prestamoActivo.getFechaDevolucionEsperada(), hoy);
        }
        
        // Mensaje de éxito
        String mensajeRetraso = "";
        if (conRetraso) {
            mensajeRetraso = "\n⚠ DEVOLUCIÓN CON RETRASO: " + diasRetraso + " día(s)\n";
        }
        
        JOptionPane.showMessageDialog(this,
            "✓ LIBRO DEVUELTO EXITOSAMENTE\n\n" +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
            "📚 LIBRO:\n" +
            "   • " + libro.getTitulo() + "\n" +
            "   • Autor: " + libro.getAutor() + "\n\n" +
            "👤 USUARIO:\n" +
            "   • " + prestamoActivo.getUsuario().getNombre() + " " + 
                     prestamoActivo.getUsuario().getApellido() + "\n\n" +
            "📅 FECHAS:\n" +
            "   • Préstamo: " + prestamoActivo.getFechaPrestamo() + "\n" +
            "   • Esperada: " + prestamoActivo.getFechaDevolucionEsperada() + "\n" +
            "   • Devolución: " + hoy + "\n" +
            mensajeRetraso +
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
            "Devolución Exitosa",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiar campos
        limpiarCampos();
        
    } else {
        JOptionPane.showMessageDialog(this,
            "❌ Error al devolver el libro\n\n" +
            "No se pudo actualizar el préstamo en la base de datos.\n" +
            "Por favor intente nuevamente.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

/**
 * Limpiar campos del formulario
 */
private void limpiarCampos() {
    respuesta5.setText("");
    respuesta6.setText("");
    respuesta5.requestFocus();
}

/**
 * Ver préstamos activos de un usuario
 */
private void verPrestamosUsuario() {
    String folioStr = respuesta5.getText().trim();
    
    if (folioStr.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Ingrese un Folio Usuario para consultar",
            "Campo Vacío",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        int idUsuario = Integer.parseInt(folioStr);
        List<Prestamo> prestamos = prestamoDAO.obtenerPorUsuario(idUsuario);
        
        // Filtrar solo activos
        List<Prestamo> activos = new java.util.ArrayList<>();
        for (Prestamo p : prestamos) {
            if ("ACTIVO".equals(p.getEstado())) {
                activos.add(p);
            }
        }
        
        if (activos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El usuario no tiene préstamos activos",
                "Sin Préstamos Activos",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("PRÉSTAMOS ACTIVOS:\n\n");
            
            for (Prestamo p : activos) {
                mensaje.append("━━━━━━━━━━━━━━━━━━\n");
                mensaje.append("Libro ID: ").append(p.getLibro().getIdLibro()).append("\n");
                mensaje.append("Título: ").append(p.getLibro().getTitulo()).append("\n");
                mensaje.append("Fecha préstamo: ").append(p.getFechaPrestamo()).append("\n");
                mensaje.append("Fecha devolución: ").append(p.getFechaDevolucionEsperada()).append("\n\n");
            }
            
            JOptionPane.showMessageDialog(this,
                mensaje.toString(),
                "Préstamos Activos del Usuario",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
            "El Folio Usuario debe ser un número",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        foto2 = new javax.swing.JLabel();
        texto3 = new javax.swing.JLabel();
        texto4 = new javax.swing.JLabel();
        respuesta5 = new javax.swing.JTextField();
        respuesta6 = new javax.swing.JTextField();
        devolvers = new javax.swing.JButton();
        paleta1 = new javax.swing.JLabel();
        titulo4 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(706, 457));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setForeground(new java.awt.Color(204, 0, 51));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator2.setBackground(new java.awt.Color(153, 0, 51));
        jSeparator2.setForeground(new java.awt.Color(204, 0, 51));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        bg.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 50, 440));

        foto2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/entrada.png"))); // NOI18N
        foto2.setMaximumSize(new java.awt.Dimension(1200, 1200));
        foto2.setPreferredSize(new java.awt.Dimension(1200, 1200));
        bg.add(foto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 380, 490));

        texto3.setBackground(new java.awt.Color(0, 51, 102));
        texto3.setFont(new java.awt.Font("STZhongsong", 0, 18)); // NOI18N
        texto3.setForeground(new java.awt.Color(0, 51, 102));
        texto3.setText("Folio Usuario:");
        bg.add(texto3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 190, 45));

        texto4.setFont(new java.awt.Font("STZhongsong", 0, 18)); // NOI18N
        texto4.setForeground(new java.awt.Color(0, 51, 102));
        texto4.setText("Libro ID:");
        bg.add(texto4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 158, 48));

        respuesta5.setFont(new java.awt.Font("STZhongsong", 0, 14)); // NOI18N
        respuesta5.addActionListener(this::respuesta5ActionPerformed);
        bg.add(respuesta5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 240, 40));

        respuesta6.addActionListener(this::respuesta6ActionPerformed);
        bg.add(respuesta6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 240, 40));

        devolvers.setBackground(new java.awt.Color(0, 51, 102));
        devolvers.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        devolvers.setForeground(new java.awt.Color(255, 255, 255));
        devolvers.setText("Devolver");
        devolvers.setBorder(null);
        devolvers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        devolvers.addActionListener(this::devolversActionPerformed);
        bg.add(devolvers, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 200, 50));

        paleta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paleta.png"))); // NOI18N
        paleta1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 78, 1, 56, new java.awt.Color(0, 0, 0)));
        paleta1.setMaximumSize(new java.awt.Dimension(300, 256));
        paleta1.setMinimumSize(new java.awt.Dimension(300, 256));
        paleta1.setPreferredSize(new java.awt.Dimension(300, 200));
        bg.add(paleta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 280, 10));

        titulo4.setBackground(new java.awt.Color(0, 0, 0));
        titulo4.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 28)); // NOI18N
        titulo4.setForeground(new java.awt.Color(204, 0, 51));
        titulo4.setText("Panel Devolución");
        bg.add(titulo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 260, 40));

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

    private void respuesta5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_respuesta5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_respuesta5ActionPerformed

    private void respuesta6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_respuesta6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_respuesta6ActionPerformed

    private void devolversActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devolversActionPerformed
        devolverLibro();
    }//GEN-LAST:event_devolversActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton devolvers;
    private javax.swing.JLabel foto2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel paleta1;
    private javax.swing.JTextField respuesta5;
    private javax.swing.JTextField respuesta6;
    private javax.swing.JLabel texto3;
    private javax.swing.JLabel texto4;
    private javax.swing.JLabel titulo4;
    // End of variables declaration//GEN-END:variables

    
}
