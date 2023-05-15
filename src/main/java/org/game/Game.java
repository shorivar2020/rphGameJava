package org.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.*;
import org.game.entity.enemy.Enemy;
import org.game.entity.item.Item;
import org.game.entity.item.collar.BasicCollar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class of game management
 */
@Log
@Getter
@Setter
public class Game extends JPanel implements Runnable, KeyListener, Serializable {

    private static final int SLEEP_TIME = 10;
    //Display size
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    private transient Thread thread;
    private boolean running;

    private Player player;

    //Objects of Map
    private List<Background> backgrounds;
    private List<Obstacle> obstacles;
    private List<Door> doors;
    private List<TrashCan> trashCans;
    private List<Item> items;
    private List<Enemy> enemies;
    private final Exit exit;

    //Displays
    private final GameOverView loseDisplay;
    private final GameWonView wonDisplay;

    //Keyboard management
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;

    //Fin game flags
    private boolean gameFinishWin;
    private boolean gameFinishLose;

    //Interface management
    private static final InterfaceBar interfaceBar = new InterfaceBar();
    private boolean showingInventory;

    /*
     * Set screen settings, add player and obstacles
     */
    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        player = new Player(100, 30);

        //Objects on Map
        MapLoader mapLoader = new MapLoader();
        mapLoader.mapParser();
        backgrounds = mapLoader.getBackgrounds();
        obstacles = mapLoader.getObstacles();
        doors = mapLoader.getDoors();
        trashCans = mapLoader.getTrashCans();
        items = mapLoader.getItems();
        enemies = mapLoader.getEnemies();
        exit = mapLoader.getExit();
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
                log.warning("Interrupted!");
                Thread.currentThread().interrupt();
            }
        }
    }

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
        for(Enemy enemy: enemies){
            enemy.move();
        }
    }

    public void winGame(){
        gameFinishWin = true;
        List<Item> itList = new ArrayList<>();
        itList.add(new BasicCollar(0, 0));
        interfaceBar.saveItemsInFile(itList);
    }

    public void loseGame(){
        gameFinishLose = true;
        interfaceBar.saveItemsInFile(player.getInventory());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        for (Background background: backgrounds){
            background.draw(g2d);
        }
        for (TrashCan trashCan: trashCans){
            trashCan.draw(g2d);
        }
        player.draw(g2d);
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g2d);
        }
        for (Door door:doors){
            door.draw(g2d);
        }
        for( Item item: items){
            item.draw(g2d);
        }
        for (Enemy enemy:enemies){
            enemy.draw(g2d);
        }
        exit.draw(g2d);

        interfaceBar.inventoryBar(g2d, showingInventory, player);

        if(gameFinishWin){
            wonDisplay.draw(g2d);
            stop();
        }
        if(gameFinishLose){
            loseDisplay.draw(g2d);
            stop();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Not function been implemented
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
            default -> log.severe("WRONG KEY");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_UP, KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> downPressed = false;
            default -> log.severe("FALSE KEY");

        }
    }

}