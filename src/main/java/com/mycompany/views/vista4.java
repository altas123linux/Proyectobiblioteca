package com.mycompany.views;


import com.mycompany.biblioteca_digital.base_datos.PrestamoDAO;
import com.mycompany.biblioteca_digital.base_datos.LibroDAO;
import com.mycompany.biblioteca_digital.base_datos.PersonaDAO;
import com.mycompany.biblioteca_digital.modelo.Prestamo;
import com.mycompany.biblioteca_digital.modelo.Libro;
import com.mycompany.biblioteca_digital.modelo.Persona;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        
// Configurar imagen
        foto2.setIcon(new javax.swing.ImageIcon(
            new javax.swing.ImageIcon(getClass().getResource("/imagenes/entrada.png"))
            .getImage().getScaledInstance(350, 500, java.awt.Image.SCALE_SMOOTH)));
        foto2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        // Cargar usuarios
        cargarUsuarios();
    }
/**
     * Cargar usuarios en el ComboBox
     */
    private void cargarUsuarios() {
        cmbUsuarios.removeAllItems();
        cmbUsuarios.addItem("-- Seleccione un usuario --");
        
        List<Persona> usuarios = personaDAO.obtenerTodos();
        int count = 0;
        for (Persona persona : usuarios) {
            if (persona.isActivo() && "USUARIO".equals(persona.getTipo())) {
                String item = persona.getIdPersona() + " - " + 
                             persona.getNombre() + " " + persona.getApellido() + 
                             " (" + persona.getCedula() + ")";
                cmbUsuarios.addItem(item);
                count++;
            }
        }
        
        System.out.println("✓ Cargados " + count + " usuarios en devoluciones");
        
        if (cmbUsuarios.getItemCount() > 0) {
            cmbUsuarios.setSelectedIndex(0);
        }
    }

    /**
     * Cargar libros prestados por el usuario seleccionado
     */
    private void cargarLibrosPrestados() {
        cmbLibros.removeAllItems();
        cmbLibros.addItem("-- Seleccione un libro --");
        
        String usuarioSel = (String) cmbUsuarios.getSelectedItem();
        
        if (usuarioSel == null || usuarioSel.startsWith("--")) {
            System.out.println("⚠ No hay usuario seleccionado");
            return;
        }
        
        try {
            // Extraer ID del usuario
            int idUsuario = Integer.parseInt(usuarioSel.split(" - ")[0].trim());
            
            // Obtener préstamos activos del usuario
            List<Prestamo> prestamos = prestamoDAO.obtenerPorUsuario(idUsuario);
            
            int count = 0;
            for (Prestamo p : prestamos) {
                if ("ACTIVO".equals(p.getEstado())) {
                    Libro libro = p.getLibro();
                    String item = p.getIdPrestamo() + " | Libro: " + libro.getTitulo() + 
                                 " | Prestado: " + p.getFechaPrestamo();
                    cmbLibros.addItem(item);
                    count++;
                }
            }
            
            if (count == 0) {
                cmbLibros.removeAllItems();
                cmbLibros.addItem("-- Usuario sin préstamos activos --");
            }
            
            System.out.println("✓ Cargados " + count + " préstamos activos del usuario");
            
        } catch (Exception e) {
            System.err.println("✗ Error al cargar libros prestados: " + e.getMessage());
            e.printStackTrace();
            cmbLibros.removeAllItems();
            cmbLibros.addItem("-- Error al cargar libros --");
        }
    }
    
/**
     * Devolver libro
     */
    private void devolverLibro() {
        // Verificar índices
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
        
        String libroSeleccionado = (String) cmbLibros.getSelectedItem();
        
        // Verificar que no sea mensaje de error
        if (libroSeleccionado.contains("sin préstamos") || libroSeleccionado.contains("Error")) {
            JOptionPane.showMessageDialog(this,
                "⚠ No hay préstamos activos para devolver",
                "Sin Préstamos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Extraer ID del préstamo (formato: "ID | Libro: ...")
            int idPrestamo = Integer.parseInt(libroSeleccionado.split(" \\| ")[0].trim());
            
            // Buscar el préstamo
            Prestamo prestamo = prestamoDAO.buscarPorId(idPrestamo);
            
            if (prestamo == null) {
                JOptionPane.showMessageDialog(this,
                    "❌ Préstamo no encontrado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar que esté activo
            if (!"ACTIVO".equals(prestamo.getEstado())) {
                JOptionPane.showMessageDialog(this,
                    "⚠ Este préstamo ya fue devuelto",
                    "Préstamo Ya Devuelto",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Calcular días de retraso
            LocalDate hoy = LocalDate.now();
            LocalDate fechaEsperada = prestamo.getFechaDevolucionEsperada();
            long diasRetraso = ChronoUnit.DAYS.between(fechaEsperada, hoy);
            
            String estadoNuevo;
            if (diasRetraso > 0) {
                estadoNuevo = "DEVUELTO_TARDE";
            } else {
                estadoNuevo = "DEVUELTO";
            }
            
            // Actualizar préstamo
            prestamo.setFechaDevolucionReal(hoy);
            prestamo.setEstado(estadoNuevo);
            
            boolean exito = prestamoDAO.actualizar(prestamo);
            
            if (exito) {
                // Actualizar disponibilidad del libro
                Libro libro = prestamo.getLibro();
                int nuevaDisponibilidad = libro.getCantidadDisponible() + 1;
                libroDAO.actualizarDisponibilidad(libro.getIdLibro(), nuevaDisponibilidad);
                
                // Mensaje de éxito
                String mensaje = "✓ DEVOLUCIÓN REALIZADA EXITOSAMENTE\n\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                    "📚 LIBRO: " + libro.getTitulo() + "\n" +
                    "   Autor: " + libro.getAutor() + "\n\n" +
                    "👤 USUARIO: " + prestamo.getUsuario().getNombre() + " " + 
                                     prestamo.getUsuario().getApellido() + "\n\n" +
                    "📅 Fecha Préstamo: " + prestamo.getFechaPrestamo() + "\n" +
                    "📅 Fecha Devolución Esperada: " + fechaEsperada + "\n" +
                    "📅 Fecha Devolución Real: " + hoy + "\n\n";
                
                if (diasRetraso > 0) {
                    mensaje += "⚠ RETRASO: " + diasRetraso + " día(s)\n" +
                              "Estado: DEVUELTO CON RETRASO";
                } else {
                    mensaje += "✓ ENTREGADO A TIEMPO\n" +
                              "Estado: DEVUELTO";
                }
                
                mensaje += "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
                
                JOptionPane.showMessageDialog(this,
                    mensaje,
                    "Devolución Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Limpiar y recargar
                limpiarCampos();
                
            } else {
                JOptionPane.showMessageDialog(this,
                    "❌ Error al registrar la devolución",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Error al procesar la devolución: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

 /**
     * Limpiar campos
     */
    private void limpiarCampos() {
        cmbUsuarios.setSelectedIndex(0);
        cmbLibros.removeAllItems();
        cmbLibros.addItem("-- Primero seleccione un usuario --");
    }

  
    
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        foto2 = new javax.swing.JLabel();
        texto3 = new javax.swing.JLabel();
        texto4 = new javax.swing.JLabel();
        devolvers = new javax.swing.JButton();
        paleta1 = new javax.swing.JLabel();
        titulo4 = new javax.swing.JLabel();
        cmbLibros = new javax.swing.JComboBox<>();
        cmbUsuarios = new javax.swing.JComboBox<>();

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
        texto3.setText("Usuario:");
        bg.add(texto3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 190, 45));

        texto4.setFont(new java.awt.Font("STZhongsong", 0, 18)); // NOI18N
        texto4.setForeground(new java.awt.Color(0, 51, 102));
        texto4.setText("Libro Prestado:");
        bg.add(texto4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 158, 48));

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

        cmbLibros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        bg.add(cmbLibros, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 240, 40));

        cmbUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbUsuarios.addActionListener(this::cmbUsuariosActionPerformed);
        bg.add(cmbUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 240, 40));

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

    private void devolversActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devolversActionPerformed
        devolverLibro();
    }//GEN-LAST:event_devolversActionPerformed

    private void cmbUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUsuariosActionPerformed
        cargarLibrosPrestados();
    }//GEN-LAST:event_cmbUsuariosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JComboBox<String> cmbLibros;
    private javax.swing.JComboBox<String> cmbUsuarios;
    private javax.swing.JButton devolvers;
    private javax.swing.JLabel foto2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel paleta1;
    private javax.swing.JLabel texto3;
    private javax.swing.JLabel texto4;
    private javax.swing.JLabel titulo4;
    // End of variables declaration//GEN-END:variables

    
}
