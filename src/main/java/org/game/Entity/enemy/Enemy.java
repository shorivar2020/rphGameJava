package org.game.Entity.enemy;

import org.game.Entity.Entity;
import org.game.Player;

import java.awt.*;

public abstract class Enemy extends Entity {
    int health;
    int damage;


    public abstract void takeDamage(int damage);
    public abstract Rectangle getBounds();
    public abstract void draw(Graphics2D g2d);
    public abstract void attack(Player player);
    public abstract boolean isAlive();
    public abstract void move(int x_d, int y_d);
}
