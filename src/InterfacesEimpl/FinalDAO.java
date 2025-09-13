package InterfacesEimpl;

import Clientes.Cliente;
import Mesas.Mesa;
import Personal.Personal;
import Productos.Bebida;
import Productos.Comida;
import Productos.Postre;

import java.sql.SQLException;

public final class FinalDAO implements GeneralDAO{
    @Override
    public long guardarCliente(Cliente cli) throws SQLException {
        return 0;
    }

    @Override
    public long guardarPersonal(Personal per) throws SQLException {
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
