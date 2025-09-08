package Personal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalDAOImpl implements PersonalDAO {

    private static final String SQL_INSERT = "INSERT INTO personal (nombre, rol) VALUES (?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM personal WHERE idPersonal = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM personal";
    private static final String SQL_DELETE = "DELETE FROM personal WHERE idPersonal = ?";


    @Override
    public long guardar(Personal p) throws SQLException {
        //Por hacer
        return 0;
    }

    @Override
    public Personal buscarPorId(long id) throws SQLException {
        //Por hacer
        return null;
    }

    @Override
    public List<Personal> listarTodos() throws SQLException {
        //Por hacer
        return List.of();
    }

    @Override
    public boolean eliminar(long id) throws SQLException {
        //Por hacer
        return false;
    }
}
