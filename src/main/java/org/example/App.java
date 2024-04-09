package org.example;

import org.example.businesslogic.SimulationManager;
import org.example.controllers.StartButtonActionListener;
import org.example.gui.ApplicationFrame;

public class App {
    public App() {
        this.applicationFrame = new ApplicationFrame(this);
        this.actionListener = this.applicationFrame.getFrontPanel().getActionListener();
    }

    public void start() {
        SimulationManager manager = new SimulationManager(actionListener.getInputData(), actionListener.getSelectionPolicy(), actionListener.getBackPanel());
        Thread thread = new Thread(manager);
        thread.start();
    }

    private final ApplicationFrame applicationFrame;
    private final StartButtonActionListener actionListener;
}
