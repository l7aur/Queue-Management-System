package org.example.business.logic;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    synchronized public void addTask(List<Server> servers, MyTask t) {
        Server bestServer = null;
        Integer shortestQ = Integer.MAX_VALUE;
        for (Server server : servers)
            if(server.getTasks().size() < shortestQ) {
                shortestQ = server.getTasks().size();
                bestServer = server;
            }
        bestServer.addTask(t);
    }
}
