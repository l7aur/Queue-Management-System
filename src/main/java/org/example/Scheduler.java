package org.example;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Scheduler {
    public Scheduler(Integer maximumNumberOfServers, Integer maximumTasksPerServer) {
        this.maximumNumberOfServers = maximumNumberOfServers;
        this.maximumTasksPerServer = maximumTasksPerServer;
        this.serverList = new ArrayList<>();
        for (int i = 0; i < maximumNumberOfServers; i++) {
            this.serverList.add(new Server("Thread #" + i));
            Thread t = new Thread(this.serverList.get(i), this.serverList.get(i).getServerName());
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        switch (policy) {
            case SHORTEST_TIME -> this.strategy = new TimeStrategy();
            case SHORTEST_QUEUE -> this.strategy = new ShortestQueueStrategy();
            default -> System.out.println("Invalid Selection Policy");
        }
    }

    public synchronized void dispatchTask(Task task) {
        this.strategy.addTask(this.serverList, task);
        System.out.println("dispatch finished");
    }

    public synchronized void stopServers() {
        for (Server server : this.serverList) {
            server.setHasFinished();
        }
    }

    public ArrayList<Server> getServerList() {
        return serverList;
    }

    private ExecutorService executorService;
    private ArrayList<Server> serverList;
    private Integer maximumNumberOfServers;
    private Integer maximumTasksPerServer;
    private Strategy strategy;
}
