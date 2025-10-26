package InterfacesEimpl;

import Modelo.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class FinalDAO implements GeneralDAO{
    public Connection conn;

    public FinalDAO(Connection conn){
        this.conn = conn;
    }
    @Override
    public long guardarCliente(Cliente cli) throws SQLException {
        return 0;
    }

    @Override
    public long guardarPersonal(Personal per) throws SQLException {
        return 0;
    }

    @Override
    public long guardarComida(Comida com) throws SQLException {
        return 0;
    }

    @Override
    public long guardarBebida(Bebida beb) throws SQLException {
        return 0;
    }

    @Override
    public long guardarPostre(Postre pos) throws SQLException {
        return 0;
    }

    @Override
    public long guardarMesa(Mesa mesa) throws SQLException {
        return 0;
    }

    @Override
    public void guardarReserva(Reserva reserva) throws SQLException {
        String sqlConsultar = "SELECT cliente FROM reservas WHERE fecha = ? AND hora = ?";
        PreparedStatement pst = conn.prepareStatement(sqlConsultar);
        pst.setString(1, reserva.getFecha().toString());
        pst.setString(2, reserva.getHora().toString());
        ResultSet rs = pst.executeQuery();

        if (!rs.next()) {
            String sqlGuardar = "INSERT INTO reservas (ciCliente, cliente, fecha, hora, mesa, personas) VALUES (?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(sqlGuardar);
            pst.setString(1, reserva.getCiCliente());
            pst.setString(2, reserva.getApellido());
            pst.setString(3, reserva.getFecha().toString());
            pst.setString(4, reserva.getHora().toString());
            pst.setInt(5, reserva.getMesa());
            pst.setInt(6, reserva.getPersonas());
            pst.execute();}
        else{
            JOptionPane.showMessageDialog(null, "Reserva existente\nCliente: " + rs.getString(1));
        }
        pst.close();
    }
}
