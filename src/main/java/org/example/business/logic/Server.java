package org.example.business.logic;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private AtomicBoolean flag = new AtomicBoolean(false);
    @Override
    public void run() {
        while (true) {
            MyTask currentTask = null;
            try {
                currentTask = this.tasks.take();
                currentTask.decrementServiceTime();
                this.waitingPeriod.incrementAndGet();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public Server() {
        this.tasks = new LinkedBlockingQueue<MyTask>();
        this.waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(MyTask task) {
        this.tasks.add(task);
        this.waitingPeriod.addAndGet(task.getServiceTime());
    }

    public BlockingQueue<MyTask> getTasks() {
        return this.tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    private BlockingQueue<MyTask> tasks;
    private AtomicInteger waitingPeriod;
}
