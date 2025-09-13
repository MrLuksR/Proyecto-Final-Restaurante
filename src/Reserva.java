import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private int idCliente;
    private int numMesa;
    private LocalDate fecha;
    private LocalTime hora;

    public Reserva(int idCliente, int numMesa) {
        this.idCliente = idCliente;
        this.numMesa = numMesa;
    }

    void reservar(LocalDate fecha, LocalTime hora) {
        // Conexion con base de datos y reservar
    }
}
