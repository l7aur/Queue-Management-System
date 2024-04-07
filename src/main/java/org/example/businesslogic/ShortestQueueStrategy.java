package org.example.businesslogic;

import org.example.utility.Strategy;
import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public synchronized void addTask(List<Server> servers, Task t) {
        Server bestServer = servers.getFirst();
        for (Server server : servers)
            if(server.getTaskQ().size() < bestServer.getTaskQ().size()) {
                bestServer = server;
            }
        bestServer.addTask(t);
    }
}
