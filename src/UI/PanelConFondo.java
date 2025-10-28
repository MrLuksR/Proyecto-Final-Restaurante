package UI;

import javax.swing.*;
import java.awt.*;

public class PanelConFondo extends JPanel {
    private Image imagen;

    public PanelConFondo(String rutaImagen) {
        imagen = new ImageIcon(rutaImagen).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}