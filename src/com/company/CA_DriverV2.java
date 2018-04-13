package com.company;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ConcurrentModificationException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class CA_DriverV2 extends JFrame{

    private static final Color white = Color.WHITE, black = Color.BLACK;

    private Board board;
    private JButton start_pause;

    public CA_DriverV2(){

        board = new Board();
        board.setBackground(white);

        start_pause = new JButton("Start");
        start_pause.addActionListener(board);

        this.add(board, BorderLayout.NORTH);
        this.add(start_pause, BorderLayout.SOUTH);
        //this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

    }

    public static void main(String args[]){
        new CA_DriverV2();
    }

    private class Board extends JPanel implements ActionListener{

        private final Dimension DEFAULT_SIZE = new Dimension(10, 10);
        private final int DEFAULT_CELL = 10, DEFAULT_INTERVAL = 100, DEFAULT_RATIO = 50;

        private Dimension board_size;
        private int cell_size, interval, fill_ratio;
        private boolean run;
        private Timer timer;

        private Color[][] grid;

        public Board(){
            board_size = DEFAULT_SIZE;
            cell_size = DEFAULT_CELL;
            interval = DEFAULT_INTERVAL;
            fill_ratio = DEFAULT_RATIO;
            run = false;

            //Initialize grid with random values
            //NOTE: Add JOptionPane for option to define fill rate and board size?
            //ALT: Have a resize(int h, int w) method to resize grid when needed.
            //ALT: Have refill(int r) method to restart with different fill ratio.
            grid = new Color[board_size.height + 1][board_size.width + 1];
            for (int h = 0; h < board_size.height; h++)
                for (int w = 0; w < board_size.width; w++){
                    int r = (int)(Math.random() * 100);
                    if (r >= fill_ratio)
                        grid[h][w] = black;
                    else grid[h][w] = white;
                }

            timer = new Timer(interval, this);
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(board_size.height * cell_size, board_size.width * cell_size);
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            for (int h = 0; h < board_size.height; h++)
                for (int w = 0; w < board_size.width; w++){
                    try{
                        if (grid[h][w] == black)
                            g.setColor(black);
                        else g.setColor(white);
                        g.fillRect(h * cell_size, w * cell_size, cell_size, cell_size);
                    } catch (ConcurrentModificationException cme){}
                }
        }

        public void actionPerformed(ActionEvent e) {

            //Timer tick processing
            if (e.getSource().equals(timer)){
                repaint();
                Color[][] newGrid = new Color[board_size.height][board_size.width];
                for (int h = 0; h < board_size.height; h++)
                    for (int w = 0; w < board_size.width; w++) {
                        int surrounding = 0;
                        //Count black neighbors
                        for (int i = -1; i <= 1; i++)
                            for (int j = -1; j <= 1; j++)
                                if((h + i >= 0) && (h + i < board_size.height)
                                        && (w + j >= 0) && (w + j < board_size.width)
                                        && !(i == 0 && j == 0) && (grid[h + i][w + j] == black))
                                    surrounding++;

                        /*                      //Generate next iteration                                       */
                        if (grid[h][w] == black){
                            if (surrounding == 2 || surrounding == 3)
                                newGrid[h][w] = black;
                            else newGrid[h][w] = white;
                        }
                        else if(surrounding == 3)
                            newGrid[h][w] = black;
                    }
                for (int h = 0; h < board_size.height; h++){
                    for (int w = 0; w < board_size.width; w++){
                        grid[h][w] = newGrid[h][w];
//                      if (grid[h][w] == black)
//                          System.out.print("b ");
//                      else System.out.print("w ");
                    }
                    //System.out.println();
                }
                //System.out.println();

            }

            //Start-Pause button processing
            else if(e.getSource().equals(start_pause)){
                if(run){
                    timer.stop();
                    start_pause.setText("Start");
                }
                else {
                    timer.restart();
                    start_pause.setText("Pause");
                }
                run = !run;

            }
        }
    }
}