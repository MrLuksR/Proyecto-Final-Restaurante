package InterfacesEimpl;

import Modelo.*;

import java.sql.SQLException;

public interface GeneralDAO {
    long guardarCliente(Cliente cli) throws SQLException;
    long guardarPersonal(Personal per) throws SQLException;
    void guardarComida(Comida com) throws SQLException;
    void guardarBebida(Bebida beb) throws SQLException;
    void guardarPostre(Postre pos) throws SQLException;
    long guardarMesa(Mesa mesa) throws SQLException;
    void guardarReserva(Reserva reserva) throws SQLException;
}