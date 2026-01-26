package com.mycompany.views;

import java.awt.Image;
import javax.swing.ImageIcon;
import com.mycompany.biblioteca_digital.base_datos.PersonaDAO;
import com.mycompany.biblioteca_digital.modelo.Persona;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
 
public class vista3 extends javax.swing.JPanel {

    private PersonaDAO personaDAO;
    private DefaultTableModel modeloTabla;
    
    public vista3() {
        initComponents();
        inicializar();
}

/**
 * Inicializar componentes personalizados
 */
private void inicializar() {
    personaDAO = new PersonaDAO();
    configurarTabla();
    cargarUsuarios();

        
boton3.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono.png")).getImage().getScaledInstance(125, 110, java.awt.Image.SCALE_SMOOTH)));

// 2. Forzar el centrado horizontal dentro del espacio del Label
boton3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

boton5.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono2.png")).getImage().getScaledInstance(80, 50, java.awt.Image.SCALE_SMOOTH)));

// 2. Forzar el centrado horizontal dentro del espacio del Label
boton5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

boton6.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono3_1.png")).getImage().getScaledInstance(80, 50, java.awt.Image.SCALE_SMOOTH)));

// 2. Forzar el centrado horizontal dentro del espacio del Label
boton6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
boton7.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono4_1.png")).getImage().getScaledInstance(80, 50, java.awt.Image.SCALE_SMOOTH)));

// 2. Forzar el centrado horizontal dentro del espacio del Label
boton7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
}
    /**
 * Configurar columnas de la tabla
 */
private void configurarTabla() {
    String[] columnas = {"ID", "Cédula", "Nombre", "Apellido", "Email", 
                        "Dirección", "Tipo", "Usuario", "Activo"};
    modeloTabla = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    jTable1.setModel(modeloTabla);
    
    // Ajustar anchos
    jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
    jTable1.getColumnModel().getColumn(1).setPreferredWidth(100); // Cédula
    jTable1.getColumnModel().getColumn(2).setPreferredWidth(120); // Nombre
    jTable1.getColumnModel().getColumn(3).setPreferredWidth(120); // Apellido
    jTable1.getColumnModel().getColumn(4).setPreferredWidth(180); // Email
    jTable1.getColumnModel().getColumn(5).setPreferredWidth(150); // Dirección
    jTable1.getColumnModel().getColumn(6).setPreferredWidth(120); // Tipo
    jTable1.getColumnModel().getColumn(7).setPreferredWidth(100); // Usuario
    jTable1.getColumnModel().getColumn(8).setPreferredWidth(60);  // Activo
}

/**
 * Cargar todos los usuarios
 */
private void cargarUsuarios() {
    modeloTabla.setRowCount(0);
    
    List<Persona> usuarios = personaDAO.obtenerTodos();
    
    for (Persona persona : usuarios) {
        Object[] fila = {
            persona.getIdPersona(),
            persona.getCedula(),
            persona.getNombre(),
            persona.getApellido(),
            persona.getMail(),
            persona.getDireccion(),
            persona.getTipo(),
            persona.getUsuario(),
            persona.isActivo() ? "Sí" : "No"
        };
        modeloTabla.addRow(fila);
    }
    
    System.out.println("✓ Usuarios cargados: " + usuarios.size());
}

/**
 * Buscar usuarios por nombre o apellido
 */
private void buscarUsuarios(String texto) {
    modeloTabla.setRowCount(0);
    
    if (texto.trim().isEmpty()) {
        cargarUsuarios();
        return;
    }
    
    List<Persona> todosUsuarios = personaDAO.obtenerTodos();
    
    // Filtrar localmente
    for (Persona persona : todosUsuarios) {
        String nombreCompleto = (persona.getNombre() + " " + persona.getApellido()).toLowerCase();
        if (nombreCompleto.contains(texto.toLowerCase()) || 
            persona.getCedula().contains(texto)) {
            
            Object[] fila = {
                persona.getIdPersona(),
                persona.getCedula(),
                persona.getNombre(),
                persona.getApellido(),
                persona.getMail(),
                persona.getDireccion(),
                persona.getTipo(),
                persona.getUsuario(),
                persona.isActivo() ? "Sí" : "No"
            };
            modeloTabla.addRow(fila);
        }
    }
}

/**
 * Obtener usuario seleccionado
 */
private Persona obtenerUsuarioSeleccionado() {
    int fila = jTable1.getSelectedRow();
    
    if (fila == -1) {
        JOptionPane.showMessageDialog(this,
            "Por favor seleccione un usuario de la tabla",
            "Ningún Usuario Seleccionado",
            JOptionPane.WARNING_MESSAGE);
        return null;
    }
    
    int id = (int) modeloTabla.getValueAt(fila, 0);
    return personaDAO.buscarPorId(id);

}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        bg = new javax.swing.JPanel();
        paleta1 = new javax.swing.JLabel();
        titulo4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        boton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        boton5 = new javax.swing.JButton();
        boton6 = new javax.swing.JButton();
        boton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(706, 457));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setForeground(new java.awt.Color(204, 0, 51));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        paleta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paleta.png"))); // NOI18N
        paleta1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 78, 1, 56, new java.awt.Color(0, 0, 0)));
        paleta1.setMaximumSize(new java.awt.Dimension(300, 256));
        paleta1.setMinimumSize(new java.awt.Dimension(300, 256));
        paleta1.setPreferredSize(new java.awt.Dimension(300, 200));
        bg.add(paleta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 410, 10));

        titulo4.setBackground(new java.awt.Color(0, 0, 0));
        titulo4.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 28)); // NOI18N
        titulo4.setForeground(new java.awt.Color(204, 0, 51));
        titulo4.setText("Panel Usuario");
        bg.add(titulo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 220, 40));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null,  new Boolean(true)},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Apellido", "Cedula", "Mail", "Dirección", "Tipo", "Usuario", "Contraseña", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setGridColor(new java.awt.Color(0, 102, 204));
        jTable1.setSelectionBackground(new java.awt.Color(153, 153, 153));
        jTable1.setSelectionForeground(new java.awt.Color(204, 255, 255));
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        bg.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 650, 220));

        boton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono.png"))); // NOI18N
        boton3.setText("jButton1");
        bg.add(boton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 90, 60));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.setText("Busqueda de Usuario");
        jTextField1.setBorder(null);
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTextField1.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addActionListener(this::jTextField1ActionPerformed);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        bg.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 500, 30));

        jSeparator1.setBackground(new java.awt.Color(0, 51, 153));
        jSeparator1.setForeground(new java.awt.Color(0, 102, 153));
        bg.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 113, 500, 10));

        boton5.setBackground(new java.awt.Color(0, 0, 0));
        boton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono2.png"))); // NOI18N
        boton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton5.addActionListener(this::boton5ActionPerformed);
        bg.add(boton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 80, 50));

        boton6.setBackground(new java.awt.Color(0, 0, 0));
        boton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono3_1.png"))); // NOI18N
        boton6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 153), new java.awt.Color(0, 51, 153), new java.awt.Color(0, 51, 153), new java.awt.Color(0, 51, 153)));
        boton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton6.addActionListener(this::boton6ActionPerformed);
        bg.add(boton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 360, 80, 50));

        boton7.setBackground(new java.awt.Color(0, 0, 0));
        boton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icono4_1.png"))); // NOI18N
        boton7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        boton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton7.addActionListener(this::boton7ActionPerformed);
        bg.add(boton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 360, 80, 50));

        jLabel1.setFont(new java.awt.Font("STZhongsong", 1, 18)); // NOI18N
        jLabel1.setText("Nuevo");
        bg.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 420, 60, 20));

        jLabel2.setFont(new java.awt.Font("STZhongsong", 1, 18)); // NOI18N
        jLabel2.setText("Editar");
        bg.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 420, 70, 20));

        jLabel3.setFont(new java.awt.Font("STZhongsong", 1, 18)); // NOI18N
        jLabel3.setText("Borrar");
        bg.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 420, 70, 20));

        jScrollPane3.setViewportView(bg);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        // TODO add your handling code here:
        if (jTextField1.getText().equals("Busqueda de Usuario")) {
    jTextField1.setText("");
    jTextField1.setForeground(java.awt.Color.BLACK); // El texto se vuelve negro al escribir
}
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        // TODO add your handling code here:
        if (jTextField1.getText().isEmpty()) {
    jTextField1.setForeground(new java.awt.Color(153, 153, 153));
    jTextField1.setText("Busqueda de Usuario");
}
    }//GEN-LAST:event_jTextField1FocusLost

    private void boton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton6ActionPerformed
         Persona persona = obtenerUsuarioSeleccionado();
    
    if (persona != null) {
        JOptionPane.showMessageDialog(this,
            "Funcionalidad de Editar Usuario\n\n" +
            "Usuario seleccionado:\n" +
            "Nombre: " + persona.getNombre() + " " + persona.getApellido() + "\n" +
            "Tipo: " + persona.getTipo(),
            "Editar Usuario",
            JOptionPane.INFORMATION_MESSAGE);}
    }//GEN-LAST:event_boton6ActionPerformed

    private void boton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton5ActionPerformed
         JOptionPane.showMessageDialog(this,
        "Funcionalidad de Nuevo Usuario\n\n" +
        "Los nuevos usuarios se registran desde la ventana de registro\n" +
        "o puede crear un formulario administrativo aquí",
        "Nuevo Usuario",
        JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_boton5ActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
// Esto hace que al tocar el panel, la tabla pierda el foco y se limpie la selección visual
jTable1.clearSelection(); 
this.requestFocusInWindow();        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MousePressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        String texto = jTextField1.getText();
    buscarUsuarios(texto);
    }//GEN-LAST:event_jTextField1KeyReleased

    private void boton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton7ActionPerformed
         Persona persona = obtenerUsuarioSeleccionado();
    
    if (persona == null) {
        return;
    }
    
    // Confirmar eliminación
    int confirmacion = JOptionPane.showConfirmDialog(this,
        "¿Está seguro de que desea eliminar este usuario?\n\n" +
        "Usuario: " + persona.getUsuario() + "\n" +
        "Nombre: " + persona.getNombre() + " " + persona.getApellido() + "\n" +
        "Tipo: " + persona.getTipo() + "\n\n" +
        "Esta acción marcará el usuario como inactivo.",
        "Confirmar Eliminación",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
    
    if (confirmacion == JOptionPane.YES_OPTION) {
        boolean exito = personaDAO.eliminar(persona.getIdPersona());
        
        if (exito) {
            JOptionPane.showMessageDialog(this,
                "✓ Usuario eliminado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Recargar tabla
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error al eliminar el usuario",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_boton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton boton3;
    private javax.swing.JButton boton5;
    private javax.swing.JButton boton6;
    private javax.swing.JButton boton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel paleta1;
    private javax.swing.JLabel titulo4;
    // End of variables declaration//GEN-END:variables

    
}
