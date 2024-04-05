package org.example.business.logic;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Scheduler {

    public Scheduler(Integer maximumNumberOfServers, Integer maximumTasksPerServer) {
        this.maximumNumberOfServers = maximumNumberOfServers;
        this.maximumTasksPerServer = maximumTasksPerServer;
        this.serverList = new ArrayList<>();
        for (int i = 0; i < maximumNumberOfServers; i++) {
            this.serverList.add(new Server());
            Thread t = new Thread(this.serverList.get(i));
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

    synchronized public void dispatchTask(MyTask task) {
        this.strategy.addTask(this.serverList, task);
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
