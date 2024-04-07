package org.example;

import org.example.businesslogic.SimulationManager;
import org.example.gui.SimulationFrame;

public class App {
    public App() {
        this.simulationFrame = new SimulationFrame();
    }

    void start() {
        this.manager = new SimulationManager();
        Thread thread = new Thread(this.manager);
        thread.start();
    }

    void quit() {

    }

    private SimulationManager manager;
    private SimulationFrame simulationFrame;
}
