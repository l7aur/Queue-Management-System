package org.example.controllers;

import org.example.utility.SelectionPolicy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

public class StartButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        for (JTextField field : textFields) {
            inputData.add(Integer.valueOf(field.getText()));
        }
        Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if(button.isSelected()) {
                if (button.getText().equals("Shortest Waiting Time")) {
                    this.selectionPolicy = SelectionPolicy.SHORTEST_TIME;
                }
                else {
                    this.selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
                }
                break;
            }
        }
        for (Integer inputDatum : inputData) {
            System.out.println(inputDatum);
        }
        System.out.println(selectionPolicy.toString());
        inputData.clear();
    }

    public StartButtonActionListener(ArrayList<JTextField> textFields, ButtonGroup buttonGroup) {
        super();
        this.textFields = textFields;
        this.buttonGroup = buttonGroup;
        this.inputData = new ArrayList<>();
    }
    private final ArrayList<JTextField> textFields;
    private final ButtonGroup buttonGroup;
    private final ArrayList<Integer> inputData;
    private SelectionPolicy selectionPolicy;
}
