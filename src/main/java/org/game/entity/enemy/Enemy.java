package org.game.entity.enemy;

import org.game.entity.Entity;
import org.game.Player;

import java.awt.*;
import java.io.Serializable;

public abstract class Enemy extends Entity implements Serializable {
    int health;
    int damage;


    public abstract void takeDamage(int damage);
    public abstract Rectangle getBounds();
    public abstract void draw(Graphics2D g2d);
    public abstract void attack(Player player);
    public abstract boolean isAlive();
    public abstract void move();


}
