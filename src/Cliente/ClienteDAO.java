package Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    long guardar(Cliente c) throws SQLException;
    Cliente buscarPorId(long id) throws SQLException;
    List<Cliente> listarTodos() throws SQLException;
    boolean eliminar(long id) throws SQLException;
}
