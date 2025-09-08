package Prodcutos;

public class Comida extends Producto {

    public Comida(long idProducto, String nombre, double precio, boolean estado, String descripcion) {
        super(idProducto, nombre, precio, "Comida", estado, descripcion);
    }

    @Override
    public double calcularImpuesto() {
        //A completar
        return getPrecio();
    }
}