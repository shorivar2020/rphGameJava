package org.game.Entity.item.collar;

import org.game.Entity.item.Item;

import java.awt.*;

public abstract class Collar extends Item {
    int health;
    int damage;
    int x;
    int y;
    int width = 20;
    int height = 5;

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }




    public abstract void draw(Graphics2D g2d);
}
