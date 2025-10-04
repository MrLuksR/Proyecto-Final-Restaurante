package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Random;

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

        // LISTENERS
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
        lblNombre.setBounds(0,0,vntReservas.getWidth(),70);
        lblNombre.setHorizontalAlignment(SwingConstants.LEFT);

        vntReservas.add(lblNombre);
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

    public static void main(String[] args) throws SQLException {
        FormMain ventanaForm = new FormMain();
        ventanaForm.setContentPane(ventanaForm.vntMain);
        ventanaForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Cambiar fondo(verde menta)
        ventanaForm.getContentPane().setBackground(new Color(123, 214, 144));

        // Abrir maximizada (ocupa toda la pantalla pero con bordes)
        ventanaForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //ventanaForm.setSize(1366, 768);
        ventanaForm.setUndecorated(true); // true = sin bordes, false = con bordes
        ventanaForm.setVisible(true);
    }

}
