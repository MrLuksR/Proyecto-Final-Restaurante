package UI;

import InterfacesEimpl.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FormMain extends JFrame {
    private JPanel vntMain;
    private JLabel lblNombre;
    private JSplitPane splMain;
    private JPanel vntButtons;
    private JPanel vntInfoBase;
    private JButton btnReservas;
    private JButton btnPedidos;
    private JButton btnHistorial;
    private JLabel lblInformación;
    public Connection conn;

    // Elementos de la ventana RESERVAS
    private JButton btnAgregar = new JButton("Agregar");
    private JButton btnEliminar = new JButton("Eliminar");
    private JTable tablaReservas;
    private DefaultTableModel mdlTblReservas; // Modelo de tabla para poder borrar las filas
    private JComboBox<String> cmbReservas = new JComboBox<>();


    public FormMain() {
        // MenuBar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("Opción 1"));
        menuBar.add(new JMenu("Opción 2"));
        menuBar.add(new JMenu("Opción 3"));

        setJMenuBar(menuBar);

        // SplitPane
        splMain.setRightComponent(vntInfoBase);
        splMain.setEnabled(false);
        splMain.setDividerLocation(150);

        // Iniciar Pantalla
        setContentPane(vntMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cambiar fondo(verde menta)
        getContentPane().setBackground(new Color(123, 214, 144));

        // Abrir maximizada (ocupa toda la pantalla pero con bordes)
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //ventanaForm.setSize(1366, 768);
        setUndecorated(false); // true = sin bordes, false = con bordes
        setVisible(true);

        //==================================================================

        // LISTENERS DE VENTANAS

        //Listener de Botón RESERVAS
        btnReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splMain.setRightComponent(opcReservas()); // Establecemos la ventana a la derecha del Split
            }
        });
        //Listener de Botón PEDIDOS
        btnPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splMain.setRightComponent(opcPedidos());
            }
        });
        //Listener de Botón HISTORIAL (OPCIÓN DE EJEMPLO, ELIMINAR DESPUÉS)
        btnHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splMain.setRightComponent(opcHistorial());
            }
        });

        //==================================================================

        // LISTENERS DE BOTONES EN VENTANAS

        // Listener de botón agregar (Ventana Reservas)
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Abrir ventana JDialog
            }
        });

        // Listener de botón eliminar (Ventana Reservas)
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opc = JOptionPane.showOptionDialog(null, "¿Eliminar esta reserva?", "Confirmar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                int valor = Integer.parseInt(tablaReservas.getValueAt(tablaReservas.getSelectedRow(), 0).toString());
                if (opc == JOptionPane.YES_OPTION && eliminarFila("reservas", "id", valor)) {
                    mdlTblReservas.removeRow(tablaReservas.getSelectedRow());
                    JOptionPane.showMessageDialog(null, "Reserva Eliminada");
                }
            }
        });
    }

    // Métodos para crear ventanas de los BOTONES DE LA IZQUIERDA DEL SPLIT
    //Botón RESERVAS
    public JPanel opcReservas() {
        splMain.setDividerLocation(150);
        JPanel vntReservas = new JPanel();
        vntReservas.setLayout(null);
        vntReservas.setBounds(vntInfoBase.getBounds());

        // Label del Nombre
        JLabel lblNombre = new JLabel("Reservas");
        lblNombre.setFont(new Font("Tahoma", Font.BOLD, 70));
        lblNombre.setBounds(50, 20, 600, 80);
        lblNombre.setHorizontalAlignment(SwingConstants.LEFT);
        vntReservas.add(lblNombre);

        // Columnas
        String[] columnas = {"ID", "CI del Cliente", "Cliente", "Fecha", "Hora", "Mesa", "Personas"};
        // Datos de ejemplo para observar como se ve en la tabla
        /*Object[][] datosEjemplo = {
                {"1", "52243048", "Moraes", "2025-10-07", "20:00", 5, 2},
                {"2", "34011238", "Albornoz", "2025-10-08", "21:30", 2, 4},
                {"3", "55746049", "Rangel", "2025-12-24", "21:30", 10, 4}
        };*/

        // JComboBox para ordenar
        String[] items = {"ID", "Fecha", "Hora", "Cliente", "Mesa"};
        cmbReservas = new JComboBox(items);
        cmbReservas.setBounds(1060,145,100,30);
        vntReservas.add(cmbReservas);

        // TABLA
        mdlTblReservas = new DefaultTableModel(datosReserva("id"), columnas);
        tablaReservas = new JTable(mdlTblReservas);
        tablaReservas.setDefaultEditor(Object.class, null); // No permite que la tabla sea editable
        tablaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite seleccionar solamente un elemento
        tablaReservas.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollTabla = new JScrollPane(tablaReservas);
        scrollTabla.setBounds(50, 130, 1000, 400);
        vntReservas.add(scrollTabla);

        // Label para ordenar
        JLabel lblOrdenar = new  JLabel("Ordenar por:");
        lblOrdenar.setBounds(1060,125,100,20);
        vntReservas.add(lblOrdenar);

        // Botones de agregar y eliminar
        btnAgregar.setBounds(50, 560, 200, 40);
        vntReservas.add(btnAgregar);

        btnEliminar.setBounds(270, 560, 200, 40);
        btnEliminar.setEnabled(false); // el botón eliminar arranca desactivado
        vntReservas.add(btnEliminar);

        // Activa el button eliminar solo si se selecciona una opción de la tabla
        // getSelectedRow() devuelve el índice de la fila seleccionada
        // Si no hay ninguna fila seleccionada devuelve -1, de lo contrario se habilita el botón eliminar
        tablaReservas.getSelectionModel().addListSelectionListener(e -> {
            boolean filaSeleccionada = tablaReservas.getSelectedRow() != -1;
            btnEliminar.setEnabled(filaSeleccionada);
        });

        // Listener de JComboBox para ordenar
        cmbReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tablaReservas == null) return;
                int cbmIndex = cmbReservas.getSelectedIndex();
                String order = "id";

                switch(cbmIndex) {
                    case 0:
                        order = "id";
                        break;
                    case 1:
                        order = "fecha";
                        break;
                    case 2:
                        order = "hora";
                        break;
                    case 3:
                        order = "cliente";
                        break;
                    case 4:
                        order = "mesa";
                        break;}
                mdlTblReservas.setDataVector(datosReserva(order), columnas);
                tablaReservas.setModel(mdlTblReservas);
            }
        });

        return vntReservas;
    }

    // Botón PEDIDOS
    public JPanel opcPedidos() {
        splMain.setDividerLocation(150);
        JPanel vntPedidos = new JPanel();
        vntPedidos.setLayout(null);
        vntPedidos.setBounds(vntInfoBase.getBounds());

        // Label del Nombre
        JLabel lblNombre = new JLabel("Pedidos");
        lblNombre.setFont(new Font("Tahoma", Font.BOLD, 70));
        lblNombre.setBounds(0,0,vntPedidos.getWidth(),70);
        lblNombre.setHorizontalAlignment(SwingConstants.LEFT);

        vntPedidos.add(lblNombre);
        return vntPedidos;
    }

    // Botón Historial (CAMBIAR ES SOLO EJEMPLO)
    public JPanel opcHistorial() {
        splMain.setDividerLocation(150);
        JPanel vntHistorial = new JPanel();
        vntHistorial.setLayout(null);
        vntHistorial.setBounds(vntInfoBase.getBounds());

        // Label del Nombre
        JLabel lblNombre = new JLabel("Historial");
        lblNombre.setFont(new Font("Tahoma", Font.BOLD, 70));
        lblNombre.setBounds(0,0,vntHistorial.getWidth(),70);
        lblNombre.setHorizontalAlignment(SwingConstants.LEFT);

        vntHistorial.add(lblNombre);
        return vntHistorial;
    }

    // Métodos para traer y eliminar DATOS DENTRO DE LA BASE
    public String[][] datosReserva(String order){
        try {
            String sqlTotalReservas = "SELECT COUNT(id) AS total FROM reservas"; // Cuenta todas las reservas que hay
            PreparedStatement pst = conn.prepareStatement(sqlTotalReservas);
            ResultSet totalRes = pst.executeQuery(); // Número total de reservas
            int total = 0;
            if (totalRes.next()) {
                total = totalRes.getInt("total");
            }

            String[][] matrizReservasFinal = new String[total][7];
            int i = 0;

            // Creamos lo necesario para obtener la fila con datos de reserva
            String sqlReserva= "SELECT id, ciCliente, cliente, fecha, hora, mesa, personas FROM reservas ORDER BY " + order; // Para obtener una fila de reservas

            /* Creamos una variable del tipo PrepareStatement y le pasamos la consulta SQl
            * Luego creamos un ResultSet para comprobar el resultado obtenido de esa consulta*/

            pst = conn.prepareStatement(sqlReserva, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rsReservas = pst.executeQuery();
            while (rsReservas.next()) {
                matrizReservasFinal[i][0] = String.valueOf(rsReservas.getInt("id"));
                matrizReservasFinal[i][1] = rsReservas.getString("ciCliente");
                matrizReservasFinal[i][2] = rsReservas.getString("cliente");
                matrizReservasFinal[i][3] = rsReservas.getString("fecha");
                matrizReservasFinal[i][4] = rsReservas.getString("hora");
                matrizReservasFinal[i][5] = String.valueOf(rsReservas.getInt("mesa"));
                matrizReservasFinal[i][6] = String.valueOf(rsReservas.getInt("personas"));
                i++;
            }

            // Cerramos el PreparedStatement y ResultSet
            rsReservas.close();
            totalRes.close();
            pst.close();
            return matrizReservasFinal;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new String[0][0];
        }
    }

    //==================================================================
    //Botón PEDIDOS

    /*Código
    código
    código*/

    // Métodos para eliminar fila
    public boolean eliminarFila(String tabla, String columna, int valor){
        try{
            String eliminar = "DELETE FROM " +  tabla + " WHERE " + columna + " = " + valor;
            String sqlTotalReservas = eliminar; // Elimina la fila en la base de datos
            PreparedStatement pst = conn.prepareStatement(sqlTotalReservas);
            pst.executeUpdate();
            pst.close();
            return true;
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } // Se utiliza para eliminar fila solamente

    public static void main(String[] args) throws SQLException {
        Connection conn = ConexionBD.getConnection();
        if (conn == null) return;
        FormMain ventanaForm = new FormMain();
        ventanaForm.conn = conn;
        ventanaForm.setContentPane(ventanaForm.vntMain);
        ventanaForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cambiar fondo(verde menta)
        ventanaForm.getContentPane().setBackground(new Color(123, 214, 144));

        // Abrir maximizada (ocupa toda la pantalla pero con bordes)
        ventanaForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //ventanaForm.setSize(1366, 768);
        ventanaForm.setVisible(true);
    }

}
