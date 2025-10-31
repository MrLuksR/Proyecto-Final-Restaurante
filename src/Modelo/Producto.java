package Modelo;

public abstract class Producto {
    private String nombre;
    private double precio;
    private String categoria;
    private int stock;
    private String descripcion;

    public Producto(String nombre, double precio, String categoria, int stock, String descripcion){
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
        this.descripcion = descripcion;
    }

    // Getters
    public String getNombre() { return this.nombre; }
    public double getPrecio() { return this.precio; }
    public String getCategoria() { return this.categoria; }
    public int getStock() { return this.stock; }
    public String getDescripcion() { return this.descripcion; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setStock(int stock) { this.stock = stock; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public abstract double getTotal(int impuesto, double prop);

    @Override
    public String toString() {
        return nombre + " - $" + precio + " [" + categoria + "] " + " Stock: " + stock + " Descripcion: " + descripcion;
    }
}
