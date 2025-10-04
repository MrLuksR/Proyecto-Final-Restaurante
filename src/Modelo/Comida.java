package Modelo;

public class Comida extends Producto{
    public Comida(String nombre, double precio, boolean stock, String descripcion) {
        super(nombre, precio, "Modelo.Comida", stock, descripcion);
    }

    @Override
    public double calcImp(){
        //A completar
        return getPrecio();
    }
}
