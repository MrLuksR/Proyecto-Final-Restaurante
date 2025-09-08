package Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    private static final String SQL_INSERT = "INSERT INTO clientes (nombre, fidelidad) VALUES (?, ?)";
    private static final String SQL_INSERT_TELEFONO = "INSERT INTO telefonos (idCliente, telefono) VALUES (?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM clientes WHERE idCliente = ?";
    private static final String SQL_SELECT_TELEFONOS = "SELECT telefono FROM cliente_telefonos WHERE idCliente = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM clientes";
    private static final String SQL_DELETE = "DELETE FROM clientes WHERE idCliente = ?";

    @Override
    public long guardar(Cliente c) throws SQLException {
        //Por hacer
        return 0;
    }

    @Override
    public Cliente buscarPorId(long id) throws SQLException {
        //Por hacer
        return null;
    }

    @Override
    public List<Cliente> listarTodos() throws SQLException {
        //Por hacer
        return List.of();
    }

    @Override
    public boolean eliminar(long id) throws SQLException {
        //Por hacer
        return false;
    }
}
