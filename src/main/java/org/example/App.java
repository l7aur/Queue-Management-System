package org.example;

import org.example.businesslogic.SimulationManager;
import org.example.controllers.StartButtonActionListener;
import org.example.gui.ApplicationFrame;

public class App {
    public App() {
        this.applicationFrame = new ApplicationFrame(this);
    }

    public void start() {
        ApplicationFrame applicationFrame = new ApplicationFrame(this);
        StartButtonActionListener actionListener = this.applicationFrame.getActionListener();
        SimulationManager manager = new SimulationManager(actionListener.getInputData(), actionListener.getSelectionPolicy());
        Thread thread = new Thread(manager);
        thread.start();
    }

    private final ApplicationFrame applicationFrame;
}
