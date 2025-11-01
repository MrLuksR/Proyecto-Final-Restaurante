package UI;

import InterfacesEimpl.ConexionBD;
import InterfacesEimpl.FinalDAO;
import Modelo.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FormPrincipal extends JFrame {
    private JPanel vntPrincipal;
    private JTabbedPane tbdSecciones;
    private JPanel vntReservas;
    private JPanel vntMesas;
    private JTable tblReservas;
    private JScrollPane scrTablaRes;
    private JComboBox cmbOrder;
    private JButton btnEliminar;
    private JLabel lblOrder;
    private JLabel lblCedula;
    private JTextField txtCedula;
    private JLabel lblApellido;
    private JTextField txtApellido;
    private JLabel lblFecha;
    private JSpinner spnFechaDia;
    private JSpinner spnFechaMes;
    private JSpinner spnFechaAnio;
    LocalDate hoy = LocalDate.now();
    LocalTime nowHora = LocalTime.now();
    private JLabel lblInfoCedula;
    private JLabel lblInfoApellido;
    private JLabel lblHora;
    private JSpinner spnHora;
    private JSpinner spnMinutos;
    private JLabel lblMesa;
    private JComboBox cmbMesa;
    private JLabel lblPersonas;
    private JLabel lblCantidad;
    private JButton btnAgregar;
    private JLabel lblTitulo;
    private JLabel lblInfoFecha;
    private JLabel lblInfoHora;
    private JButton btnMesa1;
    private JButton btnMesa2;
    private JButton btnMesa3;
    private JButton btnMesa4;
    private JButton btnMesa5;
    private JButton btnMesa6;
    private JButton btnMesa7;
    private JButton btnMesa8;
    private JButton btnMesa9;
    private JLabel lblNumMesa;
    private JLabel lblNumeroMesaInfo;
    private JLabel lblSubTitulo;
    private JLabel lblEstadoMesa;
    private JLabel lblEstadoInfo;
    private JLabel lblMesero;
    private JLabel lblPersonalInfo;
    private JLabel lblPedidosTitulo;
    private JButton btnCambiarEstado;
    private JComboBox cmbEstadosMesas;
    private JScrollPane scrProductosMesas;
    private JTable tblPedidosMesas;
    private JLabel lblAgregarProdMesas;
    private JRadioButton rbtnComida;
    private JRadioButton rbtnBebida;
    private JRadioButton rbtnPostre;
    private JComboBox cmbProductosMesas;
    private JButton btnAgregarProdMesa;
    private JButton btnCerrarPedidoMesa;
    private JButton btnCambiarPersonal;
    private JComboBox cmbPersonal;
    private JButton btnElimProdMesas;
    private JSpinner spnCantidadProdMesas;
    private JComboBox cmbEstadoProdMesas;
    private JButton btnCambEstadoProdMesas;
    private JLabel lblTotalProdMesas;
    private JLabel lblTotalPedidMesasInfo;
    private JPanel vtnCarta;
    private JPanel vtnHistorial;
    private DefaultComboBoxModel mdlComboBoxPersonalMesas; // Modelo de comboBox para seleccionar personal
    private DefaultTableModel mdlTblPedidosMesas; // Modelo de tabla para pedidos
    private String[] columnasMesasPedidos = {"ID", "Mesa", "Estado", "Producto", "Cantidad", "Totales"}; // Columnas para la tabla de pedidos
    private DefaultComboBoxModel mdlComboBoxProductosMesas;
    public Connection conn;
    protected String personal;
    boolean cedula, apellido, fecha, hora;

    public FormPrincipal(Connection conn, String personal) {
        this.conn = conn;
        this.personal = personal;
        validarBtnAgregar();
        setContentPane(vntPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        spnCantidadProdMesas.setPreferredSize(new Dimension(50, 25));
        cmbPersonal.setPreferredSize(new Dimension(100, 25));
        spnCantidadProdMesas.setValue(1);

        DefaultTableModel mdl = new DefaultTableModel();

        mdl.setDataVector(datosPedidosMesas(3), columnasMesasPedidos);

        //setDesingPrincipal();

        // Personalizar el tamaño de las pestañas de JTabbedPane
        for (int i = 0; i < tbdSecciones.getTabCount(); i++) {
            JLabel tabLabel = new JLabel(tbdSecciones.getTitleAt(i));
            tabLabel.setPreferredSize(new Dimension(100, 30));
            tabLabel.setFont(new Font(tabLabel.getFont().getName(), Font.BOLD, 18));
            tabLabel.setForeground(Color.DARK_GRAY);
            tbdSecciones.setTabComponentAt(i, tabLabel);
        }

        // Cambiar fondo(verde menta)
        getContentPane().setBackground(new Color(123, 214, 144));

        // Abrir maximizada (ocupa toda la pantalla pero con bordes)
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //ventanaForm.setSize(1366, 768);
        setVisible(true);

        // RESERVAS
        //=================================================================================

        // Establecer los spinners con la fecha actual
        spnFechaDia.setValue(hoy.getDayOfMonth());
        spnFechaMes.setValue(hoy.getMonthValue());
        spnFechaAnio.setValue(hoy.getYear());

        // Establecer los spinners a la hora actual
        spnHora.setValue(nowHora.getHour());
        spnMinutos.setValue(nowHora.getMinute());

        // Listeners de las Fechas
        spnFechaDia.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                validarFecha();
                validarBtnAgregar();
            }
        });
        spnFechaMes.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                validarFecha();
                validarBtnAgregar();
            }
        });
        spnFechaAnio.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                validarFecha();
                validarBtnAgregar();
            }
        });

        // Listeners de la Hora
        spnHora.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                validarHora();
                validarBtnAgregar();
            }
        });
        spnMinutos.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                validarHora();
                validarBtnAgregar();
            }
        });

        // Tabla en Reservas
        String[] columnas = {"ID", "CI del Cliente", "Cliente", "Fecha", "Hora", "Mesa", "Personas"};
        DefaultTableModel mdlTblReservas =  new DefaultTableModel(datosReserva(0), columnas);
        tblReservas.setModel(mdlTblReservas);
        tblReservas.setDefaultEditor(Object.class, null); // No permite que la tabla sea editable
        tblReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite seleccionar solamente un elemento
        tblReservas.getTableHeader().setReorderingAllowed(false);

        // Activa el button eliminar solo si se selecciona una opción de la tabla
        // getSelectedRow() devuelve el índice de la fila seleccionada
        // Si no hay ninguna fila seleccionada devuelve -1, de lo contrario se habilita el botón eliminar
        tblReservas.getSelectionModel().addListSelectionListener(e -> {
            boolean filaSeleccionada = tblReservas.getSelectedRow() != -1;
            btnEliminar.setEnabled(filaSeleccionada);
        });

        // ComboBox en Reservas
        String[] items = {"ID", "Fecha", "Hora", "Cliente", "Mesa"};
        cmbOrder.setModel(new DefaultComboBoxModel(items));

        // ComboBox en Agregar Reservas
        String[] mesas = {"1","2","3","4","5","6","7","8","9"};
        DefaultComboBoxModel<String> cmbModel =  new DefaultComboBoxModel(mesas);
        cmbMesa.setModel(cmbModel);

        // Establecer la cantidad de personas de la mesa
        int mesa = Integer.parseInt((String) cmbModel.getSelectedItem());
        lblCantidad.setText(String.valueOf(getCantPers(mesa)));

        // Listener de Botón Eliminar (Tabla Reservas)
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opc = JOptionPane.showOptionDialog(null, "¿Eliminar esta reserva?", "Confirmar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                int valor = Integer.parseInt(tblReservas.getValueAt(tblReservas.getSelectedRow(), 0).toString());
                int mesa = Integer.parseInt(tblReservas.getValueAt(tblReservas.getSelectedRow(), 5).toString());
                try{
                    if (opc == JOptionPane.YES_OPTION && eliminarFila("reservas", "id", valor)) {
                        String sqlNuevoEstMesa = "UPDATE mesas SET estado = 'Libre' WHERE numMesa = ?";
                        PreparedStatement pst = conn.prepareStatement(sqlNuevoEstMesa);
                        pst.setInt(1, mesa);
                        pst.execute();

                        String sqlAutoIncr = "ALTER TABLE reservas AUTO_INCREMENT = ?";
                        pst = conn.prepareStatement(sqlAutoIncr);
                        pst.setInt(1, valor);
                        pst.execute();

                        pst.close();

                        mdlTblReservas.removeRow(tblReservas.getSelectedRow());
                        JOptionPane.showMessageDialog(null, "Reserva Eliminada");
                    }
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al eliminar esta reserva\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener del ComboBox Order
        cmbOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tblReservas == null) return;
                int cbmIndex = cmbOrder.getSelectedIndex();
                mdlTblReservas.setDataVector(datosReserva(cbmIndex), columnas);
                tblReservas.setModel(mdlTblReservas);
            }
        });

        // Listener de Cedula
        txtCedula.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if (txtCedula.getText().isEmpty()){
                    txtCedula.setBackground(Color.yellow);
                    lblInfoCedula.setText("Este campo no puede estar vacio");
                    cedula = false;
                }
                else if (!validarCedula(txtCedula.getText())){
                    txtCedula.setBackground(Color.red);
                    lblInfoCedula.setText("Cédula inválida");
                    cedula = false;
                }
                else{
                    txtCedula.setBackground(Color.green);
                    lblInfoCedula.setText("");
                    cedula = true;
                }

                validarBtnAgregar();
            }
        });

        // Listener de Apellido
        txtApellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if (txtApellido.getText().isEmpty()){
                    txtApellido.setBackground(Color.yellow);
                    lblInfoApellido.setText("Este campo no puede estar vacío");
                    apellido = false;
                }
                else if (!txtApellido.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$")){
                    txtApellido.setBackground(Color.red);
                    lblInfoApellido.setText("Apellido no puede contener números ni caracteres especiales");
                    apellido = false;
                }
                else{
                    txtApellido.setBackground(Color.green);
                    lblInfoApellido.setText("");
                    apellido = true;
                }

                validarBtnAgregar();
            }
        });

        // Listener del botón Agregar
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    // Establece la fecha
                    int dia = Integer.parseInt(spnFechaDia.getValue().toString());
                    int mes = Integer.parseInt(spnFechaMes.getValue().toString());
                    int anio = Integer.parseInt(spnFechaAnio.getValue().toString());

                    LocalDate fecha = LocalDate.of(anio, mes, dia);

                    // Establece la hora
                    String hora = spnHora.getValue().toString();
                    String minutos = spnMinutos.getValue().toString();

                    String finalHora = hora + ":" + minutos;

                    // Toma los valores del comboBox y del Label de cantidad para agregarlos a la base
                    int mesa = Integer.parseInt(cmbMesa.getSelectedItem().toString());
                    int cant = Integer.parseInt(lblCantidad.getText());

                    // Los añade a la base
                    Reserva res1 = new Reserva(txtCedula.getText(), txtApellido.getText(), fecha, finalHora, mesa, cant);
                    FinalDAO finalDAO = new FinalDAO(conn);
                    finalDAO.guardarReserva(res1);
                    int cbmIndex = cmbOrder.getSelectedIndex();
                    mdlTblReservas.setDataVector(datosReserva(cbmIndex), columnas);
                    tblReservas.setModel(mdlTblReservas);
                    lblNumeroMesaInfo.setText("nada");
                    lblEstadoInfo.setText("nada");
                    lblPersonalInfo.setText("nada");
                    setEnabledAtMesaSelec(false);
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error en base de datos:\n" + ex.getMessage());
                }
            }
        });

        // MESAS
        //===============================================================================

        // ComboBox Estados
        String[] estados = {"Libre", "Ocupada", "Limpieza"};
        cmbEstadosMesas.setModel(new DefaultComboBoxModel(estados));

        // ComboBox Personal a seleccionar
        mdlComboBoxPersonalMesas = new DefaultComboBoxModel();
        getMeseros();
        btnCambiarPersonal.setEnabled(false);

        // LISTENERS
        listenersMesas(); // Contiene los lísteners de los botones de mesas, lo hago así para no dejar tan largo el constructor

        // Listener de botón de cambiar estado
        btnCambiarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pregunta si realmente se quiere cambiar el estado de la mesa
                int opc = JOptionPane.showOptionDialog(null, "¿Cambiar estado de la mesa a " + cmbEstadosMesas.getSelectedItem().toString() + "?", "Confirmar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                String estadoSelec = lblEstadoInfo.getText().toString();
                String estadoChg = cmbEstadosMesas.getSelectedItem().toString();

                // Si la respuesta es SÍ y el estado a cambiar no es el mismo, entonces lo cambiamos
                int numMesa = Integer.parseInt(lblNumeroMesaInfo.getText());
                if (opc == JOptionPane.YES_OPTION && !estadoSelec.equals(estadoChg) && datosPedidosMesas(numMesa) == null) { // No permite cambiar el estado si la mesa está ocupada y con pedidos
                    String sqlChgEstado = "UPDATE mesas SET estado = ? WHERE numMesa = ?";
                    String estado = cmbEstadosMesas.getSelectedItem().toString();

                    if (estadoSelec.equals("Reservada")) {
                        opc = JOptionPane.showOptionDialog(null, "Reserva existente en mesa " + lblNumeroMesaInfo.getText() + "\n¿Desea eliminarla?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                        if (opc == JOptionPane.YES_OPTION && eliminarFila("reservas", "mesa", numMesa)) {
                            mdlTblReservas.setDataVector(datosReserva(cmbOrder.getSelectedIndex()), columnas);
                            tblReservas.setModel(mdlTblReservas);
                            JOptionPane.showMessageDialog(null, "Reserva para mesa " + numMesa + " eliminada");
                            try{
                                PreparedStatement pst = conn.prepareStatement(sqlChgEstado);
                                pst.setString(1, estado);
                                pst.setInt(2, numMesa);
                                pst.executeUpdate();
                                pst.close();
                                lblEstadoInfo.setText(estado);
                            }catch(SQLException ex){
                                JOptionPane.showMessageDialog(null, "Error en base de datos:\n" + ex.getMessage());
                            }
                        }
                    }else{
                        try{
                            PreparedStatement pst = conn.prepareStatement(sqlChgEstado);
                            pst.setString(1, estado);
                            pst.setInt(2, numMesa);
                            pst.executeUpdate();
                            pst.close();
                            lblEstadoInfo.setText(estado);
                        }catch(SQLException ex){
                            JOptionPane.showMessageDialog(null, "Error en base de datos:\n" + ex.getMessage());
                        }
                    }
                    // Si el estado a cambiar es igual al ya seleccionado entonces mostrar el mensaje
                }else if (estadoSelec.equals(estadoChg)){
                    JOptionPane.showMessageDialog(null, "La mesa ya está " + estadoChg, "Información", JOptionPane.INFORMATION_MESSAGE);
                }else if (datosPedidosMesas(numMesa) != null){
                    JOptionPane.showMessageDialog(null, "No se ha cerrado el pedido de mesa " + lblNumeroMesaInfo.getText(), "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Listener de botón de cambiar personal
        btnCambiarPersonal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pregunta si realmente se quiere cambiar el mesero asignado a esta mesa
                int opc = JOptionPane.showOptionDialog(null, "¿Cambiar mesero a " + cmbPersonal.getSelectedItem().toString() + "?", "Confirmar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                String personalSelec = lblPersonalInfo.getText();
                String personalChg = cmbPersonal.getSelectedItem().toString();

                // Si la respuesta es SÍ y el mesero a cambiar no es el mismo, entonces lo cambiamos
                if (opc == JOptionPane.YES_OPTION && !personalSelec.equals(personalChg)) {
                    String personal = cmbPersonal.getSelectedItem().toString();
                    String sqlChgPersonal = "UPDATE mesas SET mesero = (SELECT ci_personal FROM personal WHERE apellido = ?) WHERE numMesa = ?";
                    int numMesa = Integer.parseInt(lblNumeroMesaInfo.getText());

                    try{
                        PreparedStatement pst = conn.prepareStatement(sqlChgPersonal);
                        pst.setString(1, personal);
                        pst.setInt(2, numMesa);
                        pst.executeUpdate();
                        pst.close();
                        lblPersonalInfo.setText(personal);
                        JOptionPane.showMessageDialog(null,"Personal de mesa " + numMesa + " cambiado a mesero " + personal);
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(null, "Error en base de datos:\n" + ex.getMessage());
                    }
                // Si el mesero a cambiar es igual al ya seleccionado entonces mostrar el mensaje
                }else if (personalSelec.equals(personalChg)) {
                    JOptionPane.showMessageDialog(null, "El mesero " + personalChg + " ya está designado a esta mesa", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Tabla de pedidos
        mdlTblPedidosMesas = new DefaultTableModel(columnasMesasPedidos, 0);
        tblPedidosMesas.setModel(mdlTblPedidosMesas);
        tblPedidosMesas.setDefaultEditor(Object.class, null); // No permite que la tabla sea editable
        tblPedidosMesas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite seleccionar solamente un elemento
        tblPedidosMesas.getTableHeader().setReorderingAllowed(false);

        // Listener de Tabla pedidos para botón Eliminar y Cambiar Estado de Pedido
        tblPedidosMesas.getSelectionModel().addListSelectionListener(e -> {
            boolean filaSeleccionada = tblPedidosMesas.getSelectedRow() != -1;
            btnElimProdMesas.setEnabled(filaSeleccionada);
            btnCambEstadoProdMesas.setEnabled(filaSeleccionada);
            cmbEstadoProdMesas.setEnabled(filaSeleccionada);
        });

        // Listener de Botón eliminar (Tabla Productos)
        btnElimProdMesas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombrePedido = tblPedidosMesas.getValueAt(tblPedidosMesas.getSelectedRow(), 3).toString();
                int opc = JOptionPane.showOptionDialog(null, "¿Eliminar pedido " + nombrePedido + "?", "Confirmar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                String estado = tblPedidosMesas.getValueAt(tblPedidosMesas.getSelectedRow(), 2).toString();
                if (opc == JOptionPane.YES_OPTION && !(estado.equals("Servido") || estado.equals("Listo"))) {
                    int id = Integer.parseInt(tblPedidosMesas.getValueAt(tblPedidosMesas.getSelectedRow(), 0).toString());
                    int numMesa = Integer.parseInt(lblNumeroMesaInfo.getText());
                    eliminarFila("pedidos","id_pedido", id);
                    String sqlAutoIncr = "ALTER TABLE pedidos AUTO_INCREMENT = ?";
                    try{
                        PreparedStatement pst = conn.prepareStatement(sqlAutoIncr);
                        pst.setInt(1, id);
                        pst.executeUpdate();
                        pst.close();
                        mdlTblPedidosMesas.setDataVector(datosPedidosMesas(numMesa), columnasMesasPedidos);
                        tblPedidosMesas.setModel(mdlTblPedidosMesas);
                        lblTotalPedidMesasInfo.setText("$" + getTotales());
                        JOptionPane.showMessageDialog(null,"Producto Eliminado");
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Error en base de datos:\n" + ex.getMessage());
                    }
                }else if (estado.equals("Servido") || estado.equals("Listo")) {
                    JOptionPane.showMessageDialog(null,"No se puede eliminar el producto:\nproducto (" + estado +")", "Información", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // ComboBox de productos
        mdlComboBoxProductosMesas = new DefaultComboBoxModel();
        getProductos("'Comida'");

        // Listeners de los RADIO BUTTONS
        // Radio Button COMIDA
        rbtnComida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getProductos("'Comida'");
            }
        });
        // Radio Button BEBIDA
        rbtnBebida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getProductos("'Bebida'");
            }
        });
        // Radio Button POSTRE
        rbtnPostre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getProductos("'Postre'");
            }
        });

        // Listener de Spinner cantidad
        spnCantidadProdMesas.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                CantidadNegativaException ex = new CantidadNegativaException("La cantidad no puede ser negativa ni cero");
                int valor = (int) spnCantidadProdMesas.getValue();
                if (valor < 1) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",  JOptionPane.ERROR_MESSAGE);
                    throw ex;
                }
            }
        });

        // ComboBox para cambiar estado de Pedidos
        String[] estadosPedidos = {"Pendiente", "En preparación", "Listo", "Servido"};
        cmbEstadoProdMesas.setModel(new DefaultComboBoxModel(estadosPedidos));

        // Listener de botón Agregar Producto
        btnAgregarProdMesa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cant = (int) spnCantidadProdMesas.getValue();
                addProducto(cant);
            }
        });

        // Listener de botón Cambiar Estado de Pedido
        btnCambEstadoProdMesas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String estado = cmbEstadoProdMesas.getSelectedItem().toString();
                int id_pedido = Integer.parseInt(tblPedidosMesas.getValueAt(tblPedidosMesas.getSelectedRow(), 0).toString());
                int numMesa = Integer.parseInt(lblNumeroMesaInfo.getText());
                String sqlEstado = "UPDATE pedidos SET estado = ? WHERE id_pedido = ?";
                try{
                    PreparedStatement pst = conn.prepareStatement(sqlEstado);
                    pst.setString(1, estado);
                    pst.setInt(2, id_pedido);
                    pst.executeUpdate();
                    pst.close();
                    mdlTblPedidosMesas.setDataVector(datosPedidosMesas(numMesa), columnasMesasPedidos);
                    tblPedidosMesas.setModel(mdlTblPedidosMesas);
                    JOptionPane.showMessageDialog(null,"Estado cambiado a " + estado);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Error en base de datos:\n" + ex.getMessage());
                }
            }
        });

        // Listener de botón Cerrar Pedido
        btnCerrarPedidoMesa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numMesa = Integer.parseInt(lblNumeroMesaInfo.getText());
                int opc = JOptionPane.showOptionDialog(null, "¿Cerrar pedido de la mesa" + numMesa + "?", "Confirmar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (opc == JOptionPane.YES_OPTION && datosPedidosMesas(numMesa) != null && cerrarPedido()) {
                    DialogConfirmFactura dialog = new DialogConfirmFactura(conn, numMesa, personal, columnasMesasPedidos, tblPedidosMesas, recuentoDePedidos(), mdlTblPedidosMesas);
                    dialog.setBounds(200, 200, 450, 300);
                    dialog.setVisible(true);
                }else if (datosPedidosMesas(numMesa) == null)
                    JOptionPane.showMessageDialog(null, "Lista de pedidos vacía", "Información", JOptionPane.INFORMATION_MESSAGE);
                else if (!cerrarPedido())
                    JOptionPane.showMessageDialog(null, "Hay pedidos pendientes", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Obtener los datos de la reserva en la base de datos
    public void setDesingPrincipal() {
        lblInfoHora.setForeground(Color.WHITE);
        lblInfoHora.setFont(new Font("Tahoma", Font.BOLD, 18));

        lblHora.setForeground(Color.WHITE);
        lblHora.setFont(new Font("Times New Roman", Font.BOLD, 18));

        cmbEstadosMesas.setBackground(Color.DARK_GRAY);
        cmbEstadosMesas.setForeground(Color.WHITE);


        rbtnBebida.setForeground(Color.WHITE);
        rbtnBebida.setBorderPainted(false);
        rbtnBebida.setContentAreaFilled(false);
        rbtnBebida.setFocusPainted(false);

        tbdSecciones.setBackground(Color.DARK_GRAY);
        tbdSecciones.setForeground(Color.WHITE);
        tbdSecciones.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                tbdSecciones.setForegroundAt(tbdSecciones.getSelectedIndex(), Color.DARK_GRAY);
            }
        });

        scrTablaRes.getViewport().setBackground(Color.DARK_GRAY);

        tblReservas.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tblReservas.setBackground(Color.DARK_GRAY);
        tblReservas.setForeground(Color.WHITE);
        tblReservas.setFont(new Font("Times New Roman", Font.BOLD, 10));
        tblReservas.setGridColor(new Color(68, 74, 70));


        ImageIcon imgMesa1 = new ImageIcon(new ImageIcon("C:\\Users\\range\\OneDrive\\Desktop\\Proyecto Final - Pensamiento Computacional\\Nivel 1\\environment_forest_evening.png").getImage().getScaledInstance(140,70, Image.SCALE_DEFAULT));
        btnMesa1.setText("");
        btnMesa1.setIcon(imgMesa1);
        btnMesa1.setBorderPainted(false);
        btnMesa1.setContentAreaFilled(false);
        btnMesa1.setFocusPainted(false);

        btnMesa2.setText("");
        btnMesa2.setIcon(imgMesa1);
        btnMesa2.setBorderPainted(false);
        btnMesa2.setContentAreaFilled(false);
        btnMesa2.setFocusPainted(false);

        btnMesa3.setText("");
        btnMesa3.setIcon(imgMesa1);
        btnMesa3.setBorderPainted(false);
        btnMesa3.setContentAreaFilled(false);
        btnMesa3.setFocusPainted(false);

        btnMesa4.setText("");
        btnMesa4.setIcon(imgMesa1);
        btnMesa4.setBorderPainted(false);
        btnMesa4.setContentAreaFilled(false);
        btnMesa4.setFocusPainted(false);

        btnMesa5.setText("");
        btnMesa5.setIcon(imgMesa1);
        btnMesa5.setBorderPainted(false);
        btnMesa5.setContentAreaFilled(false);
        btnMesa5.setFocusPainted(false);

        btnMesa6.setText("");
        btnMesa6.setIcon(imgMesa1);
        btnMesa6.setBorderPainted(false);
        btnMesa6.setContentAreaFilled(false);
        btnMesa6.setFocusPainted(false);

        btnMesa7.setText("");
        btnMesa7.setIcon(imgMesa1);
        btnMesa7.setBorderPainted(false);
        btnMesa7.setContentAreaFilled(false);
        btnMesa7.setFocusPainted(false);

        btnMesa8.setText("");
        btnMesa8.setIcon(imgMesa1);
        btnMesa8.setBorderPainted(false);
        btnMesa8.setContentAreaFilled(false);
        btnMesa8.setFocusPainted(false);

        btnMesa9.setText("");
        btnMesa9.setIcon(imgMesa1);
        btnMesa9.setBorderPainted(false);
        btnMesa9.setContentAreaFilled(false);
        btnMesa9.setFocusPainted(false);

    }


    // RESERVA
    //===============================================================
    public String[][] datosReserva(int order){
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
            String ordenar = "id";

            switch(order) {
                case 0:
                    ordenar = "id";
                    break;
                case 1:
                    ordenar = "fecha";
                    break;
                case 2:
                    ordenar = "hora";
                    break;
                case 3:
                    ordenar = "cliente";
                    break;
                case 4:
                    ordenar = "mesa";
                    break;}

            // Creamos lo necesario para obtener la fila con datos de reserva
            String sqlReserva= "SELECT id, ciCliente, cliente, fecha, hora, mesa, personas FROM reservas ORDER BY " + ordenar; // Para obtener una fila de reservas

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
    } // Trae los datos de la reserva a una Matriz para aplicar a la tabla
    public boolean validarCedula(String cedula){ // Verificar cedula uruguaya
        if (cedula.length() == 8) {
            int dgVer = Integer.parseInt(cedula.substring(7, 8));
            int priNum = (Integer.parseInt(cedula.substring(0, 1)) * 2);
            int segNum = (Integer.parseInt(cedula.substring(1, 2)) * 9);
            int terNum = (Integer.parseInt(cedula.substring(2, 3)) * 8);
            int cuaNum = (Integer.parseInt(cedula.substring(3, 4)) * 7);
            int quiNum = (Integer.parseInt(cedula.substring(4, 5)) * 6);
            int sexNum = (Integer.parseInt(cedula.substring(5, 6)) * 3);
            int sepNum = (Integer.parseInt(cedula.substring(6, 7)) * 4);

            int sum = (priNum + segNum + terNum + cuaNum + quiNum + sexNum + sepNum);
            int mod = sum % 10;
            int comp = 10 - mod;

            if (comp == 10 && dgVer == 0)
                return true;

            if (comp == dgVer)
                return true;
            else
                return false;
        }
        else
            return false;
    } // Validador de la cédula
    public int getCantPers(int mesa){
        try{
            String sqlCant = "SELECT capacidad FROM mesas WHERE numMesa = ?";
            PreparedStatement ps = conn.prepareStatement(sqlCant);
            ps.setInt(1, mesa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getInt("capacidad");
            }

        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error en Base de Datos:\n" + ex.getMessage());
        }
        return 0;
    } // Obtiene la cantidad de personas que se puede tener por mesa
    public boolean eliminarFila(String tabla, String columna, int valor){
        try{
            String eliminar = "DELETE FROM " +  tabla + " WHERE " + columna + " = ?";
            String sqlTotalReservas = eliminar; // Elimina la fila en la base de datos
            PreparedStatement pst = conn.prepareStatement(sqlTotalReservas);
            pst.setInt(1, valor);
            pst.executeUpdate();
            pst.close();
            return true;
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } // Se utiliza para eliminar fila solamente
    void validarBtnAgregar(){
        if (apellido && cedula && fecha && hora)
            btnAgregar.setEnabled(true);
        else
            btnAgregar.setEnabled(false);
    } // Activa el botón agregar
    public void validarFecha(){
        int diaSelect = Integer.parseInt(spnFechaDia.getValue().toString());
        int mesSelect = Integer.parseInt(spnFechaMes.getValue().toString());
        int anioSelect = Integer.parseInt(spnFechaAnio.getValue().toString());
        int horaSelect = Integer.parseInt(spnHora.getValue().toString());
        int minSelect = Integer.parseInt(spnMinutos.getValue().toString());

        try{
            LocalDate fechaSelect = LocalDate.of(anioSelect, mesSelect, diaSelect);
            LocalTime horaSelectTime =  LocalTime.of(horaSelect, minSelect);
            if (fechaSelect.isBefore(hoy)){
                lblInfoFecha.setText("Fecha inválida");
                fecha = false;
            }
            else{
                lblInfoFecha.setText("");
                fecha = true;

                // Validar hora según la fecha
                if (fechaSelect.equals(hoy)) {
                    // si es hoy, la hora debe ser posterior a la actual
                    if (horaSelectTime.isAfter(nowHora)) {
                        lblInfoHora.setText(""); // válida
                        hora = true;
                    } else {
                        lblInfoHora.setText("Hora inválida");
                        hora = false;
                    }
                }
                else {
                    // si es un día futuro, cualquier hora es válida
                    lblInfoHora.setText("");
                    hora = true;
                }
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en fecha:\n" + e.getMessage());
        }
    } // Controla si la fecha para la reserva es válida
    public void validarHora(){
        int horaSelect = Integer.parseInt(spnHora.getValue().toString());
        int minSelect = Integer.parseInt(spnMinutos.getValue().toString());
        int diaSelect = Integer.parseInt(spnFechaDia.getValue().toString());
        int mesSelect = Integer.parseInt(spnFechaMes.getValue().toString());
        int anioSelect = Integer.parseInt(spnFechaAnio.getValue().toString());

        try{
            // Validación de formato 24h
            LocalDate fechaSelect = LocalDate.of(anioSelect, mesSelect, diaSelect);
            LocalTime horaSelectTime =  LocalTime.of(horaSelect, minSelect);
            if (horaSelect < 0 || horaSelect > 23 || minSelect < 0 || minSelect > 59) {
                lblInfoHora.setText("Error");
                JOptionPane.showMessageDialog(null, "La hora seleccionada no es válida. (Formato 24 horas)", "Error", JOptionPane.ERROR_MESSAGE);
                spnHora.setValue(nowHora.getHour());
                spnMinutos.setValue(nowHora.getMinute());
                hora = false;
                lblInfoHora.setText("");
            }
            else if (fechaSelect.equals(hoy) && !horaSelectTime.isAfter(nowHora)){
                lblInfoHora.setText("Hora inválida");
                hora = false;
            }
            else {
                lblInfoHora.setText(""); // si es válida, no muestra error
                hora = true;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en hora:\n" + e.getMessage());
        }
    } // Controla si la hora para reservar es válida
    //===============================================================

    // MESAS
    //===============================================================
    public String consultarEstadoYpersonal(int numMesa, int estado_personal){
        String sqlConsulta = "SELECT mesas.estado, personal.apellido FROM mesas\n" +
                "INNER JOIN personal ON personal.ci_personal = mesas.mesero AND numMesa = ?";
        try{
            PreparedStatement pst = conn.prepareStatement(sqlConsulta);
            pst.setInt(1, numMesa);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String[] estado = new String[2];
                estado[0] = rs.getString("estado");
                estado[1] = rs.getString("apellido");
                return estado[estado_personal];
            }
            pst.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en la consulta:\n" + e.getMessage());
        }
        return "nada";
    } // Obtiene estado y personal de una mesa específica
    public void listenersMesas(){
        // Mesa 1
        btnMesa1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledAtMesaSelec(true);
                lblNumeroMesaInfo.setText("1");
                String estado = consultarEstadoYpersonal(1, 0);
                String personal = consultarEstadoYpersonal(1, 1);
                cmbEstadosMesas.setSelectedItem(estado);
                lblEstadoInfo.setText(estado);
                lblPersonalInfo.setText(personal);
                getMeseros();
                mdlTblPedidosMesas.setDataVector(datosPedidosMesas(1), columnasMesasPedidos);
                tblPedidosMesas.setModel(mdlTblPedidosMesas);
                lblTotalPedidMesasInfo.setText("$" + getTotales());
            }
        });

        // Mesa 2
        btnMesa2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledAtMesaSelec(true);
                lblNumeroMesaInfo.setText("2");
                String estado = consultarEstadoYpersonal(2, 0);
                String personal = consultarEstadoYpersonal(2, 1);
                cmbEstadosMesas.setSelectedItem(estado);
                lblEstadoInfo.setText(estado);
                lblPersonalInfo.setText(personal);
                getMeseros();
                mdlTblPedidosMesas.setDataVector(datosPedidosMesas(2), columnasMesasPedidos);
                tblPedidosMesas.setModel(mdlTblPedidosMesas);
                lblTotalPedidMesasInfo.setText("$" + getTotales());
            }
        });

        // Mesa 3
        btnMesa3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledAtMesaSelec(true);
                lblNumeroMesaInfo.setText("3");
                String estado = consultarEstadoYpersonal(3, 0);
                String personal = consultarEstadoYpersonal(3, 1);
                cmbEstadosMesas.setSelectedItem(estado);
                lblEstadoInfo.setText(estado);
                lblPersonalInfo.setText(personal);
                getMeseros();
                mdlTblPedidosMesas.setDataVector(datosPedidosMesas(3), columnasMesasPedidos);
                tblPedidosMesas.setModel(mdlTblPedidosMesas);
                lblTotalPedidMesasInfo.setText("$" + getTotales());
            }
        });

        // Mesa 4
        btnMesa4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledAtMesaSelec(true);
                lblNumeroMesaInfo.setText("4");
                String estado = consultarEstadoYpersonal(4, 0);
                String personal = consultarEstadoYpersonal(4, 1);
                cmbEstadosMesas.setSelectedItem(estado);
                lblEstadoInfo.setText(estado);
                lblPersonalInfo.setText(personal);
                getMeseros();
                mdlTblPedidosMesas.setDataVector(datosPedidosMesas(4), columnasMesasPedidos);
                tblPedidosMesas.setModel(mdlTblPedidosMesas);
                lblTotalPedidMesasInfo.setText("$" + getTotales());
            }
        });

        // Mesa 5
        btnMesa5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledAtMesaSelec(true);
                lblNumeroMesaInfo.setText("5");
                String estado = consultarEstadoYpersonal(5, 0);
                String personal = consultarEstadoYpersonal(5, 1);
                cmbEstadosMesas.setSelectedItem(estado);
                lblEstadoInfo.setText(estado);
                lblPersonalInfo.setText(personal);
                getMeseros();
                mdlTblPedidosMesas.setDataVector(datosPedidosMesas(5), columnasMesasPedidos);
                tblPedidosMesas.setModel(mdlTblPedidosMesas);
                lblTotalPedidMesasInfo.setText("$" + getTotales());
            }
        });

        // Mesa 6
        btnMesa6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledAtMesaSelec(true);
                lblNumeroMesaInfo.setText("6");
                String estado = consultarEstadoYpersonal(6, 0);
                String personal = consultarEstadoYpersonal(6, 1);
                cmbEstadosMesas.setSelectedItem(estado);
                lblEstadoInfo.setText(estado);
                lblPersonalInfo.setText(personal);
                getMeseros();
                mdlTblPedidosMesas.setDataVector(datosPedidosMesas(6), columnasMesasPedidos);
                tblPedidosMesas.setModel(mdlTblPedidosMesas);
                lblTotalPedidMesasInfo.setText("$" + getTotales());
            }
        });

        // Mesa 7
        btnMesa7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledAtMesaSelec(true);
                lblNumeroMesaInfo.setText("7");
                String estado = consultarEstadoYpersonal(7, 0);
                String personal = consultarEstadoYpersonal(7, 1);
                cmbEstadosMesas.setSelectedItem(estado);
                lblEstadoInfo.setText(estado);
                lblPersonalInfo.setText(personal);
                getMeseros();
                mdlTblPedidosMesas.setDataVector(datosPedidosMesas(7), columnasMesasPedidos);
                tblPedidosMesas.setModel(mdlTblPedidosMesas);
                lblTotalPedidMesasInfo.setText("$" + getTotales());
            }
        });

        // Mesa 8
        btnMesa8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledAtMesaSelec(true);
                lblNumeroMesaInfo.setText("8");
                String estado = consultarEstadoYpersonal(8, 0);
                String personal = consultarEstadoYpersonal(9, 1);
                cmbEstadosMesas.setSelectedItem(estado);
                lblEstadoInfo.setText(estado);
                lblPersonalInfo.setText(personal);
                getMeseros();
                mdlTblPedidosMesas.setDataVector(datosPedidosMesas(8), columnasMesasPedidos);
                tblPedidosMesas.setModel(mdlTblPedidosMesas);
                lblTotalPedidMesasInfo.setText("$" + getTotales());
            }
        });

        // Mesa 9
        btnMesa9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledAtMesaSelec(true);
                lblNumeroMesaInfo.setText("9");
                String estado = consultarEstadoYpersonal(9, 0);
                String personal = consultarEstadoYpersonal(9, 1);
                cmbEstadosMesas.setSelectedItem(estado);
                lblEstadoInfo.setText(estado);
                lblPersonalInfo.setText(personal);
                getMeseros();
                mdlTblPedidosMesas.setDataVector(datosPedidosMesas(9), columnasMesasPedidos);
                tblPedidosMesas.setModel(mdlTblPedidosMesas);
                lblTotalPedidMesasInfo.setText("$" + getTotales());
            }
        });
    } // Los lísteners de los botones de las mesas (para limpiar un poco el código)
    public void getMeseros(){
        // Hacemos la consulta con normalidad
        String sqlCons = "SELECT apellido FROM personal WHERE rol = 'Mesero'";
        btnCambiarPersonal.setEnabled(true);
        // establecemos el JComboBox al personal ya designado para no desorientar al usuario
        cmbPersonal.setSelectedItem(lblPersonalInfo.getText());

        try{
            // Consultamos con un Statment a fin de establecer la manera de recorrer el ResultSet
            // Permite recorrerlo como un array sin índice, cada vez que se llamen sus valores con getString o getInt
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sqlCons);
            mdlComboBoxPersonalMesas.removeAllElements();

            // Mientras el ResultSet encuentre valores poner cada valor al modelo del JComboBox
            while (rs.next()){
                mdlComboBoxPersonalMesas.addElement(rs.getString("apellido"));
            }
            // Meter el modelo al JComboBox
            cmbPersonal.setModel(mdlComboBoxPersonalMesas);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error en la consulta:\n" + e);
        }
    } // Obtiene los apellidos de los meseros para el JComboBox, para cambiar designado por mesa
    public String[][] datosPedidosMesas(int numMesa){
        try {
            String sqlTotalReservas = "SELECT COUNT(id_pedido) AS total FROM pedidos WHERE mesa = ?";// Cuenta todas los pedidos que hay
            PreparedStatement pst = conn.prepareStatement(sqlTotalReservas);
            pst.setInt(1, numMesa);
            ResultSet totalRes = pst.executeQuery(); // Número total de pedidos
            int total = 0;
            if (totalRes.next()) {
                total = totalRes.getInt("total");
            }

            String[][] matrizPedidosFinal = new String[total][6];

            // Creamos lo necesario para obtener la fila con datos de pedidos
            String sqlPedidos = "SELECT id_pedido, productos.precio, mesa, estado, productos.nombre AS producto, cantidad, totales FROM pedidos\n" +
                    "INNER JOIN productos ON pedidos.producto = productos.idProducto WHERE mesa = " + numMesa; // Para obtener una fila de pedidos

            /* Creamos una variable del tipo PrepareStatement y le pasamos la consulta SQl
             * Luego creamos un ResultSet para comprobar el resultado obtenido de esa consulta*/

            int i = 0;
            pst = conn.prepareStatement(sqlPedidos, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rsPedidos = pst.executeQuery();
            while (rsPedidos.next()) {
                matrizPedidosFinal[i][0] = String.valueOf(rsPedidos.getInt("id_pedido"));
                matrizPedidosFinal[i][1] = String.valueOf(rsPedidos.getInt("mesa"));
                matrizPedidosFinal[i][2] = rsPedidos.getString("estado");
                matrizPedidosFinal[i][3] = rsPedidos.getString("producto");
                matrizPedidosFinal[i][4] = String.valueOf(rsPedidos.getInt("cantidad"));
                matrizPedidosFinal[i][5] = String.valueOf(rsPedidos.getInt("Totales"));
                i++;
            }

            // Cerramos el PreparedStatement y ResultSet
            rsPedidos.close();
            totalRes.close();
            pst.close();
            if (i != 0) // Si la i aumentó en algún momento, significa que ResultSet obtuvo resultados
                return matrizPedidosFinal;
            else // De lo contrario no, entonces devolvemos null
                return null;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    } // Trae los datos de la pedidos de una mesa a una Matriz para aplicar a la tabla
    public String[] getProductos(String categoria){
        // Hacemos la consulta con normalidad
        String sqlCons = "SELECT nombre FROM productos WHERE categoria = " + categoria;

        try{
            // Consultamos con un Statment a fin de establecer la manera de recorrer el ResultSet
            // Permite recorrerlo como un array sin índice, cada vez que se llamen sus valores con getString o getInt
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sqlCons);
            mdlComboBoxProductosMesas.removeAllElements();

            // Mientras el ResultSet encuentre valores poner cada valor al modelo del JComboBox
            String elemento;
            while (rs.next()){
                elemento = rs.getString("nombre");
                mdlComboBoxProductosMesas.addElement(elemento);
            }
            // Meter el modelo al JComboBox
            cmbProductosMesas.setModel(mdlComboBoxProductosMesas);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error en la consulta:\n" + e);
        }
        return new String[0];
    } // Obtiene los nombres de los productos para ComboBox
    public void setEnabledAtMesaSelec(boolean valor){
        cmbEstadosMesas.setEnabled(valor);
        cmbPersonal.setEnabled(valor);
        btnCambiarPersonal.setEnabled(valor);
        btnCambiarEstado.setEnabled(valor);
        btnCerrarPedidoMesa.setEnabled(valor);
        cmbProductosMesas.setEnabled(valor);
        btnAgregarProdMesa.setEnabled(valor);
        spnCantidadProdMesas.setEnabled(valor);
        rbtnComida.setEnabled(valor);
        rbtnPostre.setEnabled(valor);
        rbtnBebida.setEnabled(valor);
        lblTotalPedidMesasInfo.setText("$");
    } // Establece todos los controles de la pestaña de mesa
    public void addProducto(int cant){
        String sqlProd = "SELECT idProducto FROM productos WHERE nombre = ?";
        String sqlAdd = "INSERT INTO pedidos (mesa, estado, producto, cantidad, totales)\n" +
                "SELECT ?, ?, ?, ?, productos.precio * ?\n" +
                "FROM productos\n" +
                "WHERE productos.idProducto = ?;";
        int opc = JOptionPane.showOptionDialog(null, "¿Agregar " + cant + " " + cmbProductosMesas.getSelectedItem().toString() + " a la mesa?", "Confirmar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        int numMesa = Integer.parseInt(lblNumeroMesaInfo.getText());

        if (opc == JOptionPane.YES_OPTION && getEstadoMesa(numMesa,"Ocupada")){
            try{
                PreparedStatement pst = conn.prepareStatement(sqlProd);
                String nombre = cmbProductosMesas.getSelectedItem().toString();
                pst.setString(1, nombre);
                // Restar cantidad de productos
                if (modStockProd(nombre, cant)){
                    ResultSet rs = pst.executeQuery();
                    int mesa = Integer.parseInt(lblNumeroMesaInfo.getText());

                    if (rs.next()) {
                        int id = rs.getInt("idProducto");
                        pst = conn.prepareStatement(sqlAdd);
                        pst.setInt(1, mesa);
                        pst.setString(2, "Pendiente");
                        pst.setInt(3, id);
                        pst.setInt(4, cant);
                        pst.setInt(5, cant);
                        pst.setInt(6, id);
                        pst.execute();

                        // Establecer nuevos datos a la tabla
                        mdlTblPedidosMesas.setDataVector(datosPedidosMesas(mesa), columnasMesasPedidos);
                        tblPedidosMesas.setModel(mdlTblPedidosMesas);
                        lblTotalPedidMesasInfo.setText("$" + getTotales());
                        JOptionPane.showMessageDialog(null, "Producto/s agregados a mesa " + numMesa, "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                    rs.close();
                }
                pst.close();
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Error base de datos:\n" + e);
            }
        }else if (!getEstadoMesa(numMesa,"Ocupada"))
            JOptionPane.showMessageDialog(null, "No se puede agregar producto\nmesa: " + lblEstadoInfo.getText(), "Información", JOptionPane.WARNING_MESSAGE);
    } // Agrega un producto a la tabla
    public boolean modStockProd(String nombre, int cant){
        String sqlGetStock = "SELECT stock FROM productos WHERE nombre = ?";
        try{
            PreparedStatement pst = conn.prepareStatement(sqlGetStock);
            pst.setString(1, nombre);
            pst.execute();
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int stock = rs.getInt("stock");

                if (cant <= stock){
                    String sqlUpdateStock = "UPDATE productos SET stock = ? WHERE nombre = ?";
                    pst = conn.prepareStatement(sqlUpdateStock);
                    int total = stock - cant;
                    pst.setInt(1, total);
                    pst.setString(2, nombre);
                    pst.execute();
                    rs.close();
                    if (total <= 5)
                        JOptionPane.showMessageDialog(null, "Cantidades por debajo de 5", "Información", JOptionPane.WARNING_MESSAGE);
                    return true;
                }else{
                    StockInsuficienteException ex =  new StockInsuficienteException("Stock insuficiente");
                    JOptionPane.showMessageDialog(null, ex.getMessage() + " " + nombre, "Información", JOptionPane.WARNING_MESSAGE);
                    throw ex;
                }
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error base de datos:\n" + e);
        }
        return false;
    } // Modifica la cantidad de productos en la base de datos
    public boolean getEstadoMesa(int numMesa, String estado){
        String sqlCons = "SELECT estado FROM mesas WHERE numMesa = ?";

        try{
            PreparedStatement pst = conn.prepareStatement(sqlCons);
            pst.setInt(1, numMesa);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                if (estado.equals(rs.getString("estado")))
                    return true;
                else
                    return false;
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error base de datos:\n" + e);
        }
        return false;
    } // Controla si el estado pasado por parámetro es igual al de la base de datos
    public double getTotales(){
        int numMesa = Integer.parseInt(lblNumeroMesaInfo.getText());
        if (datosPedidosMesas(numMesa) != null){
            double totales = 0;
            for (int i = 0; i < tblPedidosMesas.getRowCount(); i++) {
                totales += Integer.parseInt(tblPedidosMesas.getValueAt(i, 5).toString());
            }
            return totales;
        }
        return 0;
    } // Suma todas las filas del total de los pedidos
    public Pedido[] recuentoDePedidos() {
        int lenPedidos = tblPedidosMesas.getRowCount();
        int lenAtributos = tblPedidosMesas.getColumnCount();
        Pedido[] totalPedidos = new Pedido[lenPedidos];
        String[] atributos = new String[lenAtributos];

        for (int i = 0; i < lenPedidos; i++) {
            for (int j = 0; j < lenAtributos; j++) {
                atributos[j] = tblPedidosMesas.getValueAt(i, j).toString();
            }
            totalPedidos[i] = new Pedido(
                    Integer.parseInt(atributos[0]),
                    Integer.parseInt(atributos[1]),
                    atributos[2],
                    atributos[3],
                    Integer.parseInt(atributos[4]),
                    Double.parseDouble(atributos[5])
                );
            }
            return totalPedidos;
    } // Retorna un array de Pedidos
    public boolean cerrarPedido(){
        int lenProductos = tblPedidosMesas.getRowCount();
        String valor;

        for (int i = 0; i < lenProductos; i++) {
            valor = tblPedidosMesas.getValueAt(i, 5).toString();
            if (!valor.equals("Servido"))
                return false;
        }
        return true;
    } // Controla que todos los pedidos hayan sido servidos para poder cerrar
    //===============================================================

    // PRODUCTOS / CARTA
    //===============================================================
    //===============================================================
    public static void main(String[] args) throws SQLException {
        Connection conn = ConexionBD.getConnection();
        if (conn == null) return;
        FormPrincipal ventanaForm = new FormPrincipal(conn, "");
        ventanaForm.setContentPane(ventanaForm.vntPrincipal);
        ventanaForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cambiar fondo(verde menta)
        ventanaForm.getContentPane().setBackground(Color.LIGHT_GRAY);

        // Abrir maximizada (ocupa toda la pantalla pero con bordes)
        ventanaForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //ventanaForm.setSize(1280, 720);
        ventanaForm.setVisible(true);
    }

    /*
    private void createUIComponents() {
        // TODO: place custom component creation code here
        String username = System.getenv("USERNAME");
        vntReservas = new PanelConFondo("C:/Users/" + username + "/IdeaProjects/Imagenes/Background.png");
        vntMesas = new PanelConFondo("C:/Users/" + username + "/IdeaProjects/Imagenes/Background.png");
    }
     */
}
