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

        W = 500;
        H = 500;
        cellsX = 100;
        cellsY = 1;
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

        simulator.addParticle(50, 0);

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                simulator.drawAllParticles();
                simulator.simulateOneStep();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }
        }.start();
        System.out.println("Done");

    }
}
