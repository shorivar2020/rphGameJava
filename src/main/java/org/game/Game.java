package org.game;

import org.game.Entity.*;
import org.game.Entity.enemy.Dog;
import org.game.Entity.enemy.Enemy;
import org.game.Entity.enemy.Rat;
import org.game.Entity.item.Food;
import org.game.Entity.item.Item;
import org.game.Entity.item.Key;
import org.game.Entity.item.collar.BasicCollar;
import org.game.Entity.item.collar.Collar;
import org.game.Entity.item.collar.GoldCollar;
import org.game.Entity.item.collar.SilverCollar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class of game management
 */
public class Game extends JPanel implements Runnable, KeyListener, Serializable {

    //Display size
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    private Thread thread;
    private boolean running;

    private Player player;


    //Objects of Map
    private List<Background> backgrounds;
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
        ArrayList<Item> load_inv = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("items.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            load_inv = (ArrayList<Item>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Список объектов загружен из файла:");
            if(load_inv!= null){
//                for (Item item : load_inv) {
//                    System.out.println(item.x + " " + item.y + " " + item.name);
//                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        player = new Player(WIDTH / 2, HEIGHT / 2, load_inv);

//        System.out.print("AAAA" + player.inventory);

        //Objects on Map
        backgrounds = new ArrayList<>();
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
                if (map[j][i] == 0){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, true, false, false, false));
                }
                if (map[j][i] == 'g'){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, true, false, false, false, false));
                }
                if (map[j][i] == 'f'){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, true, false, false));
                }
                if (map[j][i] == 1){
                    obstacles.add(new Obstacle(x_loc,y_loc,30,30, true, false));
                }
                if (map[j][i] == 'w'){
                    obstacles.add(new Obstacle(x_loc,y_loc,30,30, false, true));
                }
                if(map[j][i] == 2){
                    List<Item> c = new ArrayList<>();
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, true, false, false, false, false));
                    trashCans.add(new TrashCan(x_loc, y_loc, c));
                    trash_count++;
                }
                if(map[j][i] == 3){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, true, false, false, false));
                    enemies.add(new Dog(x_loc, y_loc));
                }
                if(map[j][i] == 4){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, true, false, false, false));
                    enemies.add(new Rat(x_loc, y_loc));
                }
                if(map[j][i] == 5){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, true, false, false));
                    exit = new Exit(x_loc, y_loc);
                }
                if(map[j][i] == 6){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, true, false, false, false));
                    doors.add(new Door(x_loc, y_loc, 0, false));
                    door_count++;
                }
                if(map[j][i] == 7){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, true, false, false, false));
                    doors.add(new Door(x_loc, y_loc, 0, true));
                    door_count++;
                }
                if(map[j][i] == 8){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, true, false, false));
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, false, false, true));
                    items.add(new SilverCollar(x_loc, y_loc));
                }
                if(map[j][i] == 9){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, true, false, false));

                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, false, false, true));
                    items.add(new GoldCollar(x_loc, y_loc));
                }
                if(map[j][i] == 10){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, false, true, false));

                    items.add(new Food(x_loc, y_loc));
                }
                x_loc += 30;
            }

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
        System.out.print("WINNER");
        gameFinishWin = true;
        List<Item> itList = new ArrayList<>();
        itList.add(new BasicCollar(0, 0));
        try {
            FileOutputStream fileOut = new FileOutputStream("items.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(itList);
            out.close();
            fileOut.close();
            System.out.println("Список объектов сохранен в файл");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Lose(){
        gameFinishLose = true;
        try {
            FileOutputStream fileOut = new FileOutputStream("items.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(player.inventory);
            out.close();
            fileOut.close();
            System.out.println("Список объектов сохранен в файл");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        BufferedImage imageInv;
        try {
            imageInv = ImageIO.read(getClass().getResourceAsStream("/inv_bar.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (showingInventory) {
            g2d.drawImage(imageInv, 0, 0, null);
            List<Item> inventoryItems = player.inventory;
            int x = 10;
            int y = 10;
            for (Item item : inventoryItems) {
                item.x = x;
                item.y = y;
                item.draw(g2d);
                x += 30;
                if(item instanceof Collar){
                    if(item instanceof SilverCollar){
                        ((SilverCollar) item).x_c = x;
                        ((SilverCollar) item).y_c = y;
                    }
                    if(item instanceof GoldCollar){
                        ((GoldCollar) item).x_c = x;
                        ((GoldCollar) item).y_c = y;
                    }
                    x += 30;
                }
            }
        }
        //HEALTH PANEL
        int x_count = 20;
//        g2d.setColor(Color.BLACK);
//        g2d.fillRect(0, 550, 400, 50);
        BufferedImage imageHeart;
        BufferedImage imageBar;
        try {
            imageHeart = ImageIO.read(getClass().getResourceAsStream("/heart.png"));
            imageBar = ImageIO.read(getClass().getResourceAsStream("/health_bar.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2d.drawImage(imageBar, 0, 565, null);
        for(int i = 0; i < player.health; i++){
            g2d.drawImage(imageHeart, x_count, 570, null);
            x_count += 25;
        }

            for(Enemy enemy: enemies){
                enemy.move(10 , 0);
            }
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