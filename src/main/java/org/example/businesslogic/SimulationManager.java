package org.example.businesslogic;

import org.example.utility.SelectionPolicy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimulationManager implements Runnable{
    public SimulationManager(){
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        this.scheduler.changeStrategy(selectionPolicy);
        this.stateFile = initializeOutputFile();
    }

    private File initializeOutputFile() {
        this.stateFile = new File("stateOfServers.txt");
        if(stateFile.exists() && stateFile.delete()){
            try {
                if(stateFile.createNewFile())
                    return stateFile;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public void run() {
        BlockingQueue<Task> tasks = this.generatePredefinedTasks();
        this.printTasks(tasks);
        while(currentTime <= timeLimit && !noWork) {
            System.out.println("<DEBUG>Current time: " + currentTime);
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
    private void printTasks(BlockingQueue<Task> tasks) {
        File path = new File("tasks.txt");
        try {
            FileWriter fileOut = new FileWriter(path);
            fileOut.write(tasks.size() + " generated tasks:\n");
            for (Task task : tasks) {
                fileOut.write(task.toString() + "\n");
            }
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("WHERE FILE?");
        }
    }

    private void printStateOfServers(Integer currentTime) {
        try {
            FileWriter fileOut = new FileWriter(stateFile, true);
            fileOut.write("Current time: " + currentTime + "\n");
            fileOut.write("State of servers:\n");
            for (Server server : this.scheduler.getServerList()) {
                fileOut.write("server: <" + server.getServerName() + "> has amount of work: " + server.getTaskQ().size() + "\n");
                for (Task task : server.getTaskQ()) {
                    fileOut.write("\t" + task.toString() + "\n");
                }
            }
            fileOut.write("\n\n");
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("WHERE FILE?");
        }
    }

    private BlockingQueue<Task> generateRandomTasks(Integer howMany) {
        Random random = new Random();
        LinkedBlockingQueue<Task> arrayList = new LinkedBlockingQueue<>();
        for (int i = 0; i < howMany; i++) {
            arrayList.add(new Task(i + 1, random.nextInt(maxArrivalTime - minArrivalTime) + 1,
                    random.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime));
        }
        return arrayList;
    }


    private BlockingQueue<Task> generatePredefinedTasks() {
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
        return arrayList;
    }
    private Integer currentTime = 0;
    private final Scheduler scheduler;
    private Boolean noWork = false;
    private File stateFile;

    public Integer timeLimit = 10;
    public Integer maxProcessingTime = 5;
    public Integer minProcessingTime = 1;
    public Integer minArrivalTime = 1;
    public Integer maxArrivalTime = 1;
    public Integer numberOfServers = 3;
    public Integer numberOfClients = 10;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
}
