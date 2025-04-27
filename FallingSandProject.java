package com.ftc.robotics;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FallingSandProject extends JPanel implements ActionListener, MouseListener {
    char[][] grid;
    Timer timer;
    Random random = new Random();
    int[][] neighbors = {
            {-1, -1},
            {-1, 0},
            {-1, 1},
            {0, -1},
            {0,0},
            {0, 1},
            {1, -1},
            {1, 0},
            {1, 1}
    };

    int width = 400;
    int height = 300;
    int pxp = 2; // Item size

    public FallingSandProject() {
        setPreferredSize(new Dimension(width * pxp, height * pxp));
        grid = new char[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = 'e';
            }
        }

        addMouseListener(this);
        timer = new Timer(1, this);
        timer.start();
    }


    private void applySandRules(int x, int y) {
        // Sand falls straight down if empty
        int blY = y + 1;
        if (blY < height && grid[blY][x] == 'e') {
            grid[y][x] = 'e';
            grid[blY][x] = 's';
        }
        // Otherwise, try to fall diagonally left or right
        else {
            int dir=0;
            if (random.nextBoolean()){
                dir=-1;
            }else{
                dir=1;
            }
            int nX = x + dir;
            if (nX >= 0 && nX < width && blY < height && grid[blY][nX] == 'e') {
                grid[y][x] = 'e';
                grid[blY][nX] = 's';
            }
        }
    }

    private void applyWaterRules(int x, int y) {
        int blY = y + 1;
        if (blY < height && grid[blY][x] == 'e') { // Falls down
            grid[y][x] = 'e';
            grid[blY][x] = 'w';
        } else {
            int dir = 0;
            if (random.nextBoolean()) {
                dir = -1;
            }else{
                dir = 1;
            }
            int nX = x + dir;
            if (nX >= 0 && nX < width && blY < height && grid[blY][nX] == 'e') { // Falls diagonally
                grid[y][x] = 'e';
                grid[blY][nX] = 'w';
            } else { // Moves side to side
                    if (random.nextBoolean()) {
                        if (x > 0 && grid[y][x - 1] == 'e') {
                            grid[y][x] = 'e';
                            grid[y][x - 1] = 'w';
                        } else if (x < width - 1 && grid[y][x + 1] == 'e') {
                            grid[y][x] = 'e';
                            grid[y][x + 1] = 'w';
                        }
                    } else {
                        if (x < width - 1 && grid[y][x + 1] == 'e') {
                            grid[y][x] = 'e';
                            grid[y][x + 1] = 'w';
                        } else if (x > 0 && grid[y][x - 1] == 'e') {
                            grid[y][x] = 'e';
                            grid[y][x - 1] = 'w';
                        }
                    }
                }
        }
    }

    private void refreshGrid() {
        List<Point> cellsToUpdate = new ArrayList<>();
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                cellsToUpdate.add(new Point(x, y));
            }
        }
        Collections.shuffle(cellsToUpdate); // Moves list positions around.
        int len= cellsToUpdate.size();
        for (int i = 0; i < len; i++) {
            int x = cellsToUpdate.get(i).x;
            int y = cellsToUpdate.get(i).y;

            if (grid[y][x] != 'e') { // Only update not empty cells
                if (grid[y][x] == 's'){
                    applySandRules(x,y);
                }
                else if (grid[y][x] == 'w'){
                    applyWaterRules(x,y);
                }
            }
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        refreshGrid();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / pxp;
        int y = e.getY() / pxp;
        if (x >= 0 && x < width && y >= 0 && y < height) {
            char item = 0;
            if (SwingUtilities.isLeftMouseButton(e)){
                item='w';
            }else{
                item='s';
            }
            for (int i = 0; i <= 8; i++) {
                int xLoc = x + neighbors[i][0];
                int yLoc = y + neighbors[i][1];
                if (xLoc >= 0 && xLoc < width && yLoc < height && yLoc >=0) {
                    grid[yLoc][xLoc] = item;
                }
            }
            repaint(); // Repaint after placing
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x] == 's') {
                    g.setColor(Color.YELLOW);
                } else if (grid[y][x] == 'w') {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(x * pxp, y * pxp, pxp, pxp);
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}


    public static void main(String[] args) {
        JFrame frame = new JFrame("Falling Sand Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new FallingSandProject());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

