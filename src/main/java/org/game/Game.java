package org.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.enemy.Enemy;
import org.game.entity.item.Item;
import org.game.entity.Background;
import org.game.entity.TrashCan;
import org.game.entity.Door;
import org.game.entity.Exit;
import org.game.entity.Obstacle;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.logging.Level;

/**
 * The main class of game management.
 */
@Log
@Getter
@Setter
public class Game extends JPanel implements Runnable, KeyListener {

    /**
     * The width of the game window.
     */
    public static final int WIDTH = 1200;

    /**
     * The height of the game window.
     */
    public static final int HEIGHT = 600;

    /**
     * The starting X coordinate of the player.
     */
    public static final int START_PLAYER_X = 100;

    /**
     * The starting Y coordinate of the player.
     */
    public static final int START_PLAYER_Y = 30;

    /**
     * Threads pause.
     */
    private static final int SLEEP_TIME = 10;

    /**
     * The thread responsible for executing the task.
     */
    private transient Thread thread;

    /**
     * A flag indicating whether the task is currently running.
     */
    private boolean running;
    /**
     * The player object representing the main character of the game.
     */
    private Player player;

    /**
     * The list of background objects in the game map.
     */
    private List<Background> backgrounds;

    /**
     * The list of obstacle objects in the game map.
     */
    private List<Obstacle> obstacles;

    /**
     * The list of door objects in the game map.
     */
    private List<Door> doors;

    /**
     * The list of trash can objects in the game map.
     */
    private List<TrashCan> trashCans;

    /**
     * The list of item objects in the game map.
     */
    private List<Item> items;

    /**
     * The list of enemy objects in the game map.
     */
    private List<Enemy> enemies;

    /**
     * The exit object representing the exit point of the game map.
     */
    private Exit exit;
    /**
     * The view displayed when the game is lost.
     */
    private final GameOverView loseDisplay;

    /**
     * The view displayed when the game is won.
     */
    private final GameWonView wonDisplay;

    /**
     * A flag indicating whether the left arrow key is pressed.
     */
    private boolean leftPressed;

    /**
     * A flag indicating whether the right arrow key is pressed.
     */
    private boolean rightPressed;

    /**
     * A flag indicating whether the up arrow key is pressed.
     */
    private boolean upPressed;

    /**
     * A flag indicating whether the down arrow key is pressed.
     */
    private boolean downPressed;

    /**
     * A flag indicating whether the game is finished with a win.
     */
    private boolean gameFinishWin;

    /**
     * A flag indicating whether the game is finished with a loss.
     */
    private boolean gameFinishLose;

    /**
     * The interface bar object that handles the game's interface.
     */
    private static InterfaceBar interfaceBar = new InterfaceBar();
    /**
     * A boolean flag indicating whether the inventory is currently being shown.
     */
    private boolean showingInventory;

    /**
     * Set screen settings, add player and obstacles.
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
     * Starts the game thread.
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
     * Stops the game thread.
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
     * Updates the game state.
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
     * Sets the game state to win.
     */
    public void winGame() {
        gameFinishWin = true;
        log.log(Level.FINE, "Player won the game");
    }

    /**
     * Sets the game state to lose.
     */
    public void loseGame() {
        gameFinishLose = true;
        log.log(Level.FINE, "Player lost the game");
    }

    @Override
    public void paintComponent(final Graphics g) {
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
    public void keyTyped(final KeyEvent e) {
        // Not implemented
    }

    @Override
    public void keyPressed(final KeyEvent e) {
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
    public void keyReleased(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_UP, KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> downPressed = false;
            default -> log.log(Level.INFO, "Player released wrong key");
        }
    }
}
