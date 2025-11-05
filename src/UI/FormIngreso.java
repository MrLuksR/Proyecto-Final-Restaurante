package UI;

import InterfacesEimpl.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormIngreso extends JFrame {
    private JPanel vntIngreso;
    private JLabel lblTitulo;
    private JButton btnIngresar;
    private JButton btnSalir;
    private JTextField txtUser;
    private JLabel lblUserName;
    private JLabel lblContra;
    private JPasswordField pwdContra;
    private JButton btnShowContra;
    private JLabel lblInfoUser;
    private JLabel lblInfoContra;
    private JLabel lblAbajo;
    public Connection conn;
    private ImageIcon botonMostrar;
    private ImageIcon botonNoMostrar;

    private boolean user, contra, mostrar;

    public FormIngreso() throws SQLException {

        setDesignPrincipal();

        // Listener de texto de Usuario
        txtUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (txtUser.getText().length() == 0) {
                    lblInfoUser.setText("Este campo no puede estar vacío");
                    txtUser.setBackground(Color.red);
                    user = false;
                }
                else{
                    lblInfoUser.setText("");
                    txtUser.setBackground(Color.white);
                    user = true;
                }
                validarIngreso();
            }
        });

        // Listener de campo de contraseña
        pwdContra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (pwdContra.getText().length() == 0) {
                    lblInfoContra.setText("Este campo no puede estar vacío");
                    pwdContra.setBackground(Color.red);
                    btnShowContra.setEnabled(false);
                    contra = false;
                }
                else{
                    lblInfoContra.setText("");
                    pwdContra.setBackground(Color.white);
                    btnShowContra.setEnabled(true);
                    contra = true;
                }

                validarIngreso();
            }
        });

        // Listener de botón de ingreso
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarUsuario(txtUser.getText(), pwdContra.getText());
            }
        });

        // Listener del botón mostrar contraseña
        btnShowContra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrar = !mostrar;
                if (mostrar) {
                    pwdContra.setEchoChar((char) 0);
                    btnShowContra.setIcon(botonNoMostrar);
                } else {
                    pwdContra.setEchoChar('•');
                    btnShowContra.setIcon(botonMostrar);
                }
            }
        });

        // Listener del botón Salir
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opc = JOptionPane.showOptionDialog(null, "¿Seguro que quiere salir?", "Confirmar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (opc == JOptionPane.YES_OPTION) {
                    try {
                        conn.close();
                        System.exit(0);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error en la base de datos: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Listener de la ventana (para saber si el usuario se retira de la aplicación y desconectar con la base de datos)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opc = JOptionPane.showOptionDialog(null, "¿Seguro que quiere salir?", "Confirmar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (opc == JOptionPane.YES_OPTION) {
                    try {
                        conn.close();
                        System.exit(0);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error en la base de datos: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Listener de txtUsuario
        txtUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnIngresar.isEnabled()){
                    validarUsuario(txtUser.getText(), pwdContra.getText());
                }
            }
        });

        // Listener de pwdContra
        pwdContra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnIngresar.isEnabled()){
                    validarUsuario(txtUser.getText(), pwdContra.getText());
                }
                validarIngreso();
            }
        });
    }

    void validarIngreso(){
        if (user && contra)
            btnIngresar.setEnabled(true);
        else
            btnIngresar.setEnabled(false);
    }

    void validarUsuario(String user, String contra){
        String sqlUser = "SELECT user_name FROM personal WHERE user_name = ?"; // Para usuario
        String sqlContra = "SELECT contra, nombre, apellido FROM personal WHERE contra = ?";
        try {
            /* Creamos una variable del tipo PrepareStatement y le pasamos la consulta SQl
             * Luego creamos un ResultSet para comprobar el resultado obtenido de esa consulta*/

            // Para USUARIO
            PreparedStatement pst = conn.prepareStatement(sqlUser);
            pst.setString(1, user);

            ResultSet rsUser = pst.executeQuery();
            if (rsUser.next()) { // Comprobamos si existe resultado

                pst = conn.prepareStatement(sqlContra);
                pst.setString(1, contra);

                ResultSet rsContra = pst.executeQuery();
                if (rsContra.next()) { // Comprobamos si existe resultado
                    String personal = rsContra.getString("nombre") + " " + rsContra.getString("apellido");
                    txtUser.setBackground(Color.green);
                    pwdContra.setBackground(Color.green);
                    JOptionPane.showMessageDialog(null, "Ingreso Exitoso\n" + "Bienvenido " + personal, "Información", JOptionPane.INFORMATION_MESSAGE);
                    FormPrincipal main = new FormPrincipal(conn, personal);
                    main.setVisible(true);
                    this.dispose();
                }
                else {
                    pwdContra.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                    this.contra = false;
                    validarIngreso();
                }
                rsContra.close();
            }
            else {
                txtUser.setBackground(Color.red);
                JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                pwdContra.setBackground(Color.white);
                pwdContra.setText("");
                btnShowContra.setEnabled(false);
                this.user = false;
                this.contra = false;
                validarIngreso();
            }
            // Cerramos el PreparedStatement y ResultSet
            rsUser.close();
            pst.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void setDesignPrincipal(){
        getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        String username = System.getenv("USERNAME");

        ImageIcon logueishon = new ImageIcon(new ImageIcon("C:/Users/" + username + "/IdeaProjects/Imagenes/Logo.png").getImage().getScaledInstance(551,428, Image.SCALE_SMOOTH));

        lblTitulo.setText("");
        lblTitulo.setVisible(true);
        lblTitulo.setIcon(logueishon);

        ImageIcon botonSalir = new ImageIcon(new ImageIcon("C:/Users/" + username + "/IdeaProjects/Imagenes/botonSalir.png").getImage().getScaledInstance(301,57, Image.SCALE_SMOOTH));
        ImageIcon botonSalirPres = new ImageIcon(new ImageIcon("C:/Users/" + username + "/IdeaProjects/Imagenes/botonSalirPres.png").getImage().getScaledInstance(301,57, Image.SCALE_SMOOTH));
        ImageIcon botonIngresar = new ImageIcon(new ImageIcon("C:/Users/" + username + "/IdeaProjects/Imagenes/botonIngresar.png").getImage().getScaledInstance(301,57, Image.SCALE_SMOOTH));
        ImageIcon botonIngresarPres = new ImageIcon(new ImageIcon("C:/Users/" + username + "/IdeaProjects/Imagenes/botonIngresarPres.png").getImage().getScaledInstance(301,57, Image.SCALE_SMOOTH));

        btnIngresar.setText("");
        btnIngresar.setIcon(botonIngresar);
        btnIngresar.setPressedIcon(botonIngresarPres);
        btnIngresar.setBorderPainted(false);       // Quita el borde
        btnIngresar.setContentAreaFilled(false);   // Quita el fondo
        btnIngresar.setFocusPainted(false);        // Quita el resaltado al enfocar

        btnSalir.setText("");
        btnSalir.setIcon(botonSalir);
        btnSalir.setPressedIcon(botonSalirPres);
        btnSalir.setBorderPainted(false);       // Quita el borde
        btnSalir.setContentAreaFilled(false);   // Quita el fondo
        btnSalir.setFocusPainted(false);        // Quita el resaltado al enfocar

        botonMostrar = new ImageIcon(new ImageIcon("C:/Users/" + username + "/IdeaProjects/Imagenes/BotonMostrar.png").getImage().getScaledInstance(38,30, Image.SCALE_FAST));
        botonNoMostrar = new ImageIcon(new ImageIcon("C:/Users/" + username + "/IdeaProjects/Imagenes/BotonNoMostrar.png").getImage().getScaledInstance(38,30, Image.SCALE_FAST));

        btnShowContra.setText("");
        btnShowContra.setIcon(botonMostrar);
        btnShowContra.setBorderPainted(false);
        btnShowContra.setContentAreaFilled(false);
        btnShowContra.setFocusPainted(false);

        lblUserName.setForeground(new Color(44, 49, 58));
        lblUserName.setFont(new Font("Times New Roman", Font.BOLD, 18));

        lblContra.setForeground(new Color(44, 49, 58));
        lblContra.setFont(new Font("Times New Roman", Font.BOLD, 18));

        lblInfoUser.setForeground(new Color(44, 49, 58));
        lblInfoUser.setFont(new Font("Times New Roman", Font.BOLD, 18));

        lblInfoContra.setForeground(new Color(44, 49, 58));
        lblInfoContra.setFont(new Font("Times New Roman", Font.BOLD, 18));

    }

    // Hice este Main para poder abrir la base de datos sin necesidad
    /*
    public static void main(String[] args) throws SQLException {
        Connection conn = ConexionBD.getConnection();
        if (conn == null) return;
        FormIngreso ventanaForm = new FormIngreso();
        ventanaForm.conn = conn;
        ventanaForm.setContentPane(ventanaForm.vntIngreso);
        ventanaForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Cambiar fondo(verde menta)
        ventanaForm.getContentPane().setBackground(new Color(189, 236, 182));

        // Abrir maximizada (ocupa toda la pantalla pero con bordes)
        ventanaForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //ventanaForm.setSize(1366, 768);
        //ventanaForm.setUndecorated(true); // true = sin bordes, false = con bordes
        ventanaForm.setVisible(true);
    }
     */

    public static void main(String[] args) throws SQLException {
        Connection conn = ConexionBD.getConnection();
        if (conn == null) return;
        FormIngreso ventanaForm = new FormIngreso();
        ventanaForm.conn = conn;
        ventanaForm.crearTablas();
        ventanaForm.setContentPane(ventanaForm.vntIngreso);
        ventanaForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ventanaForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventanaForm.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        String username = System.getenv("USERNAME");
        vntIngreso = new PanelConFondo("C:/Users/" + username + "/IdeaProjects/Imagenes/Background.png");
    }

    private void crearTablas(){
        String sqlCreateTables = """
        CREATE TABLE IF NOT EXISTS clientes (
          id INT(11) NOT NULL AUTO_INCREMENT,
          nombre VARCHAR(20) DEFAULT NULL,
          apellido VARCHAR(20) DEFAULT NULL,
          telefono CHAR(10) DEFAULT NULL,
          PRIMARY KEY (id)
        ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
        
        CREATE TABLE IF NOT EXISTS facturas (
          id_factura INT(11) NOT NULL AUTO_INCREMENT,
          nombre VARCHAR(30) DEFAULT NULL,
          cajero VARCHAR(30) DEFAULT NULL,
          fecha DATE DEFAULT NULL,
          hora TIME DEFAULT NULL,
          subTotal DECIMAL(20,2) DEFAULT NULL,
          impuesto DECIMAL(20,2) DEFAULT NULL,
          propina DECIMAL(20,2) DEFAULT NULL,
          metodoPago VARCHAR(30) DEFAULT NULL,
          PRIMARY KEY (id_factura)
        ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
        
        CREATE TABLE IF NOT EXISTS personal (
          ci_personal CHAR(11) NOT NULL,
          nombre VARCHAR(30) DEFAULT NULL,
          apellido VARCHAR(30) DEFAULT NULL,
          rol VARCHAR(30) DEFAULT NULL,
          user_name VARCHAR(50) DEFAULT NULL,
          contra VARCHAR(50) DEFAULT NULL,
          PRIMARY KEY (ci_personal)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
        
        CREATE TABLE IF NOT EXISTS mesas (
          numMesa INT(11) NOT NULL,
          estado VARCHAR(15) DEFAULT NULL,
          capacidad INT(11) DEFAULT NULL,
          mesero CHAR(11) DEFAULT NULL,
          PRIMARY KEY (numMesa),
          KEY personal (mesero),
          CONSTRAINT personal FOREIGN KEY (mesero) REFERENCES personal (ci_personal)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
        
        CREATE TABLE IF NOT EXISTS productos (
          idProducto INT(11) NOT NULL AUTO_INCREMENT,
          nombre VARCHAR(30) DEFAULT NULL,
          precio DECIMAL(20,2) DEFAULT NULL,
          categoria VARCHAR(30) DEFAULT NULL,
          stock INT(11) DEFAULT NULL,
          descripcion VARCHAR(80) DEFAULT NULL,
          PRIMARY KEY (idProducto)
        ) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
        
        CREATE TABLE IF NOT EXISTS pedidos (
          id_pedido INT(11) NOT NULL AUTO_INCREMENT,
          mesa INT(11) DEFAULT NULL,
          estado VARCHAR(30) DEFAULT NULL,
          producto INT(11) DEFAULT NULL,
          cantidad INT(11) DEFAULT NULL,
          totales DECIMAL(20,2) DEFAULT NULL,
          PRIMARY KEY (id_pedido),
          KEY producto (producto),
          KEY FK_pedidos_mesas (mesa),
          CONSTRAINT FK_pedidos_mesas FOREIGN KEY (mesa) REFERENCES mesas (numMesa),
          CONSTRAINT pedidos_ibfk_1 FOREIGN KEY (producto) REFERENCES productos (idProducto)
        ) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
        
        CREATE TABLE IF NOT EXISTS reservas (
          id INT(11) NOT NULL AUTO_INCREMENT,
          ciCliente CHAR(11) DEFAULT NULL,
          cliente VARCHAR(20) DEFAULT NULL,
          fecha DATE DEFAULT NULL,
          hora TIME DEFAULT NULL,
          mesa INT(11) DEFAULT NULL,
          personas INT(11) DEFAULT NULL,
          PRIMARY KEY (id),
          KEY mesa (mesa),
          CONSTRAINT reservas_ibfk_1 FOREIGN KEY (mesa) REFERENCES mesas (numMesa)
        ) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
        """;

        try {
            // separa por ';' y ejecuta cada sentencia
            for (String sql : sqlCreateTables.split(";")) {
                sql = sql.trim();
                if (!sql.isEmpty()) {
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.execute();
                    pst.close();
                }
            }
            JOptionPane.showMessageDialog(null, "Tablas inexistentes creadas correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error:\n" + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
