package Modelo;

import java.time.LocalDate;

public class Reserva {
    private String ciCliente;
    private String apellido;
    private LocalDate fecha;
    private String hora;
    private int mesa;
    private int personas;

    // Constructor
    public Reserva(String ciCliente, String apellido, LocalDate fecha, String hora, int mesa, int personas) {
        this.ciCliente = ciCliente;
        this.apellido = apellido;
        this.fecha = fecha;
        this.hora = hora;
        this.mesa = mesa;
        this.personas = personas;
    }


    // Getters y setters

    public String getCiCliente() {
        return ciCliente;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public int getMesa() {
        return mesa;
    }

    public int getPersonas() {
        return personas;
    }
}
