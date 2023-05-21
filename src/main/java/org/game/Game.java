package org.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.*;
import org.game.entity.enemy.Enemy;
import org.game.entity.item.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.List;
import java.util.logging.Level;

/**
 * The main class of game management
 */
@Log
@Getter
@Setter
public class Game extends JPanel implements Runnable, KeyListener, Serializable {

    // Display size
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;
    private static final int START_PLAYER_X = 100;
    private static final int START_PLAYER_Y = 30;

    // Threads pause
    private static final int SLEEP_TIME = 10;

    // Threads
    private transient Thread thread;
    private boolean running;

    private Player player;

    // Objects of Map
    private List<Background> backgrounds;
    private List<Obstacle> obstacles;
    private List<Door> doors;
    private List<TrashCan> trashCans;
    private List<Item> items;
    private List<Enemy> enemies;
    private Exit exit;

    // Displays
    private final GameOverView loseDisplay;
    private final GameWonView wonDisplay;

    // Keyboard management
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;

    // Game finish flags
    private boolean gameFinishWin;
    private boolean gameFinishLose;

    // Interface management
    private static final InterfaceBar interfaceBar = new InterfaceBar();
    private boolean showingInventory;

    /*
     * Set screen settings, add player and obstacles
     */
    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        player = new Player(START_PLAYER_X, START_PLAYER_Y);

        // Map management
        MapLoader mapLoader = new MapLoader();
        mapLoader.getMapFromFile();
        backgrounds = mapLoader.getBackgrounds();
        obstacles = mapLoader.getObstacles();
        doors = mapLoader.getDoors();
        trashCans = mapLoader.getTrashCans();
        items = mapLoader.getItems();
        enemies = mapLoader.getEnemies();
        exit = mapLoader.getExit();

        // Displays
        loseDisplay = new GameOverView();
        wonDisplay = new GameWonView();
    }

    /**
     * Starts the game thread
     */
    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops the game thread
     */
    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (running) {
            update();
            repaint();
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                log.log(Level.WARNING, "Thread was interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Updates the game state
     */
    private void update() {
        if (leftPressed) {
            player.checkItemCollision(this, items, trashCans, enemies);
            player.moveLeft(this, obstacles, doors, exit);
        }
        if (rightPressed) {
            player.checkItemCollision(this, items, trashCans, enemies);
            player.moveRight(this, obstacles, doors, exit);
        }
        if (upPressed) {
            player.checkItemCollision(this, items, trashCans, enemies);
            player.moveUp(this, obstacles, doors, exit);
        }
        if (downPressed) {
            player.checkItemCollision(this, items, trashCans, enemies);
            player.moveDown(this, obstacles, doors, exit);
        }
        for (Enemy enemy : enemies) {
            enemy.move();
        }
    }

    /**
     * Sets the game state to "win" and logs the event
     */
    public void winGame() {
        gameFinishWin = true;
        log.log(Level.FINE, "Player won the game");
    }

    /**
     * Sets the game state to "lose" and logs the event
     */
    public void loseGame() {
        gameFinishLose = true;
        log.log(Level.FINE, "Player lost the game");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        for (Background background : backgrounds) {
            background.draw(g2d);
        }
        for (TrashCan trashCan : trashCans) {
            trashCan.draw(g2d);
        }
        exit.draw(g2d);
        player.draw(g2d);
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g2d);
        }
        for (Door door : doors) {
            door.draw(g2d);
        }
        for (Item item : items) {
            item.draw(g2d);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(g2d);
        }

        interfaceBar.inventoryBar(g2d, showingInventory, player);

        if (gameFinishWin) {
            wonDisplay.draw(g2d);
            stop();
        }
        if (gameFinishLose) {
            loseDisplay.draw(g2d);
            stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not implemented
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_UP, KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_I -> {
                showingInventory = !showingInventory;
                repaint();
            }
            default -> log.log(Level.INFO, "Player pressed wrong key");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_UP, KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> downPressed = false;
            default -> log.log(Level.INFO, "Player released wrong key");
        }
    }
}
