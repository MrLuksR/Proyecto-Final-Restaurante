package Productos;

public class Postre extends Producto {

    public Postre(String nombre, double precio, boolean stock, String descripcion) {
        super(nombre, precio, "Postre", stock, descripcion);
    }

    @Override
    public double calcImp(){
        //A completar
        return getPrecio();
    }
}
