package Productos;

public class Bebida extends Producto {

    public Bebida(String nombre, double precio, boolean stock, String descripcion) {
        super(nombre, precio, "Bebida", stock, descripcion);
    }

    @Override
    public double calcImp(){
        //A completar
        return getPrecio();
    }
}