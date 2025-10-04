package Modelo;

public class Personal {
    private int cedula;
    private String nombre;
    private String apellido;
    private String rol; // Ej: Mesero, Cocinero, Administrador
    private String usuario;
    private String password;

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

    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}

    public String getUsuario() {return usuario;}
    public void setUsuario(String usuario) {this.usuario = usuario;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    @Override
    public String toString() {
        return this.nombre + " (" + this.rol + ")";
    }
}
