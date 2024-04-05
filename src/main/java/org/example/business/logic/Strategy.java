package org.example.business.logic;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, MyTask t);
}
