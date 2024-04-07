package org.example;

import org.example.businesslogic.SimulationManager;
import org.example.controllers.StartButtonActionListener;
import org.example.gui.ApplicationFrame;

public class App {
    public App() {
        this.applicationFrame = new ApplicationFrame(this);
    }

    public void start() {
        StartButtonActionListener actionListener = this.applicationFrame.getActionListener();
        this.manager = new SimulationManager(actionListener.getInputData(), actionListener.getSelectionPolicy());
        Thread thread = new Thread(this.manager);
        thread.start();
        System.out.println("OK");
    }

    private SimulationManager manager;
    private final ApplicationFrame applicationFrame;
}
