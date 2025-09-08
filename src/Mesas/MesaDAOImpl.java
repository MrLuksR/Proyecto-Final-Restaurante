package Mesas;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MesaDAOImpl implements MesaDAO {

    private static final String SQL_INSERT = "INSERT INTO mesas (numMesa, estado, capacidad) VALUES (?,?,?)";
    private static final String SQL_SELECT_BY_NUM = "SELECT * FROM mesas WHERE numMesa = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM mesas";
    private static final String SQL_UPDATE_ESTADO = "UPDATE mesas SET estado = ? WHERE numMesa = ?";
    private static final String SQL_DELETE = "DELETE FROM mesas WHERE numMesa = ?";


    @Override
    public int guardar(Mesa mesa) throws SQLException {
        //Por hacer
        return 0;
    }

    @Override
    public Mesa buscarPorNum(int numMesa) throws SQLException {
        //Por hacer
        return null;
    }

    @Override
    public List<Mesa> listarTodas() throws SQLException {
        //Por hacer
        return null;
    }

    @Override
    public boolean actualizarEstado(int numMesa, boolean estado) throws SQLException {
        //Por hacer
        return false;
    }

    @Override
    public boolean eliminar(int numMesa) throws SQLException {
        //Por hacer
        return false;
    }
}
