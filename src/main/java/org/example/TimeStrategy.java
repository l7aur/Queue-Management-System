package org.example;

import java.util.List;

public class TimeStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        Server bestServer = null;
        Integer bestWaitingPeriod = Integer.MAX_VALUE;
//        for (Server server : servers)
//            if(server.getWaitingPeriod().get() < bestWaitingPeriod) {
//                bestWaitingPeriod = server.getWaitingPeriod().get();
//                bestServer = server;
//            }
//        bestServer.addTask(t);
    }
}
