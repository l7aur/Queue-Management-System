package org.example;

import org.example.business.logic.SimulationManager;
import org.example.gui.SimulationFrame;

import java.lang.reflect.Executable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    private SimulationFrame frame;
    private SimulationManager manager;

    public App() {
        this.frame = new SimulationFrame();
    }

    void start() {
        this.manager = new SimulationManager();
        Thread thread = new Thread(this.manager);
        thread.start();
    }

    void quit() {

    }
}
