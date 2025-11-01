package Modelo;

public class Pedido {
    private int id;
    private int numMesa;
    private String estado;
    private String producto;
    private int cantidad;
    private double totales;

    public Pedido(int id, int numMesa, String estado, String producto, int cantidad, double totales) {
        this.id = id;
        this.estado = estado;
        this.numMesa = numMesa;
        this.producto = producto;
        this.cantidad = cantidad;
        this.totales = totales;
    }

    // Getters and Setters
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getNumMesa() {return numMesa;}

    public void setNumMesa(int numMesa) {this.numMesa = numMesa;}

    public String getEstado() {return estado;}

    public void setEstado(String estado) {this.estado = estado;}

    public String getProducto() {return producto;}

    public void setProducto(String producto) {this.producto = producto;}

    public int getCantidad() {return cantidad;}

    public void setCantidad(int cantidad) {this.cantidad = cantidad;}

    public double getTotales() {return totales;}

    public void setTotales(double totales) {this.totales = totales;}
}
