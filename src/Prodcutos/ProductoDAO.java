package Prodcutos;

import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {
    long guardar(Producto p) throws SQLException;
    Producto buscarPorId(long id) throws SQLException;
    List<Producto> listarTodos() throws SQLException;
    boolean eliminar(long id) throws SQLException;
}
