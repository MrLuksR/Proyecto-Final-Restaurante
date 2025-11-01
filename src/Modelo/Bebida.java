package Modelo;

public class Bebida extends Producto {
    public Bebida(String nombre, double precio, int stock, String descripcion) {
        super(nombre, precio, "Bebida", stock, descripcion);
    }

    @Override
    public double getTotal(float impuesto){
        double total = getPrecio() + getPrecio() * (impuesto/100);
        return total;
    }

    public double getTotal(int impuesto){
        double total = getPrecio() + getPrecio() * (impuesto/100);
        return total;
    }


}
