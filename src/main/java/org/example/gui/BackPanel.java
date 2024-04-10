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
        this.setBackground(new Color(32, 42, 68));
    }

    public void createContent(Integer numberOfPeople, Integer numberOfQueues) {
        this.m = 1 + numberOfPeople;
        this.n = 2 + numberOfQueues;
        if(this.n > 30)
            this.n = 32;
        if(this.m > 20)
            this.m = 21;
        this.panelHolder = new JPanel[n][m];

        this.setLayout(new GridLayout(n, m, 4, 2));
        panelHolder[0][0] = this.createTimePanel(0);
        for(int j = 1; j < m; j++) {
            panelHolder[0][j] = new JPanel();
            panelHolder[0][j].setOpaque(false);
        }

        for(int j = 0; j < m; j++)
            panelHolder[1][j] = new PPanel(j + 1);
        for(int i = 2; i < n; i++) {
            panelHolder[i][0] = new QPanel();
            for (int j = 1; j < m; j++) {
                panelHolder[i][j] = new EPanel();
            }
        }
        this.publicChanges();
    }

    public void updateContent(Integer currentTime, BlockingQueue<Task> waitingQ, ArrayList<Server> servers) {
        this.removeComponents();
        boolean care = (waitingQ.size() <= 30);

        panelHolder[0][0] = this.createTimePanel(currentTime);
        for (int j = 1; j < this.m; j++) {
            panelHolder[0][j] = new JPanel();
            panelHolder[0][j].setOpaque(false);
        }

        if(!care) {
            for (int j = 0; j < this.m; j++)
                panelHolder[1][j] = new PPanel(j + 1);
        }
        else {
            for (int j = 0; j < waitingQ.size()  && j < this.m; j++)
                panelHolder[1][j] = new PPanel(j + 1);
            for(int j = waitingQ.size(); j < this.m; j++)
                panelHolder[1][j] = new EPanel();
        }

        for (int i = 2; i < n; i++) {
            panelHolder[i][0] = new QPanel();
            for (int j = 0; j < servers.get(i - 2).getTaskQ().size(); j++)
                panelHolder[i][j + 1] = new PPanel(-1);
            for (int j = servers.get(i - 2).getTaskQ().size(); j + 1 < m; j++)
                panelHolder[i][j + 1] = new EPanel();
        }
        this.publicChanges();
    }

    private JPanel createTimePanel(Integer time) {
        JPanel timePanel = new JPanel();
        timePanel.setOpaque(false);
        JLabel label = new JLabel(time.toString());
        label.setForeground(Color.WHITE);
        timePanel.add(label);
        return timePanel;
    }

    private void publicChanges() {
        for(int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                add(panelHolder[i][j]);
        this.revalidate();
        this.repaint();
    }

    private void removeComponents() {
        this.removeAll();
    }

    private Integer n;
    private Integer m;
    private JPanel[][] panelHolder;
}
