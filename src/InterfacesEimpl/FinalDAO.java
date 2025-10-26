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
        // Consulta si no existe una mesa con la misma fecha y la misma hora de reserva
        String sqlConsMesaIgual = "SELECT cliente FROM reservas WHERE fecha = ? AND hora = ? AND mesa = ?";
        PreparedStatement pst = conn.prepareStatement(sqlConsMesaIgual);
        pst.setString(1, reserva.getFecha().toString());
        pst.setString(2, reserva.getHora().toString());
        pst.setInt(3, reserva.getMesa());
        ResultSet rs = pst.executeQuery();

        // Si no es así entonces sigue
        if (!rs.next()) {
            // Consulta si la mesa a reservar está libre
            String sqlConsMesaLibre = "SELECT estado FROM mesas WHERE numMesa = ? AND estado = 'Libre'";
            pst = conn.prepareStatement(sqlConsMesaLibre);
            pst.setInt(1, reserva.getMesa());
            rs = pst.executeQuery();

            if (rs.next()){
                // En caso de que sí lo esté entonces guardar la reserva en la base de datos
                String sqlGuardar = "INSERT INTO reservas (ciCliente, cliente, fecha, hora, mesa, personas) VALUES (?, ?, ?, ?, ?, ?)";
                pst = conn.prepareStatement(sqlGuardar);
                pst.setString(1, reserva.getCiCliente());
                pst.setString(2, reserva.getApellido());
                pst.setString(3, reserva.getFecha().toString());
                pst.setString(4, reserva.getHora().toString());
                pst.setInt(5, reserva.getMesa());
                pst.setInt(6, reserva.getPersonas());
                pst.execute();
                String sqlNuevoEstMesa = "UPDATE mesas SET estado = 'Reservada' WHERE numMesa = ?";
                pst = conn.prepareStatement(sqlNuevoEstMesa);
                pst.setInt(1, reserva.getMesa());
                pst.execute();
            }
            else{
                String sqlEstadoMesa = "SELECT estado FROM mesas WHERE numMesa = ?";
                pst = conn.prepareStatement(sqlEstadoMesa);
                pst.setInt(1, reserva.getMesa());
                rs = pst.executeQuery();
                if (rs.next())
                    JOptionPane.showMessageDialog(null, "No es posible reservar\nMesa: " + reserva.getMesa() + " (" + rs.getString(1) + ")", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Reserva existente\nCliente: " + rs.getString(1));
        }
        pst.close();
    }
}
