package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class QPanel extends JPanel {
    public QPanel() {
    }
    @Override
    public void paint(Graphics g) {
//        g.drawOval((int) (0.25 * this.getMaximumSize().width),
//                (int)(0.25 + this.getMaximumSize().width),
//                (int)(0.75 * this.getMaximumSize().width),
//                (int)(0.75 * this.getMaximumSize().width));
        g.drawOval((int) (5),
                (int)(5),
                (int)(10),
                (int)(10));
    }
}
