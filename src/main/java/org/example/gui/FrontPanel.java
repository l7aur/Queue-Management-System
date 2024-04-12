package org.example.gui;

import org.example.App;
import org.example.controllers.StartButtonActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class FrontPanel extends JPanel {
    private ButtonGroup group;
    private final ArrayList<JTextField> textFields;
    private StartButtonActionListener actionListener;
    private final App currentApp;
    private final CardLayout cardLayout;
    private final Container content;
    private final BackPanel backPanel;

    public FrontPanel(App app, CardLayout cardLayout, Container container, BackPanel backPanel) {
        super();
        this.setLayout(new BorderLayout());
        this.textFields = new ArrayList<>();
        this.currentApp = app;
        this.cardLayout = cardLayout;
        this.backPanel = backPanel;
        this.content = container;
        this.createContent();
    }
    protected void createButton() {
        JButton startButton = new JButton("Start Simulation");
        this.actionListener = new StartButtonActionListener(this.textFields, this.group, this.currentApp, this.cardLayout, this.content, this.backPanel);
        startButton.addActionListener(actionListener);
        this.add(startButton, BorderLayout.SOUTH);
    }

    protected void createInputPanel() {
        String[] labelNames = {
                "Number of clients: ",
                "Number of queues: ",
                "Time of simulation: ",
                "Minimum arrival time: ",
                "Maximum arrival time: ",
                "Minimum service time: ",
                "Maximum service time: "};
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(labelNames.length + 1, 1, 1, 1));
        panel.setBorder(new TitledBorder("Input Section"));
        for (String labelName : labelNames) {
            JPanel labelPanel = createInputField(labelName);
            panel.add(labelPanel);
        }
        panel.add(this.createChoiceField());
        this.add(panel, BorderLayout.CENTER);
    }

    protected JPanel createChoiceField() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Strategy: ");
        this.group = new ButtonGroup();
        JRadioButton button1 = new JRadioButton("Shortest Queue");
        JRadioButton button2 = new JRadioButton("Shortest Waiting Time");
        this.group.add(button1);
        this.group.add(button2);
        label.setLabelFor(button1);
        panel.add(label);
        panel.add(button1);
        panel.add(button2);
        this.group.setSelected(button1.getModel(), true);
        return panel;
    }

    protected JPanel createInputField(String labelName){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label1 = new JLabel(labelName);
        JTextField textField1 = new JTextField(7);
        textField1.setToolTipText("Enter a number");
        label1.setLabelFor(textField1);
        panel.add(textField1, BorderLayout.EAST);
        panel.add(label1, BorderLayout.WEST);
        this.textFields.add(textField1);
        return panel;
    }

    protected void createContent() {
        JPanel panel = new JPanel();
        this.add(panel, BorderLayout.CENTER);
        this.createInputPanel();
        this.createButton();
    }

    public StartButtonActionListener getActionListener() {
        return actionListener;
    }
}
