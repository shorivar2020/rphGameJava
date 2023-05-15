package org.game;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.Door;
import org.game.entity.Exit;
import org.game.entity.Obstacle;
import org.game.entity.TrashCan;
import org.game.entity.enemy.Enemy;
import org.game.entity.item.Food;
import org.game.entity.item.Item;
import org.game.entity.item.Key;
import org.game.entity.item.collar.Collar;
import org.game.entity.item.collar.GoldCollar;
import org.game.entity.item.collar.SilverCollar;

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
import java.util.logging.Level;

/**
 * The player class is controlled through the keyboards by the user.
 * Moves around the map. Interacts with objects.
 */
@Log
@Getter
@Setter
public class Player implements Serializable {

    private int health;
    private int damage;
    private static final int START_HEALTH = 9;
    private static final int START_DAMAGE = 5;
    public static final int SIZE = 50;
    public static final int SPEED = 5;

    private int x;
    private int y;
    int moveCount = 0;

    private boolean leftCollision;
    private boolean rightCollision;
    private boolean upCollision;
    private boolean downCollision;
    private boolean doorCollision;

    private List<Item> inventory;
    ImageIcon image;
    Rectangle newBounds;
    Random r;


    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        health = START_HEALTH;
        damage = START_DAMAGE;
        inventory = takeStartInventory();
        setImage(this);
    }


    private boolean checkObstacle(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit, Rectangle newBounds) {
        leftCollision = false;
        rightCollision = false;
        upCollision = false;
        downCollision = false;
        doorCollision = false;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(newBounds)) {
                interactWithObstacles(obstacle);
                return true;
            }
        }
        for (Door door: doors) {
            if (door.getBounds().intersects(newBounds)) {
                return interactWithDoor(door);
            }
        }
        if(exit.getBounds().intersects(newBounds)){
            game.winGame();
        }
        return false;
    }

    public void checkItemCollision(Game game, List<Item> items, List<TrashCan> trashCans, List<Enemy> enemies) {
        newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        for (Item item: items) {
            if (item instanceof Key key && (key.getBounds().intersects(newBounds))){
                findKey(game, key);
                return;
            }
            if (item instanceof Food food && (food.getBounds().intersects(newBounds))){
                findFood(game, food);
                return;
            }
            if (item instanceof SilverCollar silverCollar && ((silverCollar).getBounds().intersects(newBounds))){
                findSilverCollar(game, silverCollar);
                return;
            }
            if (item instanceof GoldCollar goldCollar && ((goldCollar).getBounds().intersects(newBounds))){
                findGoldCollar(game, goldCollar);
                return;
            }
        }
        checkTrashCanCollision(trashCans);
        checkEnemyCollision(game, enemies);
    }
    public void checkTrashCanCollision(List<TrashCan> trashCans){
        for (TrashCan trashCan: trashCans) {
            if (trashCan.getBounds().intersects(newBounds)) {
                for(Item item: trashCan.content){
                    inventory.add(item);
                    if(item instanceof Collar collar){
                        wear(collar);
                    }
                    if(item instanceof Food food){
                        eat(food);
                    }
                }
                trashCan.content.removeAll(inventory);
            }
        }
    }
    public void checkEnemyCollision(Game game, List<Enemy> enemies){
        for(Enemy enemy: enemies){
            if ((enemy).getBounds().intersects(newBounds)){
                if (enemy.getBounds().intersects(newBounds)){
                    enemy.attack(this);
                    if(!enemy.isAlive()){
                        List<Enemy> enemyList = game.getEnemies();
                        enemyList.remove(enemy);
                        game.setEnemies(enemyList);
                    }
                    if(health < 1){
                        game.loseGame();
                    }
                }
                return;
            }
        }
    }


    public void moveLeft(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImageL(this);
        if (!checkObstacle(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            x -= SPEED;
        }else{
            if (!leftCollision && !doorCollision){
                x -= SPEED;
            }
        }
    }

    public void moveRight(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImage(this);
        if (!checkObstacle(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            x += SPEED;
        }else{
            if (!rightCollision) {
                x += SPEED;
            }
        }

    }

    public void moveUp(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImageB(this);
        if (!checkObstacle(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            y -= SPEED;
        }else{
            if(!upCollision && !doorCollision){
                y -= SPEED;
            }
        }
    }

    public void moveDown(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImageA(this);
        if (!checkObstacle(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
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
        if(moveCount % 2 == 0){
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat1.png")));

        }else{
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat2.png")));

        }
        moveCount++;
    }
    public void setImageA(Player e){
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat_a1.png")));
    }
    public void setImageB(Player e){
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat_b.png")));
    }
    public void setImageL(Player e){
        if(moveCount % 2 == 1){
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat1l.png")));
        }else{
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cat2l.png")));
        }
        moveCount++;
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
        r = new Random();
        enemy.takeDamage(r.nextInt(damage));
    }
    public List<Item> takeStartInventory(){
        ArrayList<Item> loadInv = new ArrayList<>();
        ObjectInputStream in;
        try {
            try(FileInputStream fileIn = new FileInputStream("items.txt")){
                in = new ObjectInputStream(fileIn);
                loadInv = (ArrayList<Item>) in.readObject();
                in.close();
            }
            log.log(Level.FINE, "Take items from file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadInv;
    }

    public void findKey(Game game, Key key){
        log.log(Level.FINE,"I FOUND KEY");
        inventory.add(key);
        List<Item> itemList = game.getItems();
        itemList.remove(key);
        game.setItems(itemList);
    }

    public void findFood(Game game, Food food){
        log.log(Level.FINE, "I FOUND FOOD");
        inventory.add(food);
        eat(food);
        List<Item> itemList = game.getItems();
        itemList.remove(food);
        game.setItems(itemList);
    }

    public void findSilverCollar(Game game, SilverCollar silverCollar){
        log.log(Level.FINE, "I FOUND COLLAR");
        inventory.add(silverCollar);
        wear(silverCollar);
        List<Item> itemList = game.getItems();
        itemList.remove(silverCollar);
        game.setItems(itemList);
    }
    public void findGoldCollar(Game game, GoldCollar goldCollar){
        inventory.add(goldCollar);
        wear(goldCollar);
        List<Item> itemList = game.getItems();
        itemList.remove(goldCollar);
        game.setItems(itemList);
    }

    public boolean interactWithDoor(Door door){
        for (Item item: inventory){
            if (item instanceof Key key) {
                door.unlock(key);
                if(!door.isLocked()){
                    return false;
                }
            }
        }
        if(door.isLocked()){
            doorCollision = true;
            return true;
        }
        return false;
    }

    public void interactWithObstacles(Obstacle obstacle){
        Rectangle intersection = obstacle.getBounds().intersection(newBounds);

        double width = intersection.getWidth();
        double height = intersection.getHeight();

        double dx = (newBounds.getCenterX() - obstacle.getBounds().getCenterX()) / width;
        double dy = (newBounds.getCenterY() - obstacle.getBounds().getCenterY()) / height;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx < 0) {
                rightCollision = true;
            } else {
                leftCollision = true;
            }
        } else {
            if (dy < 0) {
                downCollision = true;
            } else {
                upCollision = true;
            }
        }
    }
}