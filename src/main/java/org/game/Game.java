package org.game;

import org.game.Entity.Door;
import org.game.Entity.Obstacle;
import org.game.Entity.TrashCan;
import org.game.Entity.item.Item;
import org.game.Entity.item.Key;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class of game management
 */
public class Game extends JPanel implements Runnable, KeyListener {

    //Display size
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    private Thread thread;
    private boolean running;

    private Player player;

    //Objects of Map
    private List<Obstacle> obstacles;
    private List<Door> doors;
    private List<TrashCan> trashCans;
    private List<Item> items;

    //Displays
    private GameOverView loseDisplay;
    private GameWonView wonDisplay;
    //Keyboard management
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;
    //Sticky Block
//    private Rectangle obstacle = new Rectangle(500, 300, 100, 50);


    /*
     * Set screen settings, add player and obstacles
     */
    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        player = new Player(WIDTH / 2, HEIGHT / 2);

        //Objects on Map
        obstacles = new ArrayList<>();
        doors = new ArrayList<>();
        trashCans = new ArrayList<>();
        items = new ArrayList<>();

        //|
        obstacles.add(new Obstacle(0, 0, 50, 600));
        //|
        obstacles.add(new Obstacle(0, 200, 200, 300));
        //|
        obstacles.add(new Obstacle(500, 0, 200, 300));
        //|
        obstacles.add(new Obstacle(800, 200, 200, 400));
        //------------------------------------------     -------------
        obstacles.add(new Obstacle(0, 0, 700, 25));
        obstacles.add(new Obstacle(200, 0, 500, 100));
        obstacles.add(new Obstacle(800, 0, 400, 50));

        //------------------------------------------     --------------
        obstacles.add(new Obstacle(100, 100, 600, 25));
        obstacles.add(new Obstacle(800, 100, 400, 25));
        //-------------------------------------------------------------
        obstacles.add(new Obstacle(0, 550, 1200, 50));
        //             ---------------------------
        obstacles.add(new Obstacle(300, 450, 600, 100));

        doors.add(new Door(700,200, 1));
        List<Item> content = new ArrayList<>();
        content.add(new Key(150, 150, 1));
        trashCans.add(new TrashCan(150, 150, content));

        //Displays
        loseDisplay = new GameOverView();
        wonDisplay = new GameWonView();
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
        //if .. && !obstacle Sticky Block
        if (leftPressed) {
            player.moveLeft(obstacles);
        }

        if (rightPressed) {
            player.moveRight(obstacles);
        }

        if (upPressed) {
            player.moveUp(obstacles);
        }

        if (downPressed) {
            player.moveDown(obstacles);
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
        for (Door door:doors){
            door.draw(g2d);
        }
        for (TrashCan trashCan: trashCans){
            trashCan.draw(g2d);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_S:
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
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}