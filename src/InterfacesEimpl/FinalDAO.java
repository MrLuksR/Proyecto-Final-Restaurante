package InterfacesEimpl;

import Clientes.Cliente;
import Mesas.Mesa;
import Productos.Bebida;
import Productos.Comida;
import Productos.Postre;

import java.sql.SQLException;

public final class FinalDAO implements GeneralDAO{
    @Override
    public long guardarUser(Usuario user) throws SQLException {
        // ACA HACE TODA LA CONEXION CON LA BASE Y GUARDA LOS DATOS
        return 0;
    }

    @Override
    public long giardarCliente(Cliente cliente) throws SQLException {
        return 0;
    }

    @Override
    public long guardarComida(Comida com) throws SQLException {
        return 0;
    }

    @Override
    public long guardarBebida(Bebida beb) throws SQLException {
        return 0;
    }

    @Override
    public long guardarPostre(Postre pos) throws SQLException {
        return 0;
    }

    @Override
    public long guardarMesa(Mesa mesa) throws SQLException {
        return 0;
    }
}
