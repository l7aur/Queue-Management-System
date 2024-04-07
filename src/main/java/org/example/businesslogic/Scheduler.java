package org.example.businesslogic;

import org.example.utility.SelectionPolicy;
import org.example.utility.Strategy;

import java.util.ArrayList;

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
    }

    public synchronized Boolean stopServers() {
        Boolean flag = true;
        for (Server server : this.serverList) {
            flag = flag & server.setHasFinished();
        }
        return flag;
    }

    public synchronized void forceStopServers() {
        for (Server server : this.serverList) {
            server.forceStop();
        }
    }

    public ArrayList<Server> getServerList() {
        return serverList;
    }

    private final ArrayList<Server> serverList;
    private final Integer maximumNumberOfServers;
    private final Integer maximumTasksPerServer;
    private Strategy strategy;
}
