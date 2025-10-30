package Modelo;

public class Bebida extends Producto {
    public Bebida(String nombre, double precio, int stock, String descripcion) {
        super(nombre, precio, "Modelo.Bebida", stock, descripcion);
    }

    @Override
    public double calcImp(){
        //A completar
        return getPrecio();
    }
}
