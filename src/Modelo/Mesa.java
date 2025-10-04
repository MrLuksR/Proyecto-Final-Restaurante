package Modelo;

public class Mesa {
    private int numMesa;     // PK
    private boolean estado;  // true = disponible, false = ocupada
    private int capacidad;   // cantidad de personas

    public Mesa(int numMesa, boolean estado, int capacidad) {
        this.numMesa = numMesa;
        this.estado = estado;
        this.capacidad = capacidad;
    }

    public int getNumMesa() { return this.numMesa; }
    public boolean getEstado() { return this.estado; }
    public int getCapacidad() { return this.capacidad; }

    public void setNumMesa(int numMesa) { this.numMesa = numMesa; }
    public void setEstado(boolean estado) { this.estado = estado; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    @Override
    public String toString() {
        return "Modelo.Mesa " + this.numMesa + " - " + (this.estado ? "Disponible" : "Ocupada") + " - Capacidad: " + this.capacidad;
    }
}
