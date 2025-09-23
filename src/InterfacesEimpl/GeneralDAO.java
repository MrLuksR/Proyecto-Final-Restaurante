package InterfacesEimpl;

import Clientes.Cliente;
import Mesas.Mesa;
import Personal.Personal;
import Productos.Bebida;
import Productos.Comida;
import Productos.Postre;

import java.sql.SQLException;

public interface GeneralDAO {
    long guardarCliente(Cliente cli) throws SQLException;
    long guardarPersonal(Personal per) throws SQLException;
    long guardarComida(Comida com) throws SQLException;
    long guardarBebida(Bebida beb) throws SQLException;
    long guardarPostre(Postre pos) throws SQLException;
    long guardarMesa(Mesa mesa) throws SQLException;
}