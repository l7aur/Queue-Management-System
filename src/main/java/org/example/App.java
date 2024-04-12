package org.example;

import org.example.businesslogic.SimulationManager;
import org.example.controllers.StartButtonActionListener;
import org.example.gui.ApplicationFrame;

public class App {
    private final StartButtonActionListener actionListener;

    public App() {
        ApplicationFrame applicationFrame = new ApplicationFrame(this);
        this.actionListener = applicationFrame.getFrontPanel().getActionListener();
    }

    public void start() {
        SimulationManager manager = new SimulationManager(actionListener.getInputData(), actionListener.getSelectionPolicy(), actionListener.getBackPanel());
        Thread thread = new Thread(manager);
        thread.start();
    }
}
