package org.game;

import org.game.Entity.Door;
import org.game.Entity.Exit;
import org.game.Entity.Obstacle;
import org.game.Entity.TrashCan;
import org.game.Entity.enemy.Dog;
import org.game.Entity.enemy.Enemy;
import org.game.Entity.item.Food;
import org.game.Entity.item.Item;
import org.game.Entity.item.Key;
import org.game.Entity.item.collar.SilverCollar;

import javax.swing.*;
import javax.xml.stream.events.EndElement;
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
    public List<Item> items;
    private Exit exit;
    public List<Enemy> enemies;

    //Displays
    private GameOverView loseDisplay;
    private GameWonView wonDisplay;
    //Keyboard management
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;

    private boolean objectInteraction;

    private boolean gameFinishWin;
    private boolean gameFinishLose;

    private boolean showingInventory;


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
        enemies = new ArrayList<>();

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
        content.add(new Food(150, 150));
        content.add(new SilverCollar());
        trashCans.add(new TrashCan(150, 150, content));
        items.add(new Key(300, 150, 2));
        enemies.add(new Dog(725, 250));
        exit = new Exit(1050, 200);

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
            player.moveLeft(this, obstacles, items, trashCans, doors, enemies, exit);
        }

        if (rightPressed) {
            player.moveRight(this, obstacles, items, trashCans, doors, enemies, exit);
        }

        if (upPressed) {
            player.moveUp(this, obstacles, items, trashCans, doors, enemies, exit);
        }

        if (downPressed) {
            player.moveDown(this, obstacles, items, trashCans, doors, enemies, exit);
        }
    }

    public void Win(){
        gameFinishWin = true;
    }

    public void Lose(){
        gameFinishLose = true;
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
        for( Item item: items){
            item.draw(g2d);
        }
        for (Enemy enemy:enemies){
            enemy.draw(g2d);
        }
        exit.draw(g2d);
        if(gameFinishWin){
            wonDisplay.draw(g2d);
        }
        if(gameFinishLose){
            loseDisplay.draw(g2d);
        }
        if (showingInventory) {
            List<Item> inventoryItems = player.inventory;
            int x = 10;
            int y = 50;
            for (Item item : inventoryItems) {
                Item i = item;
                i.x = x;
                i.y = y;
                i.draw(g2d);
                x += 50;
            }
        }
        //HEALTH PANEL
        int x_count = 20;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 550, 400, 50);
        for(int i = 0; i < player.health; i++){
            g2d.setColor(Color.RED);
            g2d.fillOval(x_count, 570, 20, 20);
            x_count += 25;
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
            case KeyEvent.VK_I:
                showingInventory = !showingInventory;
                repaint();


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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_E:
                objectInteraction = true;
                break;
        }
    }

}