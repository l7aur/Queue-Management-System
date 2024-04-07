package org.example.utility;

import org.example.businesslogic.Server;
import org.example.businesslogic.Task;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task t);
}
