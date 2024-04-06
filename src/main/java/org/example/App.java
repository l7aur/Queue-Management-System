package org.example;

public class App {
    public App() {

    }

    void start() {
        this.manager = new SimulationManager();
        Thread thread = new Thread(this.manager);
        thread.start();
    }

    void quit() {

    }

    private SimulationManager manager;
}
