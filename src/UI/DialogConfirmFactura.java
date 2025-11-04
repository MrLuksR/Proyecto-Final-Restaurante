package UI;

import InterfacesEimpl.FinalDAO;
import Modelo.CantidadNegativaException;
import Modelo.CrearFactura;
import Modelo.Factura;
import Modelo.Pedido;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class DialogConfirmFactura extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtNombre;
    private JLabel lblNombre;
    private JLabel lblPropina;
    private JSpinner spnPropina;
    private JLabel lblMetPago;
    private JComboBox cmbMetPago;
    private int numMesa;
    protected String personal;
    private String[] columnas;
    private JTable table;
    private Pedido[] pedidos;
    private DefaultTableModel model;
    public Connection conn;

    public DialogConfirmFactura(Connection conn, int numMesa, String personal, String[] columnas, JTable table,  Pedido[] pedidos, DefaultTableModel model) {
        this.conn = conn;
        this.numMesa = numMesa;
        this.personal = personal;
        this.columnas = columnas;
        this.table = table;
        this.pedidos = pedidos;
        this.model = model;

        // ComboBox (De métod0 de pago)
        String[] metodos = {"Efectivo", "Tarjeta", "Transferencia"};
        cmbMetPago.setModel(new DefaultComboBoxModel(metodos));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Listener del Spinner de Propina
        spnPropina.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                CantidadNegativaException ex = new CantidadNegativaException("La cantidad no puede ser negativa");
                int valor = (int) spnPropina.getValue();
                if (valor < 0) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",  JOptionPane.ERROR_MESSAGE);
                    spnPropina.setValue(0);
                    throw ex;
                }
            }
        });
    }

    private void onOK(){
        if (!txtNombre.getText().isEmpty()) {
            double propina = Double.parseDouble(spnPropina.getValue().toString());
            String metPago = String.valueOf(cmbMetPago.getSelectedItem());
            LocalDate fecha = LocalDate.now();
            LocalTime hora = LocalTime.now();
            // Crear factura y creador
            Factura factura = new Factura(txtNombre.getText(), personal, fecha, hora, pedidos, propina, metPago);
            CrearFactura cf1 = new CrearFactura("C:\\Users\\range\\OneDrive\\Desktop\\Factura" + pedidos[0].getId() + ".pdf");

            // FinalDAO para guardar la factura
            FinalDAO f1 = new FinalDAO(conn);

            // Creamos la factura y la guardamos
            cf1.crear(factura);
            try {
                f1.guardarFactura(factura);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error:\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
            String sqlElimProds = "DELETE FROM pedidos WHERE mesa = ?";

            try{
                PreparedStatement pst = conn.prepareStatement(sqlElimProds);
                pst.setInt(1, numMesa);
                pst.executeUpdate();
                model.setDataVector(null, columnas);
                table.setModel(model);
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Error:\n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
            JOptionPane.showMessageDialog(null,"Pedido cerrado. Factura creada en:\n" + cf1.getPath(), "Factura", JOptionPane.INFORMATION_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "El campo no puede estar vacío", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    String getPersonal() {
        return personal;
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        DialogConfirmFactura dialog = new DialogConfirmFactura();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
