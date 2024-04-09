package org.example.gui;

import org.example.businesslogic.Server;
import org.example.businesslogic.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class BackPanel extends JPanel {
    public BackPanel(Integer width, Integer height) {
        super();
        this.setSize(width, height);
    }
    public void createContent(Integer numberOfPeople, Integer numberOfQueues) {
        this.n = 1 + numberOfPeople;
        this.m = 1 + numberOfQueues;
        this.panelHolder = new JPanel[n + 1][m + 1];
        this.setLayout(new GridLayout(n, m));
        for(int j = 0; j < m; j++)
            panelHolder[0][j] = new Ppanel(j + 1);
        for(int i = 1; i < n; i++) {
            panelHolder[i][0] = new QPanel();
            for (int j = 1; j < m; j++) {
                panelHolder[i][j] = new EPanel();
            }
        }
        this.publicChanges();
    }
    public void updateContent(Integer currentTime, BlockingQueue<Task> waitingQ, ArrayList<Server> servers) {

    }

    private void publicChanges() {
//        for (Component comp : this.getComponents()) {
//            this.remove(comp);
//        }
        for(int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                add(panelHolder[i][j]);
    }

    private Integer n;
    private Integer m;
    private JPanel[][] panelHolder;
}
