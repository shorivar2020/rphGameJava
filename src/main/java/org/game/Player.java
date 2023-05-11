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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The player class is controlled through the keyboards by the user.
 * Moves around the map. Interacts with objects.
 */
public class Player {

    public int health;
    private int damage;
    private static final int START_HEALTH = 9;
    private static final int START_DAMAGE = 5;
    private static final int SIZE = 50;
    private static final int SPEED = 5;

    private int x;
    private int y;

    public List<Item> inventory = new ArrayList<>();

    public Player(int x, int y, List<Item> items) {
        this.x = x;
        this.y = y;
        health = START_HEALTH;
        damage = START_DAMAGE;
        if(items!= null){
            inventory = items;
        }
    }


    private boolean checkObstacleCollision(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit, Rectangle newBounds) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(newBounds)) {
                return true;
            }
        }
        for (Door door: doors) {
            if (door.getBounds().intersects(newBounds)) {
                for (Item item: inventory){
                    if (item instanceof Key) {
                        door.unlock((Key) item);
                        System.out.println("UNLOCK");
                        if(!door.locked){
//                            inventory.remove(item);
                            System.out.println(inventory + "GO THROW DOOR");
                            return false;
                        }
                    }
                }
                if(door.locked){
                    return true;
                }
            }
        }
        if(exit.getBounds().intersects(newBounds)){
            game.Win();
        }
        return false;
    }

    private boolean checkItemCollision(Game game, List<Item> items, List<TrashCan> trashCans, List<Door> doors, Rectangle newBounds) {
        for (Item item: items) {
            if (item instanceof Key) {
               if (((Key) item).getBounds().intersects(newBounds)){
                   System.out.println("I FOUND KEY");
                   inventory.add(item);
                   game.items.remove(item);
                   return true;
               }
            }
            if (item instanceof Food) {
                if (((Food) item).getBounds().intersects(newBounds)){
                    System.out.println("I FOUND FOOD");
                    inventory.add(item);
                    eat((Food) item);
                    game.items.remove(item);
                    return true;
                }
            }
            if (item instanceof SilverCollar) {
                if (((SilverCollar) item).getBounds().intersects(newBounds)){
                    System.out.println("I FOUND COLLAR");
                    inventory.add(item);
                    wear((SilverCollar) item);
                    game.items.remove(item);
                    return true;
                }
            }
            if (item instanceof GoldCollar) {
                if (((GoldCollar) item).getBounds().intersects(newBounds)){
                    System.out.println("I FOUND COLLAR");
                    inventory.add(item);
                    wear((GoldCollar) item);
                    game.items.remove(item);
                    return true;
                }
            }
        }
        for (TrashCan trashCan: trashCans) {
            if (trashCan.getBounds().intersects(newBounds)) {
                System.out.println("FIND IN TRASH");
                for(Item item: trashCan.content){
                    inventory.add(item);
                    if(item instanceof Collar){
                        wear((Collar) item);
                    }
                    if(item instanceof Food){
                        eat((Food) item);
                    }
                    System.out.println("I found NEW ITEM");
                }
                System.out.println(inventory);
                trashCan.content.removeAll(inventory);
                return true;
            }
        }

        return false;
    }

    public boolean checkEnemyCollision(Game game, List<Enemy> enemies, Rectangle newBounds){
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

    public void moveLeft(Game game, List<Obstacle> obstacles, List<Item> items, List<TrashCan> trashCans, List<Door> doors, List<Enemy> enemies, Exit exit) {
        Rectangle newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        if(checkItemCollision(game, items, trashCans, doors, newBounds)){
            System.out.println("CHECK COLLISION");
        }
        if(checkEnemyCollision(game, enemies, newBounds)){
            System.out.println("CHECK ENEMY");
        }
        else if (!checkObstacleCollision(game, obstacles, doors, exit, newBounds)) {
            x -= SPEED;
        }else{
            x += SPEED;
        }
    }

    public void moveRight(Game game, List<Obstacle> obstacles, List<Item> items, List<TrashCan> trashCans, List<Door> doors, List<Enemy> enemies, Exit exit) {
        Rectangle newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        if(checkItemCollision(game, items, trashCans, doors, newBounds)){
            System.out.println("CHECK COLLISION");
        }
        if(checkEnemyCollision(game, enemies, newBounds)){
            System.out.println("CHECK ENEMY");
        }
        if (!checkObstacleCollision(game, obstacles, doors, exit, newBounds)) {
            x += SPEED;
        }else{
            x -= SPEED;
        }

    }

    public void moveUp(Game game, List<Obstacle> obstacles, List<Item> items, List<TrashCan> trashCans, List<Door> doors, List<Enemy> enemies, Exit exit) {
        Rectangle newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        if(checkItemCollision(game, items, trashCans, doors, newBounds)){
            System.out.println("CHECK COLLISION");
        }
        if(checkEnemyCollision(game, enemies, newBounds)){
            System.out.println("CHECK ENEMY");
        }
        if (!checkObstacleCollision(game, obstacles, doors, exit, newBounds)) {
            y -= SPEED;
        }else{
            y += SPEED;
        }

    }

    public void moveDown(Game game, List<Obstacle> obstacles, List<Item> items, List<TrashCan> trashCans, List<Door> doors, List<Enemy> enemies, Exit exit) {
        Rectangle newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        if(checkItemCollision(game, items, trashCans, doors, newBounds)){
            System.out.println("CHECK COLLISION");
        }
        if(checkEnemyCollision(game, enemies, newBounds)){
            System.out.println("CHECK ENEMY");
        }
        if (!checkObstacleCollision(game, obstacles, doors, exit, newBounds)) {
            y += SPEED;
        }else{
            y -= SPEED;
        }

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fill(getBounds());
    }

    public boolean collidesWith(Rectangle rect) {
        return getBounds().intersects(rect);
    }

    public void pickup (Item item, TrashCan trashCan){
        inventory.add(item);
        trashCan.content.remove(item);
        item.taken = true;
        //wear
        //eat
    }

    void wear(Collar collar){
        System.out.println("WEAR COLLAR");
        health += collar.getHealth();
        damage += collar.getDamage();
    }

    void eat (Food food){
        System.out.println("EAT FOD AM-AM-AM");
        health += food.getFoodValue();
    }

    public void addItemToInventory(Item item) {
        if(item instanceof Food){
            eat((Food) item);
        }
        inventory.add(item);
    }

    public void drawHealth(Graphics2D g2d){
        g2d.setColor(Color.RED);
        g2d.fillOval(300, 0, 25, 25);
    }

    public void takeDamage(int damage, Enemy enemy){
        health = health - damage;
        attackEnemy(enemy);
    }

    public void attackEnemy(Enemy enemy){
        Random r = new Random();
        enemy.takeDamage(r.nextInt(damage));
    }
}