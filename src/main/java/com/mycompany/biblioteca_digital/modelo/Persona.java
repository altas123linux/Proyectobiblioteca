package com.mycompany.biblioteca_digital.modelo;

public abstract class Persona {
    
    protected int idPersona;
    protected String cedula;
    protected String nombre;
    protected String apellido;
    protected String mail;
    protected String dirrecion;
    
    public Persona(){
    } //constructor vacio para unir con la base de datos
    
    public Persona(String cedula, String nombre, String apellido, String mail, String dirrecion){
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.dirrecion = dirrecion; //Constructor con parametros para prueba
    }
    
    public int getIdPersona() {
        return idPersona;
    }    
    public void setIdPersona(int idPersona){
        this.idPersona = idPersona;
    }
    
    public String getCedula(){
        return cedula;
    }
    public void setCedula(String cedula){
        this.cedula = cedula;
    }
    
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getApellido(){
        return apellido;
    }
    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public String getMail(){
        return mail;
    }
    public void setMail(String mail){
        this.mail = mail;
    }
    

    public String getDirrecion(){
        return dirrecion;
    }
    public void setDirrecion(String dirrecion){
        this.dirrecion = dirrecion;
    }
    
    public abstract String getTipo();
    
    @Override
    public String toString(){
        return"Persona{" +
                "cedula:'" + cedula + '\'' +
                ", nombre:'" + nombre + '\'' +
                ", apellido.'" + apellido + '\'' +
                ", email:'" + mail + '\'' +
                '}';
    }
}