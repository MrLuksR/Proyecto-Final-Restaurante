package UI;

import InterfacesEimpl.ConexionBD;
import InterfacesEimpl.FinalDAO;
import Modelo.Reserva;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

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
    public Connection conn;
    boolean cedula, apellido, fecha, hora;

    public FormPrincipal(Connection conn) {
        this.conn = conn;
        validarBtnAgregar();
        setContentPane(vntPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        String[] mesas = {"1","2","3","4","5","6","7","8","9","10"};
        DefaultComboBoxModel<String> cmbModel =  new DefaultComboBoxModel(mesas);
        cmbMesa.setModel(cmbModel);

        // Establecer la cantidad de personas de la mesa
        int mesa = Integer.parseInt((String) cmbModel.getSelectedItem());
        lblCantidad.setText(String.valueOf(getCantPers(mesa)));

        // Listener de Botón Eliminar
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opc = JOptionPane.showOptionDialog(null, "¿Eliminar esta reserva?", "Confirmar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                int valor = Integer.parseInt(tblReservas.getValueAt(tblReservas.getSelectedRow(), 0).toString());
                if (opc == JOptionPane.YES_OPTION && eliminarFila("reservas", "id", valor)) {
                    mdlTblReservas.removeRow(tblReservas.getSelectedRow());
                    JOptionPane.showMessageDialog(null, "Reserva Eliminada");
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
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error en base de datos:\n" + ex.getMessage());
                }
            }
        });

        // MESAS
        //===============================================================================
    }

    // Obtener los datos de la reserva en la base de datos
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
    }
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
    }
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
    }
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

        try{
            LocalDate fechaSelect = LocalDate.of(anioSelect, mesSelect, diaSelect);
            if (fechaSelect.isBefore(hoy)){
                lblInfoFecha.setText("Fecha inválida");
                fecha = false;
            }
            else{
                lblInfoFecha.setText("");
                fecha = true;
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en fecha:\n" + e.getMessage());
        }
    }
    public void validarHora(){
        int horaSelect = Integer.parseInt(spnHora.getValue().toString());
        int minSelect = Integer.parseInt(spnMinutos.getValue().toString());

        try{
            // Validación de formato 24h
            if (horaSelect < 0 || horaSelect > 23 || minSelect < 0 || minSelect > 59) {
                lblInfoHora.setText("Error");
                JOptionPane.showMessageDialog(null, "La hora seleccionada no es válida. (Formato 24 horas)", "Error", JOptionPane.ERROR_MESSAGE);
                spnHora.setValue(nowHora.getHour());
                spnMinutos.setValue(nowHora.getMinute());
                hora = false;
                lblInfoHora.setText("");
            }
            else if (LocalTime.of(horaSelect, minSelect).isBefore(LocalTime.now()) || LocalTime.of(horaSelect, minSelect).equals(LocalTime.now())){
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
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = ConexionBD.getConnection();
        if (conn == null) return;
        FormPrincipal ventanaForm = new FormPrincipal(conn);
        ventanaForm.setContentPane(ventanaForm.vntPrincipal);
        ventanaForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cambiar fondo(verde menta)
        ventanaForm.getContentPane().setBackground(new Color(123, 214, 144));

        // Abrir maximizada (ocupa toda la pantalla pero con bordes)
        ventanaForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //ventanaForm.setSize(1366, 768);
        ventanaForm.setVisible(true);
    }
}
