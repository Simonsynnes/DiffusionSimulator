package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class CAMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private int W;
    private int H;
    private int cellsX;
    private int cellsY;
    private int step;

    private CASimulator simulator;

    @Override
    public void start(Stage primaryStage) {

        W = 1000;
        H = 800;
        cellsX = 1000;
        cellsY = 800;
        step = 0;

        Group root = new Group();
        Scene scene = new Scene(root, W, H, Color.BLACK);

        final Canvas canvas = new Canvas(W, H);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        simulator = new CASimulator(gc, W, H, cellsX, cellsY);

        root.getChildren().add(canvas);

        primaryStage.setTitle("JavaFX Scene Graph Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        for (int i = 0; i <= 20; i++) {
            for(int j = 0; j <= 50; j++)
            simulator.addParticle(i*10 + 400, j*4 + 300);
        }

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                simulator.drawAllParticles();
                simulator.simulateOne2DStep();

                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }
        }.start();
        System.out.println("Done");

    }
}
