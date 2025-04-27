package com.ftc.robotics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameOfLifeProject extends JPanel implements MouseListener, ActionListener, KeyListener {

    int rows = 800;
    int cols = 800;
    int pxp = 10;
    boolean[][] grid = new boolean[rows][cols];
    int[][] neighbors = {
            {-1, -1},
            {-1, 0},
            {-1, 1},
            {0, -1},
            {0, 1},
            {1, -1},
            {1, 0},
            {1, 1}
    };
    Timer timer;
    boolean sbPressed = false;

    public GameOfLifeProject() {
        System.out.println("NewGameOfLife constructor Called");
        setPreferredSize(new Dimension(cols * pxp, rows * pxp));
        addKeyListener(this);
        addMouseListener(this);
        setBackground(Color.BLACK);
        setFocusable(true);
        timer = new Timer(200, this); // Timer in milliseconds
        timer.start();
    }

    private int countNeighbors(int row, int col) {
        int count = 0;
        for (int i = 0; i <= 7; i++) {
            int r = row + neighbors[i][0];
            int c = col + neighbors[i][1];
            if (r >= 0 && r < rows && c >= 0 && c < cols && grid[r][c]) {
                count++;
            }
        }
        return count;
    }

    private void refreshGrid() {
        boolean[][] nextGrid = new boolean[rows][cols];
        System.out.println("Refresh Grid Called");
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int neighbors = countNeighbors(y, x);
                if (grid[y][x]) {
                    if (neighbors == 2 || neighbors == 3) {
                        nextGrid[y][x] = true;
                    } else {
                        nextGrid[y][x] = false;
                    }
                } else {
                    if (neighbors == 3){
                        nextGrid[y][x] = true;
                    } else {
                        nextGrid[y][x] = false;
                    }
                }
            }
        }
        grid = nextGrid;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("PaintComponent Called");
        super.paintComponent(g);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (grid[y][x]) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(x * pxp, y * pxp, pxp, pxp);

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ActionPerformed Called");
        refreshGrid();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Click Called");
        int x = e.getX() / pxp;
        int y = e.getY() / pxp;
        if (x >= 0 && x < cols && y >= 0 && y < rows) {
            grid[y][x] = !grid[y][x];
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("Spacebar was pressed!");
            sbPressed = !sbPressed;
            if (sbPressed) {
                timer.stop();
                System.out.println("Timer Paused.");
            }else {
                timer.start();
                System.out.println("Timer Started.");
            }
        }
    }


    @Override
    public void keyReleased (KeyEvent e){
    }


    public static void main (String[] args){
        JFrame frame = new JFrame("Conway's Game of Life Project");
        GameOfLifeProject game = new GameOfLifeProject();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


