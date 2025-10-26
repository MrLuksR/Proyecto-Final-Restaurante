package InterfacesEimpl;

import Modelo.*;

import java.sql.SQLException;

public interface GeneralDAO {
    long guardarCliente(Cliente cli) throws SQLException;
    long guardarPersonal(Personal per) throws SQLException;
    long guardarComida(Comida com) throws SQLException;
    long guardarBebida(Bebida beb) throws SQLException;
    long guardarPostre(Postre pos) throws SQLException;
    long guardarMesa(Mesa mesa) throws SQLException;
    void guardarReserva(Reserva reserva) throws SQLException;
}