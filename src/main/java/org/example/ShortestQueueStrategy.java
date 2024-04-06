package org.example;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public synchronized void addTask(List<Server> servers, Task t) {
        System.out.println("strategy here");
        Server bestServer = null;
        Integer shortestQ = Integer.MAX_VALUE;
        for (Server server : servers)
            if(server.getTaskQ().size() < shortestQ) {
                shortestQ = server.getTaskQ().size();
                bestServer = server;
            }
        bestServer.addTask(t);
        System.out.println("Strategy finished");
    }
}
