package org.game;
import org.game.Entity.Obstacle;
import org.game.Entity.item.Food;
import org.game.Entity.item.Item;
import org.game.Entity.item.collar.Collar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The player class is controlled through the keyboards by the user.
 * Moves around the map. Interacts with objects.
 */
public class Player {

    private int health;
    private int damage;
    private static final int START_HEALTH = 9;
    private static final int SIZE = 50;
    private static final int SPEED = 5;

    private int x;
    private int y;

    public List<Item> inventory;
    public List<Collar> collar;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        inventory = new ArrayList<>();
    }


    private boolean checkObstacleCollision(List<Obstacle> obstacles, Rectangle newBounds) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(newBounds)) {
                return true;
            }
        }
        return false;
    }

    public void moveLeft(List<Obstacle> obstacles) {
        Rectangle newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        if (!checkObstacleCollision(obstacles, newBounds)) {
            x -= SPEED;
        }else{
            x += SPEED;
        }
    }

    public void moveRight(List<Obstacle> obstacles) {
        Rectangle newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        if (!checkObstacleCollision(obstacles, newBounds)) {
            x += SPEED;
        }else{
            x -= SPEED;
        }

    }

    public void moveUp(List<Obstacle> obstacles) {
        Rectangle newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        if (!checkObstacleCollision(obstacles, newBounds)) {
            y -= SPEED;
        }else{
            y += SPEED;
        }

    }

    public void moveDown(List<Obstacle> obstacles) {
        Rectangle newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        if (!checkObstacleCollision(obstacles, newBounds)) {
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

    void pickup (Item item){
        inventory.add(item);
    }

    void wear(Collar collar){
        inventory.add(collar);
        health += collar.getHealth();
        damage += collar.getDamage();
    }

    void eat (Food food){
        health += food.getFoodValue();
    }

}