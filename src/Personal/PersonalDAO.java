package Personal;

import java.sql.SQLException;
import java.util.List;

public interface PersonalDAO {
    long guardar(Personal p) throws SQLException;
    Personal buscarPorId(long id) throws SQLException;
    List<Personal> listarTodos() throws SQLException;
    boolean eliminar(long id) throws SQLException;
}
