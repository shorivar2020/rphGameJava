package org.game.Entity.enemy;

import org.game.Entity.Entity;
import org.game.Player;

import java.awt.*;

public abstract class Enemy extends Entity {
    int x;
    int y;
    int health;
    int damage;
    void attack(Player player){}
    void update(){}

    public abstract void takeDamage(int damage);
    public abstract Rectangle getBounds();
    public abstract void draw(Graphics2D g2d);
}
