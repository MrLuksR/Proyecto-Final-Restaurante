package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Factura {
    private String nombre;
    private String personal;
    private LocalDate fecha;
    private LocalTime tiempo;
    private Pedido[] pedido;
    private double subTotal;
    private double impuesto;
    private double propina;
    private String metPago;

    public Factura (String nombre, String personal, LocalDate fecha, LocalTime tiempo, Pedido[] pedido, double propina, String metPago) {
        this.nombre = nombre;
        this.personal = personal;
        this.fecha = fecha;
        this.tiempo = tiempo;
        this.pedido = pedido;
        this.subTotal = this.getSubTotalPedidos();
        this.impuesto = (this.getSubTotalPedidos() * 0.22);
        this.propina = propina;
        this.metPago = metPago;
    }

    // Getters y setters
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getPersonal() {return personal;}
    public void setPersonal(String personal) {this.personal = personal;}
    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}
    public LocalTime getTiempo() {return tiempo;}
    public void setTiempo(LocalTime tiempo) {this.tiempo = tiempo;}
    public Pedido[] getPedido() {return pedido;}
    public void setPedido(Pedido[] pedido) {this.pedido = pedido;}
    public double getPropina() {return propina;}
    public void setPropina(double propina) {this.propina = propina;}
    public String getMetPago() {return metPago;}
    public void setMetPago(String metPago) {this.metPago = metPago;}
    public double getSubTotal() {return subTotal;}
    public void setSubTotal(double subTotal) {this.subTotal = subTotal;}
    public double getImpuesto() {
        return impuesto;
    }
    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    // Obtener sub total
    double getSubTotalPedidos(){
        double subTotal = 0;
        for (int i = 0; i<pedido.length; i++){
            subTotal += pedido[i].getTotales();
        }
        return subTotal;
    }
}
