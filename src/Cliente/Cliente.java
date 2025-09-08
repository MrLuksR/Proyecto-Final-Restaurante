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

    public long getIdCliente() { return idCliente; }
    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<String> getTelefonos() { return telefonos; }
    public void agregarTelefono(String telefono) { this.telefonos.add(telefono); }

    public Fidelidad getFidelidad() { return fidelidad; }
    public void setFidelidad(Fidelidad fidelidad) { this.fidelidad = fidelidad; }

    @Override
    public String toString() {
        return nombre + " [" + fidelidad + "] - Teléfonos: " + telefonos;
    }
}
