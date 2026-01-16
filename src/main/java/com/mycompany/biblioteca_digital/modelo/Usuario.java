package com.mycompany.biblioteca_digital.modelo;

public class Usuario extends Persona {
    
    private String usuario;
    private String contraseña;
    private int librosPrestados;
    private int maxLibrosPrestamo;
    private boolean activo;
    
    public Usuario(){
        super();
        this.librosPrestados = 0;
        this.maxLibrosPrestamo = 3;
        this.activo = true;
    }
    public Usuario(String cedula, String nombre, String apellido, String mail,
            String dirrecion, String usuario, String contraseña){
        
        super(cedula, nombre, apellido, mail, dirrecion);
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.librosPrestados = 0;
        this.maxLibrosPrestamo = 3;
        this.activo = true;
    }
    
    @Override
    public String getTipo(){
        return "USUARIO";
    }

    public String getUsuario(){
        return usuario;
    }
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    
    public String getContrasena(){
        return contraseña;
    }
    public void setContrasena(String contrasena){
        this.contraseña = contrasena;
    }
    
    public int getLibrosPrestados(){
        return librosPrestados;
    }
    public void setLibrosPrestados(int librosPrestados){
        this.librosPrestados = librosPrestados;
    }
    
    public int getMaxLibrosPrestamo(){
        return maxLibrosPrestamo;
    }
    public void setMaxLibrosPrestamo(int maxLibrosPrestamo){
        this.maxLibrosPrestamo = maxLibrosPrestamo;
    }
    
    public boolean isActivo(){
        return activo;
    }
    public void setActivo(boolean activo){
        this.activo = activo;
    }
    
     public boolean puedePedirLibros() {
        return activo && librosPrestados < maxLibrosPrestamo;
    }
    
    public void incrementarLibrosPrestados() {
        if (puedePedirLibros()) {
            librosPrestados++;
        } else {
            throw new IllegalStateException("El usuario no puede pedir más libros");
        }
    }
    
    public void decrementarLibrosPrestados() {
        if (librosPrestados > 0) {
            librosPrestados--;
        }
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "usuario:'" + usuario + '\'' +
                ", nombre:'" + getNombre() + '\'' +
                ", apellido:'" + getApellido() + '\'' +
                ", cedula:'" + getCedula() + '\'' +
                ", librosPrestados:" + librosPrestados +
                ", maxLibrosPrestamo:" + maxLibrosPrestamo +
                ", activo:" + activo +
                '}';
    }
}