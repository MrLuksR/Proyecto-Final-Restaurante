package Personal;

public class Personal {

    private int cedula;
    private String nombre;
    private String rol; // Ej: Mesero, Cocinero, Administrador

    public Personal(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getNombre() { return this.nombre; }
    public String getRol() { return this.rol; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRol(String rol) { this.rol = rol; }

    public int getCedula() {return cedula;}
    public void setCedula(int cedula) {this.cedula = cedula;}

    @Override
    public String toString() {
        return this.nombre + " (" + this.rol + ")";
    }
}
