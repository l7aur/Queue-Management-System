package org.example;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimulationManager implements Runnable{
    public SimulationManager(){
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        this.scheduler.changeStrategy(selectionPolicy);
    }

    @Override
    public void run() {
        BlockingQueue<Task> tasks = this.generatePredefinedTasks(numberOfClients);
        while(currentTime <= timeLimit && !noWork) {
            Integer numberOfLeftServers = numberOfServers;
            for (Task task : tasks) {
                if (task.getArrivalTime().compareTo(currentTime) <= 0) {
                    this.scheduler.dispatchTask(task);
                    tasks.remove(task);
                    numberOfLeftServers--;
                    if(numberOfLeftServers == 0){
                        break;
                    }
                }
            }
            printStateOfServers(currentTime);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                System.out.println("Simulation interrupted");
            }

            currentTime++;
            if(currentTime > timeLimit){
                this.scheduler.forceStopServers();
            }
            if(tasks.isEmpty()) {
                noWork = this.scheduler.stopServers();
            }
        }

    }

    private void printStateOfServers(Integer currentTime) {
        System.out.println("Current time: " + currentTime);
        System.out.println("State of servers:");
        this.scheduler.getServerList().forEach(server -> {
            System.out.println("server: <" + server.getServerName() + "> has amount of work: " + server.getTaskQ().size());
            for (Task task : server.getTaskQ()) {
                System.out.println(task.toString());
            }
        });
        System.out.println();
    }

    private BlockingQueue<Task> generatePredefinedTasks(Integer howMany) {
        Random random = new Random();
        BlockingQueue<Task> arrayList = new LinkedBlockingQueue<>();
        arrayList.add(new Task(1, 1, 2));
        arrayList.add(new Task(2, 2, 5));
        arrayList.add(new Task(3, 3, 1));
        arrayList.add(new Task(4, 4, 4));
        arrayList.add(new Task(5, 5, 7));
        ///5
        arrayList.add(new Task(6, 1, 2));
        arrayList.add(new Task(7, 5, 3));
        arrayList.add(new Task(8, 3, 4));
        arrayList.add(new Task(9, 2, 1));
        arrayList.add(new Task(10, 5, 1));
        ///10
        arrayList.forEach(task -> {
            System.out.println(task.toString());
        });
        return arrayList;
    }
    private Integer currentTime = 0;
    private Scheduler scheduler;
    private Boolean noWork = false;

    public Integer timeLimit = 10;
    public Integer maxProcessingTime = 5;
    public Integer minProcessingTime = 1;
    public Integer numberOfServers = 3;
    public Integer numberOfClients = 10;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
}
