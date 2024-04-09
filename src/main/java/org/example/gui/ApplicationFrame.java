package org.example.gui;

import org.example.App;
import org.example.controllers.StartButtonActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class ApplicationFrame extends JFrame {
    public ApplicationFrame(App app) {
        super("Queue Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(screenSize.width / 4,screenSize.height / 4,screenSize.width / 2,screenSize.height / 2);
        this.content = this.getContentPane();
        this.cardLayout = new CardLayout();
        this.content.setLayout(cardLayout);
        this.backPanel = new BackPanel(screenSize.width / 4, screenSize.height / 4);
        this.frontPanel = new FrontPanel(app, this.cardLayout, this.content, this.backPanel);
        this.content.add(this.frontPanel);
        this.content.add(this.backPanel);
        this.cardLayout.first(this.content);
        this.setVisible(true);
    }

    public FrontPanel getFrontPanel() {
        return frontPanel;
    }

    public BackPanel getBackPanel() {
        return backPanel;
    }

    private final Container content;
    private CardLayout cardLayout;
    private FrontPanel frontPanel;
    private BackPanel backPanel;
}
