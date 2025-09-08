package Cliente;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    public enum Fidelidad { BRONCE, PLATA, ORO, DIAMANTE }

    private long idCliente;
    private String nombre;
    private List<String> telefonos; // Multivaluado
    private Fidelidad fidelidad;

    public Cliente(long idCliente, String nombre, Fidelidad fidelidad) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.fidelidad = fidelidad;
        this.telefonos = new ArrayList<>();
    }

    public long getIdCliente() { return this.idCliente; }
    public String getNombre() { return this.nombre; }
    public List<String> getTelefonos() { return this.telefonos; }
    public Fidelidad getFidelidad() { return this.fidelidad; }

    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFidelidad(Fidelidad fidelidad) { this.fidelidad = fidelidad; }

    public void agregarTelefono(String telefono) { this.telefonos.add(telefono); }

    @Override
    public String toString() {
        return this.nombre + " [" + this.fidelidad + "] - Teléfonos: " + this.telefonos;
    }
}
