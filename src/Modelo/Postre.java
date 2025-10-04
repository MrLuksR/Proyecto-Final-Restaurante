package Modelo;

public class Postre extends Producto {
    public Postre(String nombre, double precio, boolean stock, String descripcion) {
        super(nombre, precio, "Modelo.Postre", stock, descripcion);
    }

    @Override
    public double calcImp(){
        //A completar
        return getPrecio();
    }
}
