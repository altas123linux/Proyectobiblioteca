package com.mycompany.biblioteca_digital.modelo;

public class Administrador extends Persona {
    
    private String usuario;
    private String contraseña;
    private String rol; 
    private boolean activo;
    
    public Administrador() {
        super();
        this.rol = "ADMIN";
        this.activo = true;
    }
    
    public Administrador(String cedula, String nombre, String apellido, String mail, 
                         String direccion, String usuario, String contraseña) {
        
        super(cedula, nombre, apellido, mail, direccion);
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = "ADMIN";
        this.activo = true;
    }
    
    @Override
    public String getTipo() {
        return "ADMINISTRADOR";
    }
    
    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getContrasena() {
        return contraseña;
    }
    
    public void setContrasena(String contrasena) {
        this.contraseña = contrasena;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    // Métodos auxiliares
    public boolean esSuperAdmin() {
        return "SUPER_ADMIN".equals(rol);
    }
    
    @Override
    public String toString() {
        return "Administrador{" +
                "usuario:'" + usuario + '\'' +
                ", nombre:'" + getNombre() + '\'' +
                ", apellido:'" + getApellido() + '\'' +
                ", cedula:'" + getCedula() + '\'' +
                ", rol='" + rol + '\'' +
                ", activo:" + activo +
                '}';
    }
}
