package org.example.businesslogic;

import org.example.gui.BackPanel;
import org.example.utility.SelectionPolicy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimulationManager implements Runnable{
    private Integer currentTime = 0;
    private final Scheduler scheduler;
    private Boolean noWork = false;
    private File stateFile;
    private BlockingQueue<Task> tasks;
    private float totalServiceTime;
    private Integer peakHour;
    private Integer clientsAtPeakHour;
    private final BackPanel backPanel;
    private final Integer timeLimit;
    private final Integer maxProcessingTime;
    private final Integer minProcessingTime;
    private final Integer minArrivalTime;
    private final Integer maxArrivalTime;
    private final Integer numberOfServers;
    private final Integer numberOfClients;

    public SimulationManager(ArrayList<Integer> inputData, SelectionPolicy selectionPolicy, BackPanel backPanel) {
        this.backPanel = backPanel;
        this.peakHour = this.clientsAtPeakHour = 0;
        this.totalServiceTime = 0;
        this.numberOfClients = inputData.getFirst();
        this.numberOfServers = inputData.get(1);
        this.timeLimit = inputData.get(2);
        this.minArrivalTime = inputData.get(3);
        this.maxArrivalTime = inputData.get(4);
        this.minProcessingTime = inputData.get(5);
        this.maxProcessingTime = inputData.get(6);
        this.scheduler = new Scheduler(numberOfServers);
        this.scheduler.changeStrategy(selectionPolicy);
        this.stateFile = initializeOutputFile();
    }

    private File initializeOutputFile() {
        if(this.stateFile != null)
            if(!this.stateFile.delete())
                System.out.println("<ERROR> stateOfServers.txt was not recreated");
        this.stateFile = new File("stateOfServers.txt");
        return stateFile;
    }

    @Override
    public synchronized void run() {
        this.stateFile = initializeOutputFile();
//        tasks = this.generatePredefinedTasks();
        tasks = this.generateRandomTasks(this.numberOfClients);
        this.printTasks(tasks);
        while(currentTime <= timeLimit && !noWork) {
            System.out.println("<DEBUG>Current time: " + currentTime);
            Integer numberOfLeftServers = numberOfServers;
            for (Task task : tasks) {
                if (task.getArrivalTime().compareTo(currentTime) <= 0) {
                    this.scheduler.dispatchTask(task);
                    if(!tasks.remove(task)){
                        System.out.println("<ERROR>Did not remove task: " + task);
                    }
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
            this.backPanel.updateContent(currentTime, tasks, scheduler.getServerList());
            if(currentTime > timeLimit){
                this.scheduler.forceStopServers();
            }
            if(tasks.isEmpty()) {
                noWork = this.scheduler.stopServers();
            }
        }
        this.printMetadata();
        System.out.println("<DEBUG>Simulation finished");
    }

    private void printMetadata() {
        //average time for finishing task + queue waiting divided by how many
        try {
            FileWriter fileOut = new FileWriter(stateFile, true);
            double x = 0.0;
            for (Server server : this.scheduler.getServerList())
                x += server.getWaitingPeriod().doubleValue();
            fileOut.write("Average overall waiting time: " + x / this.numberOfClients + "\n");
            fileOut.write("Average overall service time: " + (this.totalServiceTime / this.numberOfClients) + "\n");
            fileOut.write("Peak hour: " + this.peakHour + "\n");
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("WHERE FILE?");
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
            fileOut.write("Current time: " + currentTime + "\nWaiting people:\n");
            for (Task task : tasks) {
                fileOut.write( ">>" + task.toString() + "\n");
            }
            fileOut.write("\nState of servers:\n");
            int clientsPerHour = 0;
            for (Server server : this.scheduler.getServerList()) {
                clientsPerHour += server.getTaskQ().size();
                if(server.getTaskQ().isEmpty())
                    fileOut.write("server: <" + server.getServerName() + "> is closed\n");
                else
                    fileOut.write("server: <" + server.getServerName() + "> has amount of work: " + server.getTaskQ().size() + "\n");
                for (Task task : server.getTaskQ()) {
                    fileOut.write("\t" + task.toString() + "\n");
                }
            }
            if(clientsPerHour > clientsAtPeakHour) {
                this.clientsAtPeakHour = clientsPerHour;
                this.peakHour = currentTime;
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
            Integer aTime = random.nextInt(maxArrivalTime - minArrivalTime + 1);
            Integer sTime = random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;
            arrayList.add(new Task(i + 1, aTime, sTime));
            this.totalServiceTime += sTime;
        }
        return arrayList;
    }

    private BlockingQueue<Task> generatePredefinedTasks() {
        BlockingQueue<Task> arrayList = new LinkedBlockingQueue<>();
        arrayList.add(new Task(1, 1, 6));
        arrayList.add(new Task(2, 1, 3));
        arrayList.add(new Task(3, 1, 1));
        arrayList.add(new Task(4, 1, 2));
        return arrayList;
    }
}
