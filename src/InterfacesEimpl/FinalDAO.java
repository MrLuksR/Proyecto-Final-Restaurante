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
    public void guardarComida(Comida com) throws SQLException {
        String sqlAgreg = "INSERT INTO productos (nombre, precio, categoria, stock, descripcion) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pst = conn.prepareStatement(sqlAgreg);
        pst.setString(1, com.getNombre());
        pst.setDouble(2, com.getPrecio());
        pst.setString(3, com.getCategoria());
        pst.setInt(4, com.getStock());
        pst.setString(5, com.getDescripcion());
        pst.executeUpdate();
        pst.close();
    }

    @Override
    public void guardarBebida(Bebida beb) throws SQLException {
        String sqlAgreg = "INSERT INTO productos (nombre, precio, categoria, stock, descripcion) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pst = conn.prepareStatement(sqlAgreg);
        pst.setString(1, beb.getNombre());
        pst.setDouble(2, beb.getPrecio());
        pst.setString(3, beb.getCategoria());
        pst.setInt(4, beb.getStock());
        pst.setString(5, beb.getDescripcion());
        pst.executeUpdate();
        pst.close();
    }

    @Override
    public void guardarPostre(Postre pos) throws SQLException {
        String sqlAgreg = "INSERT INTO productos (nombre, precio, categoria, stock, descripcion) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pst = conn.prepareStatement(sqlAgreg);
        pst.setString(1, pos.getNombre());
        pst.setDouble(2, pos.getPrecio());
        pst.setString(3, pos.getCategoria());
        pst.setInt(4, pos.getStock());
        pst.setString(5, pos.getDescripcion());
        pst.executeUpdate();
        pst.close();
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

    @Override
    public void guardarFactura(Factura fact) throws SQLException {
        String sqlFac = "INSERT INTO facturas (nombre, cajero, fecha, hora, subTotal, impuesto, propina, metodoPago) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = conn.prepareStatement(sqlFac);
            pst.setString(1, fact.getNombre());
            pst.setString(2, fact.getPersonal());
            pst.setString(3, fact.getFecha().toString());
            pst.setString(4, fact.getTiempo().toString());
            pst.setDouble(5, fact.getSubTotal());
            pst.setDouble(6, fact.getImpuesto());
            pst.setDouble(7, fact.getPropina());
            pst.setString(8, fact.getMetPago());
            pst.execute();
            pst.close();
    }

}
