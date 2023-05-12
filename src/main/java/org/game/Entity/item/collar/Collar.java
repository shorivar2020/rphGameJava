package org.game.Entity.item.collar;

import org.game.Entity.item.Item;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Collar extends Item {
    int health;
    int damage;

    int width = 20;
    int height = 5;
    BufferedImage image;

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public abstract void  draw(Graphics2D g2d);


}
