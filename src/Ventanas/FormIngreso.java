package Ventanas;

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
    public Connection conn = ConexionBD.getConnection();

    private boolean user, contra, mostrar;

    public FormIngreso() throws SQLException {

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
                if (mostrar){
                    pwdContra.setEchoChar((char) 0);
                    btnShowContra.setText("Ah No!");
                }
                else {
                    pwdContra.setEchoChar('•');
                    btnShowContra.setText("Ah Si!");
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
    }

    void validarIngreso(){
        if (contra && contra)
            btnIngresar.setEnabled(true);
        else
            btnIngresar.setEnabled(false);
    }

    void validarUsuario(String user, String contra){
        String sqlUser = "SELECT user_name FROM personal WHERE user_name = ?"; // Para usuario
        String sqlContra = "SELECT contra FROM personal WHERE contra = ?";
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
                    txtUser.setBackground(Color.green);
                    pwdContra.setBackground(Color.green);
                    JOptionPane.showMessageDialog(null, "Ingreso Exitoso", "Info", JOptionPane.INFORMATION_MESSAGE);
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

    static void main() throws SQLException{
        FormIngreso ventanaForm = new FormIngreso();
        if (ventanaForm.conn == null) return;
        ventanaForm.setContentPane(ventanaForm.vntIngreso);
        ventanaForm.setBounds(300,200,600,400);
        ventanaForm.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ventanaForm.setVisible(true);
    }
}
