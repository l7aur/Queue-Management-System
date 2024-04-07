package org.example.businesslogic;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    public Server(String serverName) {
        this.serverName = serverName;
        this.taskQ = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
        this.numberOfPeople = new AtomicInteger(0);
    }

    @Override
    public void run() {
        while (!hasFinished.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Exception: " + e);
            }
            if(this.taskQ.size() > 1)
                this.waitingPeriod.addAndGet(this.taskQ.size() - 1);
            for (Task task : this.taskQ) {
                task.decrementServiceTime();
                if(task.getServiceTime() <= 0)
                    this.taskQ.remove(task);
                break;
            }
        }
    }

    public synchronized void addTask(Task task) {
        this.numberOfPeople.incrementAndGet();
        this.waitingPeriod.addAndGet(task.getServiceTime());
        this.taskQ.add(task);
    }

    public String getServerName() {
        return serverName;
    }
    public BlockingQueue<Task> getTaskQ() {
        return taskQ;
    }
    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
    public AtomicInteger getNumberOfPeople() {
        return numberOfPeople;
    }

    public synchronized Boolean setHasFinished() {
        if(this.taskQ.isEmpty()) {
            this.hasFinished.set(true);
            return true;
        }
        return false;
    }
    public synchronized void forceStop() {
        this.hasFinished.set(true);
    }

    private final String serverName;
    private BlockingQueue<Task> taskQ;
    private AtomicInteger waitingPeriod;
    private AtomicInteger numberOfPeople;
    private AtomicBoolean hasFinished = new AtomicBoolean(false);
}
