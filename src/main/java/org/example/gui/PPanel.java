package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class PPanel extends JPanel {
    public PPanel(Integer id) {
        this.add(new JLabel("ID: " + id));
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(1,1,10,5);
    }
}
