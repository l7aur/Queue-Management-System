package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class Ppanel extends JPanel {
    public Ppanel(Integer id) {

    }

    @Override
    public void paint(Graphics g) {
        g.drawRect((int) (5),
                (int)(5),
                (int)(10),
                (int)(10));
    }
}
