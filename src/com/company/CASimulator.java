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
            gc.setFill(Color.BLUE);
            gc.fillRect(particle.xCell * (W / cellsX), particle.yCell * (H / cellsY), W / cellsX, H / cellsY);
        }
    }

    public void clearCanvas() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);
    }

    public void simulateOne1DStep() {
        for (Particle p : particles) {
            int chance = rand.nextInt(100) + 1;
            if (chance < 25) {
                p.xCell++;
            } else if (chance < 50) {
                p.xCell--;
            }
        }
    }

    public void simulateOne2DStep() {
        for (Particle p : particles) {
            int chance = rand.nextInt(10000) + 1;

            // 20% chance for the outer ring
            // 2up
            if (chance < 250) {
                p.xCell += 0;
                p.yCell += -2;
                // up right
            } else if (chance < 500) {
                p.xCell += 1;
                p.yCell += -1;
                // 2right
            } else if (chance < 750) {
                p.xCell += 2;
                p.yCell += 0;
            }
            // down right
            else if (chance < 1000) {
                p.xCell += 1;
                p.yCell += 1;
            }
            // 2down
            else if (chance < 1250) {
                p.xCell += 0;
                p.yCell += 2;
            }
            // down left
            else if (chance < 1500) {
                p.xCell += -1;
                p.yCell += 1;
            }
            // 2left
            else if (chance < 1750) {
                p.xCell += -2;
                p.yCell += 0;
                // up left
            } else if (chance < 2000) {
                p.xCell += -1;
                p.yCell += -1;
                // 30% chance for the inner ring
                // up
            } else if (chance < 2750) {
                p.xCell += 0;
                p.yCell += -1;
                // right
            } else if (chance < 3500) {
                p.xCell += 1;
                p.yCell += 0;
                // down
            } else if (chance < 4250) {
                p.xCell += 0;
                p.yCell += 1;
                // left
            } else if (chance < 5000) {
                p.xCell += -1;
                p.yCell += 0;
                // 50% chance to not move
            } else if (chance < 10000) {
                // stay still
            }
        }
    }
}