package Modelo;

public class Comida extends Producto{
    public Comida(String nombre, double precio, int stock, String descripcion) {
        super(nombre, precio, "Comida", stock, descripcion);
    }

    @Override
    public double getTotal(float impuesto){
        double total = getPrecio() + getPrecio() * (impuesto/100);
        return total;
    }
}
