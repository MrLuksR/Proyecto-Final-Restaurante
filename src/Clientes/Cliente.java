package Clientes;

import java.util.ArrayList;
import java.util.List;

public class Cliente {


    private String nombre;
    private String apellido;
    private List<String> telefonos; // Multivaluado

    public Cliente(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefonos = new ArrayList<>();
    }

    // Getters y setters
    public String getNombre() { return this.nombre; }
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() { return this.apellido; }
    public void setApellido(String apellido) {this.apellido = apellido;}

    public List<String> getTelefonos() { return this.telefonos; }

    public void agregarTelefono(String telefono) {this.telefonos.add(telefono);}

    @Override
    public String toString() {
        return "Nombre: " + this.nombre + "\nTeléfonos: " + this.telefonos;
    }
}
