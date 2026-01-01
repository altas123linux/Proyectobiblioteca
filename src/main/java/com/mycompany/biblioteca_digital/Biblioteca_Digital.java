/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */package com.mycompany.biblioteca_digital;

import javax.swing.*;
import java.awt.*;

public class Biblioteca_Digital extends JFrame {
    
    public Biblioteca_Digital() {
        // Configurar ventana
        setTitle("Biblioteca Digital");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Título
        JLabel titulo = new JLabel("¡Bienvenido a la Biblioteca Digital!");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Botones
        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrarse");
        
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistro.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Agregar componentes
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(btnLogin);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnRegistro);
        
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Biblioteca_Digital frame = new Biblioteca_Digital();
            frame.setVisible(true);
        });
    }
}