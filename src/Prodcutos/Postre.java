package Prodcutos;

public class Postre extends Producto {

    public Postre(long idProducto, String nombre, double precio, boolean estado, String descripcion) {
        super(idProducto, nombre, precio, "Postre", estado, descripcion);
    }

    @Override
    public double calcularImpuesto() {
        //A completar
        return getPrecio();
    }
}
