package Personal;

public class Personal {

    private long idPersonal;
    private String nombre;
    private String rol; // Ej: Mesero, Cocinero, Administrador

    public Personal(long idPersonal, String nombre, String rol) {
        this.idPersonal = idPersonal;
        this.nombre = nombre;
        this.rol = rol;
    }

    public long getIdPersonal() { return this.idPersonal; }
    public String getNombre() { return this.nombre; }
    public String getRol() { return this.rol; }

    public void setIdPersonal(long idPersonal) { this.idPersonal = idPersonal; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return this.nombre + " (" + this.rol + ")";
    }
}
