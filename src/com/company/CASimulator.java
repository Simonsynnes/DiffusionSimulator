package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

import java.util.ArrayList;

public class CASimulator {

    private int W;
    private int H;
    private int cellsX;
    private int cellsY;
    private ArrayList<Particle> particles;

    private GraphicsContext gc;

    private Random rand;

    public CASimulator(GraphicsContext gc, int W, int H, int cellsX, int cellsY) {

        this.gc = gc;
        this.W = W;
        this.H = H;
        this.cellsX = cellsX;
        this.cellsY = cellsY;

        particles = new ArrayList<>();
        rand = new Random();
    }

    public void addParticle(int x, int y) {
        particles.add(new Particle(x, y));
    }

    public void drawAllParticles() {
        clearCanvas();
        for (Particle particle : particles) {
            gc.setFill(Color.WHITE);
            gc.fillRect(particle.xCell * (W / cellsX), particle.yCell * (H / cellsY), W / cellsX, H / cellsY);
        }
    }

    public void clearCanvas() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);
    }

    public void simulateOneStep(int countSteps)
    {
        for (Particle p : particles)
        {
            int chance = rand.nextInt(100) + 1;

            if (chance < 25)
            {
                p.xCell++;

                p.distanceFromOrigo++;
            }
            else if (chance < 50)
            {
                p.xCell--;

                p.distanceFromOrigo--;
            }

            printAverageDistanceFromOrigo(countSteps);
        }
    }

    public void printAverageDistanceFromOrigo(int step)
    {
        int totalDistance = 0;

        for (Particle p : particles)
        {
            totalDistance += Math.sqrt(p.distanceFromOrigo * p.distanceFromOrigo);
        }

        System.out.println("");
        System.out.println("STEP:" + step + ", average distance: " + (totalDistance / particles.size()));
    }
}