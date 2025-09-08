package Prodcutos;

public class Bebida extends Producto {

    public Bebida(long idProducto, String nombre, double precio, boolean estado, String descripcion) {
        super(idProducto, nombre, precio, "Bebida", estado, descripcion);
    }

    @Override
    public double calcularImpuesto() {
        //A completar
        return getPrecio();
    }
}