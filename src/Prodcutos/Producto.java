package Prodcutos;

//Clase abstracta
public abstract class Producto {

    private long idProducto;
    private String nombre;
    private double precio;
    private String categoria;
    private boolean estado;
    private String descripcion;

    public Producto(long idProducto, String nombre, double precio, String categoria,
                    boolean estado, String descripcion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public long getIdProducto() { return this.idProducto; }
    public String getNombre() { return this.nombre; }
    public double getPrecio() { return this.precio; }
    public String getCategoria() { return this.categoria; }
    public boolean getEstado() { return this.estado; }
    public String getDescripcion() { return this.descripcion; }

    public void setIdProducto(long idProducto) { this.idProducto = idProducto; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setEstado(boolean estado) { this.estado = estado; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public abstract double calcularImpuesto();

    @Override
    public String toString() {
        return nombre + " - $" + precio + " [" + categoria + "] " + (estado ? "(Disponible)" : "(No disponible)");
    }
}

