package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class EPanel extends JPanel {
    public EPanel() {

    }

    @Override
    public void paint(Graphics g) {
        g.drawLine((int) (5),
                (int)(5),
                (int)(10),
                (int)(10));
    }

}
