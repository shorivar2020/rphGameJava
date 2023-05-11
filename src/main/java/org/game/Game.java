package org.game;

import org.game.Entity.Door;
import org.game.Entity.Exit;
import org.game.Entity.Obstacle;
import org.game.Entity.TrashCan;
import org.game.Entity.enemy.Dog;
import org.game.Entity.enemy.Enemy;
import org.game.Entity.enemy.Rat;
import org.game.Entity.item.Food;
import org.game.Entity.item.Item;
import org.game.Entity.item.Key;
import org.game.Entity.item.collar.Collar;
import org.game.Entity.item.collar.GoldCollar;
import org.game.Entity.item.collar.SilverCollar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
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

    private int move;

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
        char[][] map = new char[40][20];
        try {
            map = MapLoader.loadMap("src/main/resources/map.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        }
        int x_loc = 0, y_loc = 0;
        int door_count = 0;
        int trash_count = 0;
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 40; j++){
                if (map[j][i] == 1){
                    obstacles.add(new Obstacle(x_loc,y_loc,30,30));
                    System.out.print("1");
                }
                if(map[j][i] == 2){
                    List<Item> c = new ArrayList<>();
                    trashCans.add(new TrashCan(x_loc, y_loc, c));
                    trash_count++;
                }
                if(map[j][i] == 3){
                    enemies.add(new Dog(x_loc, y_loc));
                }
                if(map[j][i] == 4){
                    enemies.add(new Rat(x_loc, y_loc));
                }
                if(map[j][i] == 5){
                    exit = new Exit(x_loc, y_loc);
                }
                if(map[j][i] == 6){
                    doors.add(new Door(x_loc, y_loc, 90, 25, 0));
                    door_count++;
                }
                if(map[j][i] == 7){
                    doors.add(new Door(x_loc, y_loc, 25, 90, 0));
                    door_count++;
                }
                if(map[j][i] == 8){
                    items.add(new SilverCollar(x_loc, y_loc));
                }
                if(map[j][i] == 9){
                    items.add(new GoldCollar(x_loc, y_loc));
                }
                if(map[j][i] == 10){
                    items.add(new Food(x_loc, y_loc));
                }
                x_loc += 30;
            }

            System.out.print("\n");
            x_loc = 0;
            y_loc += 30;
        }
        if(door_count <= trash_count){
            for(int i = 0; i < door_count; i++){
                doors.get(i).setDoorNumber(i);
                trashCans.get(i).content.add(new Key(doors.get(i).x, doors.get(i).y, i));
            }
        }else{
            System.out.print("TOO MANY TRASHCANS");
        }
//        //|
//        obstacles.add(new Obstacle(0, 0, 50, 600));
//        //|
//        obstacles.add(new Obstacle(0, 200, 200, 300));
//        //|
//        obstacles.add(new Obstacle(500, 0, 200, 300));
//        //|
//        obstacles.add(new Obstacle(800, 200, 200, 400));
//        //------------------------------------------     -------------
//        obstacles.add(new Obstacle(0, 0, 700, 25));
//        obstacles.add(new Obstacle(200, 0, 500, 100));
//        obstacles.add(new Obstacle(800, 0, 400, 50));
//
//        //------------------------------------------     --------------
//        obstacles.add(new Obstacle(100, 100, 600, 25));
//        obstacles.add(new Obstacle(800, 100, 400, 25));
//        //-------------------------------------------------------------
//        obstacles.add(new Obstacle(0, 550, 1200, 50));
//        //             ---------------------------
//        obstacles.add(new Obstacle(300, 450, 600, 100));
//
//        doors.add(new Door(700,200, 1));
//        List<Item> content = new ArrayList<>();
//        content.add(new Key(150, 150, 1));
//        content.add(new Food(150, 150));
//        content.add(new SilverCollar());
//        trashCans.add(new TrashCan(150, 150, content));
//        items.add(new Key(300, 150, 2));
//        enemies.add(new Dog(725, 250));
//        enemies.add(new Rat(300, 250));
//        enemies.add(new Rat(350, 250));
//        enemies.add(new Rat(400, 250));
//        exit = new Exit(1050, 200);
//
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
                item.x = x;
                item.y = y;
                item.draw(g2d);
                x += 50;
                if(item instanceof Collar){
                    if(item instanceof SilverCollar){
                        ((SilverCollar) item).x_c = x;
                        ((SilverCollar) item).y_c = y;
                        x += 50;
                    }
                    if(item instanceof GoldCollar){
                        ((GoldCollar) item).x_c = x;
                        ((GoldCollar) item).y_c = y;
                        x += 50;
                    }
                }
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

            for(Enemy enemy: enemies){
                enemy.move(10 , 0);
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