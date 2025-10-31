package Modelo;

public class Postre extends Producto {
    public Postre(String nombre, double precio, int stock, String descripcion) {
        super(nombre, precio, "Postre", stock, descripcion);
    }

    @Override
    public double getTotal(int impuesto, double  prop){
        double total = getPrecio() + getPrecio() * (impuesto/100) + prop;
        return total;
    }

    public double getTotal(int impuesto){
        double total = getPrecio() + getPrecio() * (impuesto/100);
        return total;
    }
}
