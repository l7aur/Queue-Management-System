package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class QPanel extends JPanel {
    public QPanel() {
    }
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.pink);
        g.fillOval(1,1,10,10);
    }
}
