package org.game;

import org.game.Entity.Obstacle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel implements Runnable, KeyListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Thread thread;
    private boolean running;

    private Player player;
    private List<Obstacle> obstacles;

    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;

    private Rectangle obstacle = new Rectangle(500, 300, 100, 50);


    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        player = new Player(WIDTH / 2, HEIGHT / 2);

        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(100, 100, 50, 200));
        obstacles.add(new Obstacle(500, 300, 100, 50));
    }

    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            update();
            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void update() {
        if (leftPressed && !player.collidesWith(obstacle)) {
            player.moveLeft(obstacles);
        }

        if (rightPressed && !player.collidesWith(obstacle)) {
            player.moveRight();
        }

        if (upPressed && !player.collidesWith(obstacle)) {
            player.moveUp();
        }

        if (downPressed && !player.collidesWith(obstacle)) {
            player.moveDown();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        player.draw(g2d);
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g2d);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}