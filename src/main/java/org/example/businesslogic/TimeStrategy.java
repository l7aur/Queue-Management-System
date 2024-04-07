package org.example.businesslogic;

import org.example.utility.Strategy;
import java.util.List;

public class TimeStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        Server bestServer = servers.getFirst();
        Integer bestWaitingPeriod = Integer.MAX_VALUE;
        Integer haveToWait = 0;
        for (Server server : servers) {
            haveToWait = 0;
            for (Task task : server.getTaskQ())
                haveToWait += task.getServiceTime();
            if (haveToWait < bestWaitingPeriod) {
                bestWaitingPeriod = haveToWait;
                bestServer = server;
            }
        }
        bestServer.addTask(t);
    }
}
