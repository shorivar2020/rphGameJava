package org.game.entity.item.collar;

import org.game.entity.item.Item;

import java.awt.*;

public abstract class Collar extends Item {
    int health;
    int damage;
    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public abstract void  draw(Graphics2D g2d);

}
