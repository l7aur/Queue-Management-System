package org.example.gui;

import org.example.controllers.StartButtonActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class ApplicationFrame extends JFrame {
    public ApplicationFrame() {
        super("Queue Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.content = this.getContentPane();
        this.content.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.textFields = new ArrayList<>();
        this.createContent();
        this.setBounds(screenSize.width / 4,screenSize.height / 4,screenSize.width / 2,screenSize.height / 2);
        this.setVisible(true);
    }

    protected void createButton() {
        JButton startButton = new JButton("Start Simulation");
        startButton.addActionListener(new StartButtonActionListener(textFields, group));
        this.content.add(startButton, BorderLayout.SOUTH);
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
        this.content.add(panel, BorderLayout.CENTER);
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
        this.content.add(panel, BorderLayout.CENTER);
        this.createInputPanel();
        this.createButton();
    }

    private final Container content;
    private final ArrayList<JTextField> textFields;
    private ButtonGroup group;
}
