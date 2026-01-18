package com.mycompany.biblioteca_digital.vista;

import com.mycompany.biblioteca_digital.modelo.Persona;
import com.mycompany.biblioteca_digital.servicio.RegistroUsuario;
import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {
    // Componentes
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private JButton btnRegistro;
    private JLabel lblMensaje;
    
    // Servicio
    private RegistroUsuario registroUsuario;
    
    // Constructor
    public VentanaLogin() {
        // Inicializar servicio
        registroUsuario = new RegistroUsuario();
        
        // Configurar ventana
        setTitle("Biblioteca Digital - Inicio de Sesión");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Crear componentes
        inicializarComponentes();
        
        // Hacer visible
        setVisible(true);
    }
    
    private void inicializarComponentes() {
        // Panel principal con fondo
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panelPrincipal.setBackground(new Color(236, 240, 241));
        
        // Panel superior - Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(236, 240, 241));
        
        JLabel lblTitulo = new JLabel("📚 Biblioteca Digital");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(41, 128, 185));
        panelTitulo.add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Sistema de Gestión Bibliotecaria");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(127, 140, 141));
        
        JPanel panelTituloCompleto = new JPanel();
        panelTituloCompleto.setLayout(new BoxLayout(panelTituloCompleto, BoxLayout.Y_AXIS));
        panelTituloCompleto.setBackground(new Color(236, 240, 241));
        panelTituloCompleto.add(lblTitulo);
        panelTituloCompleto.add(Box.createVerticalStrut(5));
        panelTituloCompleto.add(lblSubtitulo);
        
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Panel central - Formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelFormulario.add(lblUsuario, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panelFormulario.add(txtUsuario, gbc);
        
        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelFormulario.add(lblContrasena, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtContrasena = new JPasswordField(20);
        txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panelFormulario.add(txtContrasena, gbc);
        
        // Botones
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.setBackground(Color.WHITE);
        
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setPreferredSize(new Dimension(140, 35));
        btnLogin.setBackground(new Color(46, 204, 113));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> iniciarSesion());
        
        btnRegistro = new JButton("Registrarse");
        btnRegistro.setPreferredSize(new Dimension(140, 35));
        btnRegistro.setBackground(new Color(52, 152, 219));
        btnRegistro.setForeground(Color.WHITE);
        btnRegistro.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegistro.setFocusPainted(false);
        btnRegistro.setBorderPainted(false);
        btnRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistro.addActionListener(e -> abrirVentanaRegistro());
        
        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);
        panelFormulario.add(panelBotones, gbc);
        
        // Mensaje
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 8, 8, 8);
        lblMensaje = new JLabel(" ");
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMensaje.setForeground(Color.RED);
        panelFormulario.add(lblMensaje, gbc);
        
        // Agregar paneles a la ventana
        panelPrincipal.add(panelTituloCompleto, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        add(panelPrincipal);
        
        // Enter para login
        txtContrasena.addActionListener(e -> iniciarSesion());
    }
    
    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        // Validar campos vacíos
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarMensaje("⚠ Por favor complete todos los campos", new Color(231, 76, 60));
            return;
        }
        
        // Intentar login (ajusta según tu método de login en RegistroUsuario)
        // Asumiendo que tienes un método login que retorna Persona
        Persona personaLogueada = registroUsuario.login(usuario, contrasena);
        
        if (personaLogueada != null) {
            mostrarMensaje("✓ ¡Bienvenido " + personaLogueada.getNombre() + "!", 
                          new Color(46, 204, 113));
            
            // Esperar 1 segundo y abrir ventana principal
            Timer timer = new Timer(1000, e -> {
                abrirVentanaPrincipal(personaLogueada);
            });
            timer.setRepeats(false);
            timer.start();
            
        } else {
            mostrarMensaje("✗ Usuario o contraseña incorrectos", new Color(231, 76, 60));
            txtContrasena.setText("");
            txtContrasena.requestFocus();
        }
    }
    
    private void abrirVentanaPrincipal(Persona persona) {
        // Cerrar ventana de login
        this.dispose();
        
        // TODO: Crear VentanaPrincipal después
        System.out.println("Login exitoso: " + persona.getNombre());
        System.out.println("Tipo: " + persona.getTipo());
        
        // Por ahora, mostrar mensaje
        JOptionPane.showMessageDialog(null, 
            "¡Bienvenido " + persona.getNombre() + "!\n" +
            "Tipo de usuario: " + persona.getTipo() + "\n\n" +
            "Ventana principal en desarrollo...",
            "Login Exitoso",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirVentanaRegistro() {
        // TODO: Crear VentanaRegistro después
        JOptionPane.showMessageDialog(this,
            "Ventana de registro en desarrollo...\n\n" +
            "Próximamente podrás registrar nuevos usuarios desde aquí.",
            "Registro",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarMensaje(String mensaje, Color color) {
        lblMensaje.setText(mensaje);
        lblMensaje.setForeground(color);
    }
    
    // Método main para probar
    public static void main(String[] args) {
        // Configurar look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new VentanaLogin();
        });
    }
}