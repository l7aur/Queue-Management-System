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
            System.out.println("currentTime: " + currentTime);
            Integer numberOfLeftServers = numberOfServers;
            for (Task task : tasks) {
                if (task.getArrivalTime().compareTo(currentTime) <= 0) {
                    System.out.println("Exists task: " + task.getArrivalTime());
                    this.scheduler.dispatchTask(task);
                    tasks.remove(task);
                    numberOfLeftServers--;
                    System.out.println("scheduler finished");
                    if(numberOfLeftServers == 0){
                        break;
                    }
                }
            }
            System.out.println("Size of tasks: " + tasks.isEmpty());
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                System.out.println("Simulation interrupted");
            }
            System.out.println("\nState of servers");
            this.scheduler.getServerList().forEach(server -> {
                System.out.println("server: " + server.getServerName());
                for (Task task : server.getTaskQ()) {
                    System.out.println(task.toString());
                }
            });
            System.out.println();

            currentTime++;
            if(tasks.isEmpty()){
                System.out.println(">>>>>>>>>>>>>>>>>end of servers");
                this.scheduler.stopServers();
                noWork = true;
            }
        }

    }

    private BlockingQueue<Task> generatePredefinedTasks(Integer howMany) {
        Random random = new Random();
        BlockingQueue<Task> arrayList = new LinkedBlockingQueue<>();
        for (int i = 0; i < howMany; i++) {
            arrayList.add(new Task(i + 1, 1, 2));
        }
        arrayList.forEach(task -> {
            System.out.println(task.toString());
        });
        return arrayList;
    }
    private Integer currentTime = 0;
    private Scheduler scheduler;
    private Boolean noWork = false;

    public Integer timeLimit = 5;
    public Integer maxProcessingTime = 5;
    public Integer minProcessingTime = 1;
    public Integer numberOfServers = 3;
    public Integer numberOfClients = 5;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
}
