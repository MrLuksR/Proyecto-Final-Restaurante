package Modelo;

public class Cliente {
    private String nombre;
    private String apellido;
    private int telefono; // Multivaluado

    public Cliente(String nombre, String apellido, int telf) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telf;
    }

    // Getters y setters
    public String getNombre() { return this.nombre; }
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() { return this.apellido; }
    public void setApellido(String apellido) {this.apellido = apellido;}

    public int getTelefono() {return this.telefono;}

    public void agregarTelefono(int telefono) {this.telefono = telefono;}

    @Override
    public String toString() {
        return "Nombre: " + this.nombre + "\nTeléfono: " + this.telefono;
    }
}
