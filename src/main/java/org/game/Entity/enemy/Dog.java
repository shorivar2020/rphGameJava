package org.game.Entity.enemy;

import org.game.Player;

import java.awt.*;

public class Dog extends Enemy {
    int health = 2;
    int damage = 3;
    int width = 50;
    int height = 50;
    public Dog(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.fillRect(x, y, width, height);
    }

    public void move() {
        // Двигать монстра по экрану
    }

    public void attack(Player player) {
        player.takeDamage(damage);
    }

    public void takeDamage(int damage){
        health = health - damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
