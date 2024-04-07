package org.example;

import org.example.businesslogic.SimulationManager;
import org.example.gui.ApplicationFrame;

public class App {
    public App() {
        this.simulationFrame = new ApplicationFrame();
    }

    void start() {
        //this.manager = new SimulationManager();
        //Thread thread = new Thread(this.manager);
        //thread.start();
    }

    void quit() {

    }

    private SimulationManager manager;
    private ApplicationFrame simulationFrame;
}
