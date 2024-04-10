package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class EPanel extends JPanel {
    public EPanel() {
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.green);
        g.drawRect(0, 0, 10, 5);
    }

}
