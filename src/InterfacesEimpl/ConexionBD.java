package InterfacesEimpl;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/restaurante";
    private static final String USER = "root";
    private static final String PASS = "6114";

    public static Connection getConnection() throws SQLException {
        try{
            return DriverManager.getConnection(URL, USER, PASS);
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error de conexión con base de datos:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}