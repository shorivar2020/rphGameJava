package org.game;
import org.game.Entity.Obstacle;
import org.game.Entity.item.Item;

import java.awt.*;
import java.util.List;
public class Player {

    private int health;
    private static final int START_HEALTH = 9;
    private static final int SIZE = 50;
    private static final int SPEED = 5;

    private int x;
    private int y;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

//    public void moveLeft() {
//        x -= SPEED;
//    }

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
        }
    }

    public void moveRight() {
        x += SPEED;
    }

    public void moveUp() {
        y -= SPEED;
    }

    public void moveDown() {
        y += SPEED;
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

    void pickup(Item item){}

}