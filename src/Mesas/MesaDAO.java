package Mesas;

import java.sql.SQLException;
import java.util.List;

public interface MesaDAO {

    int guardar(Mesa mesa) throws SQLException;
    Mesa buscarPorNum(int numMesa) throws SQLException;
    List<Mesa> listarTodas() throws SQLException;
    boolean actualizarEstado(int numMesa, boolean estado) throws SQLException;
    boolean eliminar(int numMesa) throws SQLException;
}
