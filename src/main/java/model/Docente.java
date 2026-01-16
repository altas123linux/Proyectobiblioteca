package model;

/**
 *
 * @author ALEJANDRO
 */
public class Docente extends Usuario {
    
    private String facultad;
   
    private int limiteLibros; // Ejemplo: un docente puede pedir más libros que un estudiante
    private boolean activo;

    // Constructor vacío
    public Docente() {
        super();
        this.limiteLibros = 10; // Valor por defecto para docentes
        this.activo = true;
    }

    // Constructor con parámetros
    public Docente(String cedula, String nombre, String apellido, String mail, 
                   String direccion, String facultad) {
        
        super(cedula, nombre, apellido, mail, direccion);
        this.facultad = facultad;
        this.limiteLibros = 10;
        this.activo = true;
    }

   
    @Override
    public String getTipo() {
        return "DOCENTE";
    }

    // Getters y Setters
    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public int getLimiteLibros() {
        return limiteLibros;
    }

    public void setLimiteLibros(int limiteLibros) {
        this.limiteLibros = limiteLibros;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "nombre:'" + getNombre() + '\'' +
                ", apellido:'" + getApellido() + '\'' +
                ", facultad:'" + facultad + '\'' +
                ", limiteLibros:" + limiteLibros +
                '}';
    }
}