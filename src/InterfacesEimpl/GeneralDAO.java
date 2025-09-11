package InterfacesEimpl;

import Clientes.Cliente;
import Mesas.Mesa;
import Productos.Bebida;
import Productos.Comida;
import Productos.Postre;

import java.sql.SQLException;

public interface GeneralDAO {
    long guardarUser(Usuario user) throws SQLException;
    long giardarCliente(Cliente cliente) throws SQLException;
    long guardarComida(Comida com) throws SQLException;
    long guardarBebida(Bebida beb) throws SQLException;
    long guardarPostre(Postre pos) throws SQLException;
    long guardarMesa(Mesa mesa) throws SQLException;
}
