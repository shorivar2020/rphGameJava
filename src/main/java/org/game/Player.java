package org.game;
import org.game.Entity.Door;
import org.game.Entity.Exit;
import org.game.Entity.Obstacle;
import org.game.Entity.TrashCan;
import org.game.Entity.enemy.Enemy;
import org.game.Entity.item.Food;
import org.game.Entity.item.Item;
import org.game.Entity.item.Key;
import org.game.Entity.item.collar.Collar;
import org.game.Entity.item.collar.GoldCollar;
import org.game.Entity.item.collar.SilverCollar;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * The player class is controlled through the keyboards by the user.
 * Moves around the map. Interacts with objects.
 */
public class Player implements Serializable {

    public int health;
    private int damage;
    private static final int START_HEALTH = 9;
    private static final int START_DAMAGE = 5;
    public static final int SIZE = 50;
    public static final int SPEED = 5;

    private int x;
    private int y;
    int move_count = 0;

    private boolean leftCollision;
    private boolean rightCollision;
    private boolean upCollision;
    private boolean downCollision;
    private boolean doorCollision;

    public List<Item> inventory;
    ImageIcon image;
    Rectangle newBounds;


    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        health = START_HEALTH;
        damage = START_DAMAGE;
        inventory = takeStartInventory();
        setImage(this);
    }


    private boolean checkObstacleCollision(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit, Rectangle newBounds) {
        leftCollision = false;
        rightCollision = false;
        upCollision = false;
        downCollision =false;
        doorCollision = false;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(newBounds)) {
                Rectangle intersection = obstacle.getBounds().intersection(newBounds);

                double width = intersection.getWidth();
                double height = intersection.getHeight();

                double dx = (newBounds.getCenterX() - obstacle.getBounds().getCenterX()) / width;
                double dy = (newBounds.getCenterY() - obstacle.getBounds().getCenterY()) / height;

                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx < 0) {
                       System.out.println("Right collision");
                       rightCollision = true;
                    } else {
                        System.out.println("Left collision");
                        leftCollision = true;
                    }
                } else {
                    if (dy < 0) {
                        System.out.println("Down collision");
                        downCollision = true;
                    } else {
                        System.out.println("UP collision");
                        upCollision = true;
                    }
                }

                return true;
            }
        }

        for (Door door: doors) {
            if (door.getBounds().intersects(newBounds)) {
                for (Item item: inventory){
                    if (item instanceof Key) {
                        door.unlock((Key) item);
                        if(!door.locked){
//                            inventory.remove(item);
                            return false;
                        }
                    }
                }
                if(door.locked){
                    doorCollision = true;
                    return true;
                }
            }
        }
        if(exit.getBounds().intersects(newBounds)){
            game.Win();
        }
        return false;
    }

    public boolean checkItemCollision(Game game, List<Item> items, List<TrashCan> trashCans, List<Enemy> enemies) {
        newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        for (Item item: items) {
            if (item instanceof Key) {
                if (((Key) item).getBounds().intersects(newBounds)){
//                   System.out.println("I FOUND KEY");
                    inventory.add(item);
                    game.items.remove(item);
                    return true;
                }
            }
            if (item instanceof Food) {
                if (((Food) item).getBounds().intersects(newBounds)){
//                    System.out.println("I FOUND FOOD");
                    inventory.add(item);
                    eat((Food) item);
                    game.items.remove(item);
                    return true;
                }
            }
            if (item instanceof SilverCollar) {
                if (((SilverCollar) item).getBounds().intersects(newBounds)){
//                    System.out.println("I FOUND COLLAR");
                    inventory.add(item);
                    wear((SilverCollar) item);
                    game.items.remove(item);
                    return true;
                }
            }
            if (item instanceof GoldCollar) {
                if (((GoldCollar) item).getBounds().intersects(newBounds)){
//                    System.out.println("I FOUND COLLAR");
                    inventory.add(item);
                    wear((GoldCollar) item);
                    game.items.remove(item);
                    return true;
                }
            }
        }
        for (TrashCan trashCan: trashCans) {
            if (trashCan.getBounds().intersects(newBounds)) {
//                System.out.println("FIND IN TRASH");
                for(Item item: trashCan.content){
                    inventory.add(item);
                    if(item instanceof Collar){
                        wear((Collar) item);
                    }
                    if(item instanceof Food){
                        eat((Food) item);
                    }
//                    System.out.println("I found NEW ITEM");
                }
//                System.out.println(inventory);
                trashCan.content.removeAll(inventory);
                return true;
            }
        }
        for(Enemy enemy: enemies){
            if ((enemy).getBounds().intersects(newBounds)){
                enemy.attack(this);
                if(!enemy.isAlive()){
                    game.enemies.remove(enemy);
                }
                if(health < 1){
                    game.Lose();
                }
                return true;
            }
        }

        return false;
    }


    public void moveLeft(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImageL(this);
//        checkItemCollision(game, items, trashCans, enemies, new Rectangle(x - SPEED, y, SIZE, SIZE));

        if (!checkObstacleCollision(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            x -= SPEED;
        }else{
            if (!leftCollision && !doorCollision){
                x -= SPEED;
            }
        }
    }

    public void moveRight(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImage(this);
//        checkItemCollision(game, items, trashCans, enemies, new Rectangle(x - SPEED, y, SIZE, SIZE));
        if (!checkObstacleCollision(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            x += SPEED;
        }else{
            if (!rightCollision) {
                x += SPEED;
            }
        }

    }

    public void moveUp(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImageB(this);
//        checkItemCollision(game, items, trashCans, enemies, new Rectangle(x - SPEED, y, SIZE, SIZE));
        if (!checkObstacleCollision(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            y -= SPEED;
        }else{
            if(!upCollision && !doorCollision){
                y -= SPEED;
            }
        }
    }

    public void moveDown(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImageA(this);
        if (!checkObstacleCollision(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            y += SPEED;
        }else{
            if (!downCollision){
                y += SPEED;
            }
        }

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public void setImage(Player e){
        if(move_count % 2 == 0){
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat1.png")));

        }else{
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat2.png")));

        }
        move_count++;
    }
    public void setImageA(Player e){
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat_a1.png")));
    }
    public void setImageB(Player e){
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat_b.png")));
    }
    public void setImageL(Player e){
        if(move_count % 2 == 1){
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat1l.png")));
        }else{
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat2l.png")));
        }
        move_count++;
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }
    void wear(Collar collar){
        health += collar.getHealth();
        damage += collar.getDamage();
    }

    void eat (Food food){
        health += food.getFoodValue();
    }

    public void takeDamage(int damage, Enemy enemy){
        health = health - damage;
        attackEnemy(enemy);
    }

    public void attackEnemy(Enemy enemy){
        Random r = new Random();
        enemy.takeDamage(r.nextInt(damage));
    }
    public ArrayList<Item> takeStartInventory(){
        ArrayList<Item> load_inv = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("items.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            load_inv = (ArrayList<Item>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Take items from file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return load_inv;
    }
}