package org.example.business.logic;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {

    @Override
    public void run() {
        Integer currentTime = 0;
        while (currentTime <= this.timeLimit) {
            this.generatedTasks.forEach( myTask -> System.out.print(myTask.getId() + " "));
            System.out.println();
            for (MyTask generatedTask : this.generatedTasks) {
                if(generatedTask.getArrivalTime().equals(currentTime)) {
                    System.out.println(generatedTask.getArrivalTime() + "arr time");
                    this.scheduler.dispatchTask(generatedTask);
//                    this.generatedTasks.remove(generatedTask);
                }
            }
            this.generatedTasks.forEach( myTask -> System.out.print(myTask.getId() + " "));
            System.out.println();
            AtomicInteger index = new AtomicInteger(1);
            System.out.println(currentTime);
            this.scheduler.getServerList().forEach(server -> {
                System.out.print("Server #" + index.getAndIncrement() + ": ");
                for (MyTask task : server.getTasks()) {
                    System.out.print(task.toString() + " | ");
                }
                System.out.println();
            });
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.printStatistics();
    }

    public void printStatistics() {
        Integer index = 0;
        System.out.println("\nWaiting time in each queue");
        for (Server server : this.scheduler.getServerList()) {
            index++;
            System.out.println("Server #" + index + ": " + server.getWaitingPeriod());
        }
    }

    public SimulationManager() {
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        this.scheduler.changeStrategy(selectionPolicy);
//        this.generatedTasks = this.generateRandomTasks(numberOfClients);
        this.generatedTasks = this.generatePredefinedTasks(numberOfClients);
    }

    private LinkedBlockingQueue<MyTask> generateRandomTasks(Integer howMany) {
        Random random = new Random();
        LinkedBlockingQueue<MyTask> arrayList = new LinkedBlockingQueue<>();
        for (int i = 0; i < howMany; i++) {
            arrayList.add(new MyTask(i + 1, random.nextInt(timeLimit) + 1,
                    random.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime));
        }
        arrayList.forEach(task -> {
            System.out.println(task.toString());
        });
        return arrayList;
    }
    private LinkedBlockingQueue<MyTask> generatePredefinedTasks(Integer howMany) {
        Random random = new Random();
        LinkedBlockingQueue<MyTask> arrayList = new LinkedBlockingQueue<>();
        for (int i = 0; i < howMany; i++) {
            arrayList.add(new MyTask(i + 1, i + 1, 1));
        }
        arrayList.forEach(task -> {
            System.out.println(task.toString());
        });
        return arrayList;
    }

    //input data
    public Integer timeLimit = 10;
    public Integer maxProcessingTime = 5;
    public Integer minProcessingTime = 1;
    public Integer numberOfServers = 3;
    public Integer numberOfClients = 10;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;

    //
    private Scheduler scheduler;
    private LinkedBlockingQueue<MyTask> generatedTasks;
}
