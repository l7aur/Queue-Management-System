package org.example.gui;

import org.example.App;

import javax.swing.*;
import java.awt.*;

public class ApplicationFrame extends JFrame {
    private final FrontPanel frontPanel;

    public ApplicationFrame(App app) {
        super("Queue Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(screenSize.width / 4,screenSize.height / 4,screenSize.width / 2,screenSize.height / 2);
        Container content = this.getContentPane();
        CardLayout cardLayout = new CardLayout();
        content.setLayout(cardLayout);
        BackPanel backPanel = new BackPanel(screenSize.width / 4, screenSize.height / 4);
        this.frontPanel = new FrontPanel(app, cardLayout, content, backPanel);
        content.add(this.frontPanel);
        content.add(backPanel);
        cardLayout.first(content);
        this.setVisible(true);
    }

    public FrontPanel getFrontPanel() {
        return frontPanel;
    }
}
