package org.example.business.logic;

public class MyTask {
    private Integer id;
    private Integer arrivalTime;
    private Integer serviceTime;

    public MyTask(Integer id, Integer arrivalTime, Integer serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    @Override
    synchronized public String toString() {
        return this.id + " -> (arrT: " + this.arrivalTime + "; servT: " + this.serviceTime + ")";
    }

    synchronized public Integer getId() {
        return id;
    }

    synchronized public Integer getArrivalTime() {
        return arrivalTime;
    }

    synchronized public Integer getServiceTime() {
        return serviceTime;
    }

    synchronized public void decrementServiceTime() {
        this.serviceTime -= 1;
    }
}
