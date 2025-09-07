import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection obtenerConexion() {
        String url = "jdbc:mysql://localhost:3306/*****";
        String user = "root";
        String password = "******";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos:");
            e.printStackTrace();
            return null;
        }
    }
}